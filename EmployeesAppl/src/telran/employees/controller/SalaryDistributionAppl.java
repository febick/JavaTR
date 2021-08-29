package telran.employees.controller;

import java.util.List;

import telran.employees.dto.SalaryRangeEmployees;
import telran.employees.services.EmployeesMethods;

public class SalaryDistributionAppl {
	
	private static final String DEFAULT_FILE_PATH = "employess.data";
	private static final int DEFAULT_INTERVAL = 1000;

	public static void main(String[] args) {
		var filePath = args.length < 2 ? DEFAULT_FILE_PATH : args[0];
		int interval = 0;
		try {
			interval = args.length < 2 ? DEFAULT_INTERVAL : Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Interval must be a number.");
			return;
		}
		loadDistributionFile(filePath, interval);
	}
	
	private static void loadDistributionFile(String filePath, int interval) {
		try {
			var database = EmployeesMethods.getEmployees(filePath);
			var result = database.distibutionSalary(interval);
			print(result);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	private static void print(List<SalaryRangeEmployees> result) {
		result.forEach(SalaryDistributionAppl::printInterval);
	}
	
	private static void printInterval(SalaryRangeEmployees e) {
		System.out.printf("Minimal salary: %s, maximum salary: %s\n\n", e.minSalary, e.maxSalary);
		e.employees.forEach(System.out::println);
	}

}
