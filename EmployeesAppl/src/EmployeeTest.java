import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.dto.Employee;

class EmployeeTest {
	
	Employee employee;

	@BeforeEach
	void setUp() {
		employee = new Employee(12345, 10000, LocalDate.of(1987, 11, 5), "IT");
	}
	
	@Test
	void testGetSalary() {
		assertEquals(10000, employee.getSalary());
	}

	@Test
	void testGetDepartment() {
		assertEquals("IT", employee.getDepartment());
	}

	@Test
	void testGetId() {
		assertEquals(12345, employee.getId());
	}

	@Test
	void testGetBirthDate() {
		assertEquals(LocalDate.of(1987, 11, 5), employee.getBirthDate());
		assertEquals(1987, employee.getBirthDate().getYear());
	}

}
