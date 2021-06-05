import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ArrayIntTest {
	
	
	// HOME_WORK_TESTS
	
	@Test
	void binaryCountTest() {
		int array[] = { 0, 1, 2, 3, 5, 5, 6, 6, 7, 7, 7, 8, 9, 10, 11, 12 };
		assertEquals(3, ArrayInt.binaryCount(array, 7));
		assertEquals(2, ArrayInt.binaryCount(array, 5));
		assertEquals(1, ArrayInt.binaryCount(array, 12));
		assertEquals(0, ArrayInt.binaryCount(array, 30));
		assertEquals(2, ArrayInt.binaryCount(array, 6));
	}
	
	
	@Test
	void addNumberSortedTest() {
		
		// Добавление в середину массива
		int array1[] = { 0, 1, 2, 4, 5, 6, 7, 8, 9, 10 };
		int array2[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Assert.assertArrayEquals(array2, ArrayInt.addNumberSorted(array1, 3));
		
		// Добавление дубликата в начало массива
		int array3[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array4[] = { 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(array4, ArrayInt.addNumberSorted(array3, 1));
		
		// Добавление в конец массива
		int array5[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array6[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		Assert.assertArrayEquals(array6, ArrayInt.addNumberSorted(array5, 11));
		
	}
	
	@Test
	void removeNumberTest() {
		
		// Удаление элемента выходящего за пределы массива
		int array1[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array2[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Assert.assertArrayEquals(array2, ArrayInt.removeNumber(array1, 12));
		
		// Удаление элемента из середины массива
		int array3[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array4[] = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 10 };
		Assert.assertArrayEquals(array4, ArrayInt.removeNumber(array3, 6));
		
		// Удаление начального элемента
		int array5[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array6[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Assert.assertArrayEquals(array6, ArrayInt.removeNumber(array5, 0));
		
		// Удаление последнего элемента
		int array7[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int array8[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Assert.assertArrayEquals(array8, ArrayInt.removeNumber(array7, 10));
		
	}
	
	@Test
	void standartBinarySearchTest() {
		
		int[] array = {1, 2, 3, 6, 8, 9, 10};
        int pos1 = Arrays.binarySearch(array, 3);
        int pos2 = Arrays.binarySearch(array, 7);
        
        assertEquals(2, pos1); 
        assertEquals(-5, pos2); 
		
	}
	
	// CLASS_WORK_TESTS
	
	@Test
	void addNumberTest() {
		int array1[] = { 1, 2, 3 };
		int array2[] = { 1, 2, 3, 4 };
		Assert.assertArrayEquals(array2, ArrayInt.addNumber(array1, 4));
	}
	
	@Test
	void insertNumber() {
		int array1[] = { 1, 2, 4, 5};
		int array2[] = { 1, 2, 3, 4, 5};
		Assert.assertArrayEquals(array2, ArrayInt.insertNumber(array1, 2, 3));
		
		int array3[] = { 1, 2, 3, 4, 5 };
		int array4[] = { 0, 1, 2, 3, 4, 5};
		
		Assert.assertArrayEquals(array4, ArrayInt.insertNumber(array3, 0, 0));
	}

}
