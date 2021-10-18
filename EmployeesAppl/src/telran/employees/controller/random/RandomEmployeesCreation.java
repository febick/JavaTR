package telran.employees.controller.random;

import java.util.*;
import telran.employees.dto.Employee;
import telran.employees.services.EmployeesMethods;
import telran.employees.services.EmployeesMethodsMapsImpl;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;

public class RandomEmployeesCreation {

	private static Properties properties = new Properties();
	private static List<String> departments;
	private static EmployeesMethods base;

	public static void main(String[] args) throws Exception {
		getProperties();
		generateEmployees();
		printEmployees();
	}

	private static void printEmployees() throws Exception {
		base.getAllEmployees().forEach(System.out::print);
	}

	private static void generateEmployees() throws Exception {
		base = (EmployeesMethodsMapsImpl) EmployeesMethodsMapsImpl.getEmptyEmloyees();
		
		var maxID = Integer.parseInt(properties.getProperty("maxID", "99999999"));
		var minID = Integer.parseInt(properties.getProperty("minID", "10000000"));
		var limit = Integer.parseInt(properties.getProperty("nEmployees", "100"));
		var filePath = properties.getProperty("filePath", "defult.db");

		
//		EmployeesMethods employeesMethods = EmployeesMethodsMapsImpl.getEmptyEmployees();
//	    Stream.generate(() -> new Employee(getRandomId(),
//	        getRandomSalary(), getRandomDate(), getRandomDepartment()))
//	    .distinct().limit(getNEmployees()).forEach(employeesMethods::addEmployee);
		
		new Random()
		.longs(minID, maxID)
		.distinct()
		.limit(limit)
		.forEach(id -> {
			try {
				base.addEmployee(new Employee(id, getSalary(), getBirthday(), getDepartment()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		base.save(filePath);
	}

	
	private static int getSalary() {
		var minSalary = Integer.parseInt(properties.getProperty("minSalary", "5200"));
		var maxSalary = Integer.parseInt(properties.getProperty("maxSalary", "10000"));
		return new Random().nextInt(maxSalary-minSalary) + minSalary;
	}
	
	private static LocalDate getBirthday() {
		var minYear = Integer.parseInt(properties.getProperty("minYear", "1970"));
		var maxYear = Integer.parseInt(properties.getProperty("maxYear", "2021"));
		LocalDate date = LocalDate
				.of(new Random().nextInt(maxYear-minYear) + minYear, Month.JANUARY, 1);
		return date;
	}
	
	private static String getDepartment() {
		int random = new Random().nextInt(departments.size());
		return departments.get(random);
	}

	private static void getProperties() throws IOException {
		InputStream propsStream = new FileInputStream("random-config.properties");
		properties.load(propsStream);
		departments = Arrays.asList(properties.getProperty("departments").split("[, ]+"));
	}

}
