package telran.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TensorTest {

//	@Test
//	void test() {
//		int dimensions[] = {2, 5, 2};
//		Tensor<Integer> tensor = new Tensor<>(dimensions, 10);
//		tensor.forEach(n -> System.out.print(n + "; "));
//	}
	
	@Test
	void setAndGetValueTest() {
		int dimensions[] = {2, 5, 2};;
		Tensor<Integer> tensor = new Tensor<>(dimensions, 10);
		int setIndexes[] = {1, 3, 1};
		tensor.setValue(setIndexes, 5);		
		assertEquals(5, tensor.getValue(setIndexes));
	}
	
	@Test
	void flatMapTest() {
		int dimensions[] = {2, 5, 2};;
		Tensor<Integer> tensor = new Tensor<>(dimensions, 10);
		Integer[] nonFilledArray = new Integer[20];
		Integer[] filledArray = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		tensor.flatMap(nonFilledArray);
		assertArrayEquals(filledArray, nonFilledArray);
	}
	

}
