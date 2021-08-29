package telran.employees.services;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import telran.employees.dto.Employee;
import telran.employees.dto.EmployeesCodes;
import telran.employees.dto.SalaryRangeEmployees;

public class EmployeesMethodsMapsImpl implements EmployeesMethods {

	private static final long serialVersionUID = 1L;
	private HashMap<Long, Employee> employees = new HashMap<>();
	private HashMap<String, List<Employee>> employeesDep = new HashMap<>();
	private TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>();

//	public static EmployeesMethods getEmployees(String filePath) throws Exception {
//		try (var input = new ObjectInputStream(new FileInputStream(filePath))) {
//			return (EmployeesMethods) input.readObject();
//		} 
//	}
	
	public static EmployeesMethods getEmptyEmloyees() {
		return new EmployeesMethodsMapsImpl();
	}

	@Override
	public EmployeesCodes addEmployee(Employee employee) {
		var result = employees.putIfAbsent(employee.getId(), employee);
		if (result != null) { return EmployeesCodes.ALREADY_EXISTS; }
		addEmployeeDep(employee);
		addEmployeeSalary(employee);
		addEmployeeAge(employee);
		return EmployeesCodes.OK;
	}
	
	private void addEmployeeAge(Employee employee) {
		employeesAge.computeIfAbsent(employee.getBirthDate().getYear(), __ -> new LinkedList<>()).add(employee);
	}

	private void addEmployeeSalary(Employee employee) {
		employeesSalary.computeIfAbsent(employee.getSalary(), __ -> new LinkedList<Employee>()).add(employee);
	}

	private void addEmployeeDep(Employee employee) {
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
		var replacingEmployee = getEmployee(id);
		if (replacingEmployee == null) { return EmployeesCodes.NOT_FOUND; }
		if (replacingEmployee.getSalary() == newSalary) { return EmployeesCodes.NO_UPDATE_REQUIRED; }
		Employee newEmployeeye = new Employee(id, newSalary, replacingEmployee.getBirthDate(), replacingEmployee.getDepartment());
		replaceEmployee(replacingEmployee, newEmployeeye);
		return EmployeesCodes.OK;
	}

	@Override
	public EmployeesCodes updateDepartment(long id, String newDepartment) {
		var replacingEmployee = getEmployee(id);
		if (replacingEmployee == null) { return EmployeesCodes.NOT_FOUND; }
		if (replacingEmployee.getDepartment().equals(newDepartment)) { return EmployeesCodes.NO_UPDATE_REQUIRED; }
		Employee newEmployee = new Employee(id, replacingEmployee.getSalary(), replacingEmployee.getBirthDate(), newDepartment);
		replaceEmployee(replacingEmployee, newEmployee);
		return EmployeesCodes.OK;
	}

	private void replaceEmployee(Employee replacingEmploeey, Employee newEmploeey) {
		removeEmployee(replacingEmploeey.getId());
		addEmployee(newEmploeey);
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	private static <T> Iterable<Employee> getSubMapResult(TreeMap<T, List<Employee>> treeMap, T from, T to) {
		return treeMap.subMap(from, to).values().parallelStream().flatMap(List::stream).toList();
	}

	@Override
	public Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive) {
		return getSubMapResult(employeesSalary, fromInclusive, toExclusive);
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive) {
		var currentYear = LocalDate.now().getYear();
		var minYear = currentYear - toExclusive + 1;
		var maxYear = currentYear - fromInclusive + 1;		
		return getSubMapResult(employeesAge, minYear, maxYear);
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		var result = employeesDep.get(department);
		return result == null ? Collections.emptyList() : result;
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		return employees.values();		
	}

	@Override
	public Map<String, Integer> getDepartmentsSalary() {
		return employeesDep
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						department -> department.getKey(), // Map.Entry::getKey
						list -> (int) list.getValue() 
						.stream()
						.mapToInt(empl -> empl.getSalary()) // Employee::getSalary
						.average().orElse(0)));
	}

	@Override
	public List<SalaryRangeEmployees> distibutionSalary(int intervalStep) {
		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> 
				e.getSalary() / intervalStep, 
				TreeMap::new, 
				Collectors.toList()))
				.entrySet()
				.stream()
				.map(e -> {
					int nInterval = e.getKey();
					int minSalary = nInterval * intervalStep;
					return new SalaryRangeEmployees(
							minSalary, 
							minSalary + intervalStep, 
							e.getValue()); 
				})
				.toList();
	}

	@Override
	public void save(String filePath) throws Exception {
		try (var output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(this);
		}
	}



}
