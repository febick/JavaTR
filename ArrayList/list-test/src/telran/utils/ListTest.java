package telran.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {
	
List<Integer> listInt;
List<Person> persons;

	@BeforeEach
	void setUp() throws Exception {
		listInt = new ArrayList<>(1);
		listInt.add(1);
		listInt.add(2);
		listInt.add(3);
		listInt.add(4);
		listInt.add(5);
		
		persons = new ArrayList<>();
		persons.add(new Person(0, "Авраам"));
		persons.add(new Person(1, "Исаак"));
		persons.add(new Person(2, "Иаков"));
		persons.add(new Person(3, "Сарра"));
		persons.add(new Person(4, "Ревекка"));
		persons.add(new Person(5, "Рахиль"));
		persons.add(new Person(6, "Лия"));
	}
	

	@Test
	void addByIndexTest() {
		listInt.add(99, 1);
		assertEquals(99, listInt.get(1));
	}
	
	@Test
	void removeTest() {
		assertFalse(listInt.remove(100));
		assertTrue(listInt.remove(1));
	}
	
	@Test 
	void getTest() {
		assertEquals(5, listInt.get(4));
		assertEquals(null, listInt.get(100));
	}
	
	@Test
	void indexOTest() {
		assertEquals(0, listInt.indexOf(1));
		assertEquals(2, listInt.indexOf(3));
		assertEquals(3, listInt.indexOf(4));
		assertEquals(-1, listInt.indexOf(100));

		Person patternPersonTrue = new Person(0, null);
		Person patternPersonFalse = new Person(7, "Давид");
		assertEquals(0, persons.indexOf(patternPersonTrue));
		assertEquals(-1, persons.indexOf(patternPersonFalse));
	}
	
	@Test
	void lastIndexOfTest() {
		listInt.add(1, 3);
		assertEquals(1, listInt.get(3));
		assertEquals(3, listInt.lastIndexOf(1));
		
		Person patternPerson = new Person(0, "Давид");
		persons.add(patternPerson, 5);
		assertEquals(patternPerson, persons.get(5));
		assertEquals(5, persons.lastIndexOf(patternPerson));
	}
	
	@Test
	void setTest() {
		assertEquals(1, listInt.get(0));
		listInt.set(999, 0);
		assertEquals(999, listInt.get(0));
		
		assertEquals(4, listInt.get(3));
		listInt.set(9, 3);
		assertEquals(9, listInt.get(3));
	}
	
	@Test
	void swapTest() {
		assertEquals(1, listInt.get(0));
		assertEquals(2, listInt.get(1));
		listInt.swap(0, 1);
		assertEquals(1, listInt.get(1));
		assertEquals(2, listInt.get(0));
	}
	
	@Test
	void removeByPattern() {
		Person patternPerson = new Person(3, "Давид");
		persons.add(patternPerson);
		assertEquals(8, persons.size());
		
		persons.remove(patternPerson);
		assertEquals(7, persons.size());
	}
	
	@Test
	void removeAllByPattern() {
		List<Person> personsPattern = new ArrayList<>();
		Person patternPerson1 = new Person(3, "Давид");
		Person patternPerson2 = new Person(4, "Моше");
		personsPattern.add(patternPerson1);
		personsPattern.add(patternPerson2);
		assertEquals(2, personsPattern.size());
		
		persons.add(new Person(3, "Давид"));
		persons.add(new Person(3, "Давид"));
		persons.add(new Person(3, "Давид"));
		persons.add(new Person(4, "Моше"));
		persons.add(new Person(4, "Моше"));
		persons.add(new Person(4, "Моше"));
		assertEquals(13, persons.size());
		
		persons.removeAll(personsPattern);
		assertEquals(7, persons.size());
	}
	
	@Test
	void retainAllTest() {
		List<Person> personsPattern = new ArrayList<>();
		Person patternPerson1 = new Person(0, "Авраам");
		Person patternPerson2 = new Person(6, "Лия");
		Person patternPerson3 = new Person(7, "Давид");
		personsPattern.add(patternPerson1);
		personsPattern.add(patternPerson2);
		personsPattern.add(patternPerson3);
		
		assertTrue(persons.retainAll(personsPattern));
		assertEquals(patternPerson1, persons.get(0));
		assertEquals(patternPerson2, persons.get(1));
		assertEquals(2, persons.size());
	}
	
	@Test
	void addAllTest() {
		
		List<Integer> firstArray = new ArrayList<>();
		firstArray.add(1);
		firstArray.add(2);
		
		List<Integer> secondArray = new ArrayList<>();
		secondArray.add(3);
		secondArray.add(4);
		
		firstArray.addAll(secondArray);
		assertEquals(4, firstArray.size());
		assertEquals(1, firstArray.get(0));
		assertEquals(2, firstArray.get(1));
		assertEquals(3, firstArray.get(2));
		assertEquals(4, firstArray.get(3));
	}

}
