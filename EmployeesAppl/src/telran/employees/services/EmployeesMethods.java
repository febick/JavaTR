package telran.employees.services;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import telran.employees.dto.*;

public interface EmployeesMethods extends Serializable {
	EmployeesCodes addEmployee(Employee employee);
	EmployeesCodes removeEmployee(long id);
	EmployeesCodes updateSalary(long id, int newSalary);
	EmployeesCodes updateDepartment(long id, String newDepartment);
	Employee getEmployee(long id);
	Iterable<Employee> getAllEmployees();
	Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive);
	Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive);
	Iterable<Employee> getEmployeesByDepartment(String department);
	Map<String, Integer> getDepartmentsSalary(); 
	List<SalaryRangeEmployees> distibutionSalary(int interval);
	void save(String fileName) throws Exception ;
	
	static EmployeesMethods getEmployees(String filePath) throws Exception {
		try (var input = new ObjectInputStream(new FileInputStream(filePath))) {
			return (EmployeesMethods) input.readObject();
		} 
	}
	
}
