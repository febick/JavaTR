import java.util.Arrays;

public class ArrayInt {

	public static int binaryCount(int ar[], int num) {

		int workingArray[] = ar;
		int result = 0;
		int left = 0;
		int right = ar.length - 1;
		int middle = (left + right) / 2;

		while (left <= right) {
            if (workingArray[middle] < num) {
            	left = middle + 1;
            } else if (workingArray[middle] == num) {
            	
            	result++; // Записываем результат
            	
            	// Удаляем найденное значение из массива и продолжаем поиск
            	right--;
            	workingArray = removeNumber(workingArray, middle);
 
            } else {
            	right = middle - 1;
            }
            
            middle = (left + right) / 2;
        }
		

		return result;
	}
	
	public static int[] addNumberSorted(int ar[], int num) {
		
		int newAr[] = new int[ar.length + 1];
		int insertIndex = 0;
		int currentValue = ar[0];
		
		// Переносим в новый массив все числа, которые меньше добавляемого
		do {
			currentValue = ar[insertIndex];
			newAr[insertIndex] = currentValue;
			insertIndex++;
		} while (currentValue + 1 < num);
		
		// Вставляем новое число на его позицию
		newAr[insertIndex] = num;
		
		// Заполняем новый массив оставшимися элементами
		for (int i = insertIndex; i < ar.length; i++) {
			newAr[insertIndex + 1] = ar[insertIndex];
			insertIndex++;
		}

		return newAr;
	}
	
	public static int[] removeNumber(int ar[], int index) {
		
		// Возвращаем массив без изменений, если индекс удаляемого элемента превышает кол-во элементов массива
		if (index > ar.length) {
			return ar;
		}
		
		int newArray[] = new int[ar.length - 1];
		int newIndex = 0;

		for (int i = 0; i < ar.length; i++) {
			if (i != index) {
				newArray[newIndex] = ar[i];
				newIndex++;
			}
		}

		return newArray;
	}
	
	
	// CLASS_WORK: Проверьте пожалуйста также мою реализацию заданий в классе
	public static int[] addNumber(int ar[], int number) {
		int newArray[] = Arrays.copyOf(ar, ar.length + 1);		
		newArray[ar.length] = number;
		return newArray;
	}
	
	public static int[] insertNumber(int ar[], int index, int number) {
		int leftPart[] = Arrays.copyOfRange(ar, 0, index);
		int rightPart[] = Arrays.copyOfRange(ar, index, ar.length);
		
		int newLeftPart[] = addNumber(leftPart, number);
	
		int result[] = Arrays.copyOf(newLeftPart, newLeftPart.length + rightPart.length);
		System.arraycopy(rightPart, 0, result, newLeftPart.length, rightPart.length);

		return result;
	}



}
