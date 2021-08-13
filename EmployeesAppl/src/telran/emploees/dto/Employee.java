package telran.emploees.dto;

import java.time.LocalDate;

public class Employee {
	
	private long id;
	private int salary;
	private LocalDate birthDate;
	private String department;
	
	public Employee(long id, int salary, LocalDate birthDate, String department) {
		this.id = id;
		this.salary = salary;
		this.birthDate = birthDate;
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public String getDepartment() {
		return department;
	}

	public long getId() {
		return id;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	@Override
	public String toString() {
		return String.format("[ID: %s, Salary: %s, Birthday: %s, Department: %s]\n", id, salary, birthDate, department);
	}

}
