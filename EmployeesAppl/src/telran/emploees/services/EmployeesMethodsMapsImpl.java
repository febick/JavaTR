package telran.emploees.services;

import java.time.LocalDate;
import java.util.*;
import telran.emploees.dto.Employee;
import telran.emploees.dto.EmployeesCodes;

public class EmployeesMethodsMapsImpl implements EmployeesMethods {

	private HashMap<Long, Employee> employees = new HashMap<>();
	private HashMap<String, List<Employee>> employeesDep = new HashMap<>();
	private TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>();

	@Override
	public EmployeesCodes addEmploee(Employee employee) {
		var result = employees.putIfAbsent(employee.getId(), employee);
		addEmploeeDep(employee);
		addEmploeeSalary(employee);
		addEmploeeAge(employee);
		return result != null ? EmployeesCodes.ALREADY_EXISTS : EmployeesCodes.OK;
	}

	private void addEmploeeAge(Employee employee) {
		employeesAge.computeIfAbsent(employee.getBirthDate().getYear(), __ -> new LinkedList<>()).add(employee);
	}

	private void addEmploeeSalary(Employee employee) {
		employeesSalary.computeIfAbsent(employee.getSalary(), __ -> new LinkedList<Employee>()).add(employee);
	}

	private void addEmploeeDep(Employee employee) {
		employeesDep.computeIfAbsent(employee.getDepartment(), __ -> new LinkedList<Employee>()).add(employee);
	}

	@Override
	public EmployeesCodes removeEmployee(long id) {
		var removedEmployee = employees.remove(id);
		if (removedEmployee == null) { return EmployeesCodes.NOT_FOUND; }
		removeFromDepartmentList(removedEmployee);
		removeFromSalaryList(removedEmployee);
		removeFromAgeList(removedEmployee);
		return EmployeesCodes.OK;
	}

	private void removeFromAgeList(Employee removedEmployee) {
		employeesAge.compute(removedEmployee.getBirthDate().getYear(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
		//		employeesAge.get(removedEmployee.getBirthDate().getYear()).remove(removedEmployee);
	}

	private void removeFromSalaryList(Employee removedEmployee) {
		employeesSalary.compute(removedEmployee.getSalary(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
		//		employeesSalary.get(removedEmployee.getSalary()).remove(removedEmployee);
	}

	private void removeFromDepartmentList(Employee removedEmployee) {
		employeesDep.compute(removedEmployee.getDepartment(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
		//		employeesDep.get(removedEmployee.getDepartment()).remove(removedEmployee);
	}

	@Override
	public EmployeesCodes updateSalary(long id, int newSalary) {
		var replacingEmploeey = getEmployee(id);
		if (replacingEmploeey == null) { return EmployeesCodes.NOT_FOUND; }
		Employee newEmploeey = new Employee(id, newSalary, replacingEmploeey.getBirthDate(), replacingEmploeey.getDepartment());
		replaceEmployee(replacingEmploeey, newEmploeey);
		return EmployeesCodes.OK;
	}

	@Override
	public EmployeesCodes updateDepartment(long id, String newDepartment) {
		var replacingEmploeey = getEmployee(id);
		if (replacingEmploeey == null) { return EmployeesCodes.NOT_FOUND; }
		Employee newEmploeey = new Employee(id, replacingEmploeey.getSalary(), replacingEmploeey.getBirthDate(), newDepartment);
		replaceEmployee(replacingEmploeey, newEmploeey);
		return EmployeesCodes.OK;
	}
	
	private void replaceEmployee(Employee replacingEmploeey, Employee newEmploeey) {
		removeEmployee(replacingEmploeey.getId());
		addEmploee(newEmploeey);
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive) {
		List<Employee> result = new ArrayList<>();
		employeesSalary.subMap(fromInclusive, toExclusive).values().forEach(result::addAll);
		return result;
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive) {
		if (fromInclusive > toExclusive || fromInclusive < 0 || toExclusive < 0) {
			throw new IllegalArgumentException();
		}
		List<Employee> result = new ArrayList<>();
		var currentYear = LocalDate.now().getYear();
		var minYear = toExclusive - currentYear;
		var maxYear = fromInclusive - currentYear;		
		employeesAge.subMap(-minYear, -maxYear).values().forEach(result::addAll);
		return result;
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		return employeesDep.getOrDefault(department, new ArrayList<>());
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		return employees.values();		
	}

}
