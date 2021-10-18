package telran.employees.services;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import telran.employees.dto.*;

public interface EmployeesMethods extends Serializable {
	EmployeesCodes addEmployee(Employee employee) throws Exception;
	EmployeesCodes removeEmployee(long id) throws Exception;
	EmployeesCodes updateSalary(long id, int newSalary) throws Exception;
	EmployeesCodes updateDepartment(long id, String newDepartment) throws Exception;
	Employee getEmployee(long id) throws Exception;
	Iterable<Employee> getAllEmployees() throws Exception;
	Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive) throws Exception;
	Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive) throws Exception;
	Iterable<Employee> getEmployeesByDepartment(String department) throws Exception;
	Map<String, Integer> getDepartmentsSalary() throws Exception; 
	List<SalaryRangeEmployees> distibutionSalary(int interval) throws Exception;
	void save(String fileName) throws Exception ;
	
	static EmployeesMethods getEmployees(String filePath) throws Exception {
		try (var input = new ObjectInputStream(new FileInputStream(filePath))) {
			return (EmployeesMethods) input.readObject();
		} 
	}
	
}
