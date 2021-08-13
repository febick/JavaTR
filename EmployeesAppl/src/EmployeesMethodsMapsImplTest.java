import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.emploees.dto.Employee;
import telran.emploees.dto.EmployeesCodes;
import telran.emploees.services.EmployeesMethodsMapsImpl;

class EmployeesMethodsMapsImplTest {
	
	EmployeesMethodsMapsImpl database;
	Employee defaultEmployee = new Employee(1, 10000, LocalDate.of(1987, 11, 5), "IT");
	List<Employee> list;

	@BeforeEach
	void setUp() {
		database = new EmployeesMethodsMapsImpl();
	}

	@Test
	void testAddEmploee() {
		assertEquals(EmployeesCodes.OK, database.addEmploee(defaultEmployee));
		assertEquals(EmployeesCodes.ALREADY_EXISTS, database.addEmploee(defaultEmployee));
	}

	@Test
	void testRemoveEmployee() {
		database.addEmploee(defaultEmployee);
		assertEquals(EmployeesCodes.OK, database.removeEmployee(defaultEmployee.getId()));
		assertEquals(EmployeesCodes.NOT_FOUND, database.removeEmployee(defaultEmployee.getId()));
	}

	@Test
	void testUpdateSalary() {
		assertEquals(EmployeesCodes.NOT_FOUND, database.updateSalary(0, 0));
		database.addEmploee(defaultEmployee);
		assertEquals(EmployeesCodes.OK, database.updateSalary(defaultEmployee.getId(), 50000));
		assertEquals(50000, database.getEmployee(defaultEmployee.getId()).getSalary());
	}

	@Test
	void testUpdateDepartment() {
		assertEquals(EmployeesCodes.NOT_FOUND, database.updateDepartment(0, "PR"));
		database.addEmploee(defaultEmployee);
		assertEquals(EmployeesCodes.OK, database.updateDepartment(defaultEmployee.getId(), "PR"));
		assertEquals("PR", database.getEmployee(defaultEmployee.getId()).getDepartment());
	}

	@Test
	void testGetEmployee() {
		assertNull(database.getEmployee(0));
		database.addEmploee(defaultEmployee);
		assertEquals(defaultEmployee, database.getEmployee(defaultEmployee.getId()));
	}

	@Test
	void testGetEmployessBySalary() {
		fillDatabase();
		var result = database.getEmployessBySalary(2000, 8000).iterator();
		result.forEachRemaining(e -> {
			var salary = e.getSalary();
			assertTrue(salary >= 2000 && salary <= 8000);
		});
	}

	@Test
	void testGetEmployeesByAge() {
		fillDatabase();
		try {
			database.getEmployeesByAge(40, 10);
			fail();
		} catch (Exception e) {
			
		}		
		var result = database.getEmployeesByAge(25, 40).iterator();
		result.forEachRemaining(e -> {
			var currentAge = LocalDate.now().getYear() - e.getBirthDate().getYear();
			assertTrue(currentAge >= 25 && currentAge <= 40);
		});
	}

	@Test
	void testGetEmployeesByDepartment() {
		fillDatabase();
		var result = database.getEmployeesByDepartment("PR").iterator();
		result.forEachRemaining(e -> {
			var department = e.getDepartment();
			assertEquals("PR", department);
		});
	}

	@Test
	void testGetAllEmployees() {
		fillDatabase();
		var it = database.getAllEmployees().iterator();
		for (int i = 0; i < 5; i++) { assertEquals(list.get(i), it.next()); }
	}
	
	void fillDatabase() {
		list = new ArrayList<>();
		list.add(defaultEmployee);
		list.add(new Employee(2, 10000, LocalDate.of(1988, 11, 5), "IT"));
		list.add(new Employee(3, 5000, LocalDate.of(1992, 11, 5), "PR"));
		list.add(new Employee(4, 5000, LocalDate.of(1996, 11, 5), "PR"));
		list.add(new Employee(5, 2500, LocalDate.of(2001, 11, 5), "QA"));
		list.forEach(database::addEmploee);
	}

}
