package telran.emploees.services;

import telran.emploees.dto.*;

public interface EmployeesMethods {

	EmployeesCodes addEmploee(Employee employee);
	EmployeesCodes removeEmployee(long id);
	EmployeesCodes updateSalary(long id, int newSalary);
	EmployeesCodes updateDepartment(long id, String newDepartment);
	Employee getEmployee(long id);
	Iterable<Employee> getAllEmployees();
	Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive);
	Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive);
	Iterable<Employee> getEmployeesByDepartment(String department);
	
}
