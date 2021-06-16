package telran.utils;

import java.util.Arrays;

public class SortAppl {

	public static void main(String[] args) {
		
		ComparablePerson persons[] = {
				new ComparablePerson(123, 50),
				new ComparablePerson(100, 55),
				new ComparablePerson(500, 55)
		};
		
		Arrays.sort(persons);
		display(persons);
		Arrays.sort(persons, new AgeComporator());
		display(persons);
		
	}

	private static void display(ComparablePerson[] persons) {
		for (int i = 0; i < persons.length; i++) {
			System.out.println(persons[i]);
		}
		System.out.println("________________________");
	}


}
