package telran.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetTest {
	private static final int N_NUMBERS = 100;
	Set<Integer> setInt;
	Set<String> setString;

	Person p1 = new Person(1, "Moshe");
	Person p2 = new Person(2, "Alex");
	@BeforeEach
	void setUp() throws Exception {
		setInt =  new TreeSet<>();
		setString = new TreeSet<>();
		setInt.add(0);
		setInt.add(1);
		setInt.add(2);
		setInt.add(3);
		setInt.add(4);
		setInt.add(5);

	}
	@Test
	void containsTest() {
		for(int i = 0; i < 6; i++) {
			assertTrue(setInt.contains(i));
		}
		assertFalse(setInt.contains(100));

	}

	@Test
	void addTest() {

		assertTrue(setInt.add(6));
		assertTrue(setInt.contains(6));
		assertFalse(setInt.add(6));
		Set<Person> persons = new TreeSet<>();
		persons.add(p1);
		persons.add(p2);
	}


	@Test
	void sizeTest() {
		assertEquals(6, setInt.size());
	}

	@Test
	void removePatternTest() {
		assertTrue(setInt.remove(2));
		assertFalse(setInt.contains(2));
		assertFalse(setInt.remove(2));
	}
	@Test
	void addAllTest() {
		int sizeOld = setInt.size();
		setInt.addAll(setInt);

		assertEquals(sizeOld , setInt.size());



		List<Integer> list = new LinkedList<>();
		list.add(10);
		list.add(200);
		list.add(100);
		setInt.addAll(list);
		list.forEach(e -> assertTrue(setInt.contains(e)));

	}
	@Test
	void removeAll() throws Exception {
		setInt.removeAll(setInt);
		assertEquals(0, setInt.size());
		setUp();

		Set<Integer> patterns = new HashSet<>();
		patterns.add(2);
		patterns.add(3);
		setInt.removeAll(patterns);
		assertFalse(setInt.contains(2));
		assertFalse(setInt.contains(3));
		assertEquals(4, setInt.size());

	}
	@Test
	void retainAll() {
		setInt.retainAll(setInt);
		assertEquals(6, setInt.size());
		initialSetTest();
		Set<Integer> patterns = new HashSet<>();
		patterns.add(2);
		patterns.add(3);
		setInt.retainAll(patterns);
		assertTrue(setInt.contains(2));
		assertTrue(setInt.contains(3));
		assertEquals(2, setInt.size());
	}
	
	@Test
	void iteratorTest () {
		int count = 0;
		Iterator<Integer> it = setInt.iterator();
		while(it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(count, setInt.size());
	}


	@Test
	void removeIf() throws Exception {
		Set<Integer> set = getRandomSet();

		set.removeIf(n -> true);
		assertEquals(0, set.size());

		setUp();
		assertFalse(setInt.removeIf(n -> n > 100));
		initialSetTest();
		assertTrue(setInt.removeIf(n -> n % 2 == 0));
		assertEquals(3, setInt.size());
		assertFalse(setInt.remove((Integer)2));
		assertFalse(setInt.remove((Integer)4));


	}

	private Set<Integer> getRandomSet() {
		Set<Integer> res = new HashSet<>();
		for (int i = 0; i < N_NUMBERS; i++) {
			res.add((int)(Math.random() * Integer.MAX_VALUE));
		}
		return res;
	}
	@Test
	void cleanTest() {
		setInt.clean();
		assertEquals(0, setInt.size());
	}


	private void initialSetTest() {
		int value = 0;
		for (int num: setInt) {
			assertEquals(value++, num );
		}
		assertEquals(6, value);
	}
	
	private SortedSet<Integer> setUpTree() {
		Integer[] ar = new Integer[N_NUMBERS + 1];
		int value = 0;
		for (int i = 0; i <= N_NUMBERS; i++) {
			ar[i] = value;
			value += 2;
		}
		SortedSet<Integer> res = new TreeSet<>();
		Arrays.sort(ar, (n1, n2) -> Math.random() < 0.5 ? 1 : -1);
		for (int i = 0; i < ar.length; i++) {
			res.add(ar[i]);
		}
		return res;
	}
	
	@Test 
	void floorTest() {
		if (setInt instanceof SortedSet) {
			SortedSet<Integer> set = setUpTree();				
			assertNull(set.floor(-1));
			assertEquals(30, set.floor(31));
			assertEquals(32, set.floor(32));
			assertEquals(2 * N_NUMBERS, set.floor(Integer.MAX_VALUE));
		}
	}
	
	@Test
	void ceilingTest() {
		if (setInt instanceof SortedSet) {
			SortedSet<Integer> set = setUpTree();
			assertEquals(0, set.ceiling(-1));
			assertEquals(32, set.ceiling(31));
			assertEquals(32, set.ceiling(32));
		}
	}

	
	
	@Test
	void subSetTest() {
		if (setInt instanceof SortedSet) {
			SortedSet<Integer> set = setUpTree();
			SortedSet<Integer> part = new TreeSet<>();
			part.add(10);
			part.add(12);
			part.add(14);
			part.add(16);
			part.add(18);
			part.add(20);
			part.add(22);
			part.add(24);
			part.add(26);
			part.add(28);
			
			var res = set.subSet(10, true, 28, true);
			
			var resIt = res.iterator();
			var partIt = part.iterator();
			
			while (partIt.hasNext()) {
				assertEquals(partIt.next(), resIt.next());
			}
			
		}
	}


}
