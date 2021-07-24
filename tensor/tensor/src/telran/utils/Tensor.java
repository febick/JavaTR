package telran.utils;

import java.util.function.Consumer;

public class Tensor<T> {

	private Tensor<T>[] array; // array of tensor objects on one dimention less.
	private T value; // either array of value shut be null. For scalar array - null, for vector - value is null.
		
	public Tensor(int[] dimensions, T value) {
		Tensor<T> tensor = createTensor(dimensions, 0, value);
		this.array = tensor.array;
		this.value = tensor.value;
	}
	
	private Tensor(Tensor<T>[] array, T value) {
		this.array = array;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	private Tensor<T> createTensor(int[] dimensions, int currentDimension, T value) {
		if (currentDimension == dimensions.length) {
			return new Tensor<T>((Tensor<T>[]) null, value);
		}
		
		Tensor<T>[] array = new Tensor[dimensions[currentDimension]];
		for (int i = 0; i < array.length; i++) {
			array[i] = createTensor(dimensions, currentDimension + 1, value);
		}
		return new Tensor<T>(array, null);
	}

	public void forEach(Consumer<T> consumer) {
		if (array == null) {
			consumer.accept(value);
			return;
		}
		for (Tensor<T> tensor : array) {
			tensor.forEach(consumer);
		}
	}

	public void setValue(int[] indexes, T value) {
		goToElement(indexes, value);
	}
	
	public T getValue(int[] indexes) {
		return goToElement(indexes, null);
	}
	
	private T goToElement(int[] indexes, T value) {
		T res = null;
		var currentIndex = 0;
		var currentArray = array[indexes[currentIndex++]].array;
		while (currentArray != null) {
			res = currentArray[indexes[currentIndex]].value;
			if (res == null) {
				currentArray = currentArray[indexes[currentIndex++]].array;
			} else {
				if (value != null) { currentArray[indexes[currentIndex]].value = value; }
				currentArray = null;
			}
		}
		return res;
	}

	private int index = 0;
	public void flatMap(T[] arr) {
		this.forEach(n -> arr[index++] = n);
		index = 0;
	}
	

}
