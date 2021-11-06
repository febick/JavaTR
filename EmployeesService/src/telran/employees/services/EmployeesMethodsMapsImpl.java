package telran.employees.services;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import telran.employees.dto.*;

public class EmployeesMethodsMapsImpl implements EmployeesMethods {

	private static final long serialVersionUID = 1L;
	private final HashMap<Long, Employee> employees = new HashMap<>();
	private final HashMap<String, List<Employee>> employeesDep = new HashMap<>();
	private final TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private final TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>();

	// Lock Farm
	final static ReentrantReadWriteLock employeesLock = new ReentrantReadWriteLock();
	final static Lock employeesLockRead = employeesLock.readLock();
	final static Lock employeesLockWrite = employeesLock.writeLock();

	final static ReentrantReadWriteLock employeesDepLock = new ReentrantReadWriteLock();
	final static Lock employeesDepLockRead = employeesDepLock.readLock();
	final static Lock employeesDepLockWrite = employeesDepLock.writeLock();

	final static ReentrantReadWriteLock employeesSalaryLock = new ReentrantReadWriteLock();
	final static Lock employeesSalaryLockRead = employeesSalaryLock.readLock();
	final static Lock employeesSalaryLockWrite = employeesSalaryLock.writeLock();

	final static ReentrantReadWriteLock employeesAgeLock = new ReentrantReadWriteLock();
	final static Lock employeesAgeLockRead = employeesAgeLock.readLock();
	final static Lock employeesAgeLockWrite = employeesAgeLock.writeLock();

	public static EmployeesMethods getEmptyEmloyees() {
		return new EmployeesMethodsMapsImpl();
	}

	@Override
	public EmployeesCodes addEmployee(Employee employee) {
		lockWriting(true);
		try {
			var result = employees.putIfAbsent(employee.getId(), employee);
			if (result != null) { return EmployeesCodes.ALREADY_EXISTS; }
			addEmployeeDep(employee);
			addEmployeeSalary(employee);
			addEmployeeAge(employee);
			return EmployeesCodes.OK;
		} finally {
			lockWriting(false);
		}
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
		lockWriting(true);
		try {
			var removedEmployee = employees.remove(id);
			if (removedEmployee == null) { return EmployeesCodes.NOT_FOUND; }
			removeFromDepartmentList(removedEmployee);
			removeFromSalaryList(removedEmployee);
			removeFromAgeList(removedEmployee);
			return EmployeesCodes.OK;
		} finally {
			lockWriting(false);
		}
	}

	private void removeFromAgeList(Employee removedEmployee) {
		employeesAge.compute(removedEmployee.getBirthDate().getYear(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
	}

	private void removeFromSalaryList(Employee removedEmployee) {
		employeesSalary.compute(removedEmployee.getSalary(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
	}

	private void removeFromDepartmentList(Employee removedEmployee) {
		employeesDep.compute(removedEmployee.getDepartment(), (key, value) -> {
			value.remove(removedEmployee);
			if (value.size() == 0) { return null; }
			return value;
		});
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
		lock(employeesLockRead);
		try {
			return employees.get(id);
		} finally {
			unLock(employeesLockRead);
		}
	}

	private static <T> Iterable<Employee> getSubMapResult(TreeMap<T, List<Employee>> treeMap, T from, T to) {
		return treeMap.subMap(from, to).values().parallelStream().flatMap(List::stream).toList();
	}

	@Override
	public Iterable<Employee> getEmployessBySalary(int fromInclusive, int toExclusive) {
		lock(employeesSalaryLockRead);
		try {
			return getSubMapResult(employeesSalary, fromInclusive, toExclusive);
		} finally {
			unLock(employeesSalaryLockRead);
		}
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int fromInclusive, int toExclusive) {
		lock(employeesAgeLockRead);
		try {
			var currentYear = LocalDate.now().getYear();
			var minYear = currentYear - toExclusive + 1;
			var maxYear = currentYear - fromInclusive + 1;
			return getSubMapResult(employeesAge, minYear, maxYear);
		} finally {
			unLock(employeesAgeLockRead);
		}
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		lock(employeesDepLockRead);
		try {
			var result = employeesDep.get(department);
			return result == null ? Collections.emptyList() : result;
		} finally {
			unLock(employeesDepLockRead);
		}
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		lock(employeesLockRead);
		try {
			return employees.values();
		} finally {
			unLock(employeesLockRead);
		}		
	}

	@Override
	public Map<String, Integer> getDepartmentsSalary() {
		lock(employeesDepLockRead);
		try {
			return employeesDep.entrySet().stream()
					.collect(Collectors.toMap(department -> department.getKey(), // Map.Entry::getKey
							list -> (int) list.getValue().stream()
							.mapToInt(empl -> empl.getSalary()) // Employee::getSalary		
							.average().orElse(0)));
		} finally {
			unLock(employeesDepLockRead);
		}
	}

	@Override
	public List<SalaryRangeEmployees> distibutionSalary(int intervalStep) {
		lock(employeesLockRead);
		try {
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
		} finally {
			unLock(employeesLockRead);
		}
	}

	@Override
	public void save(String filePath) throws Exception {
		lockWriting(true);
		try {
			try (var output = new ObjectOutputStream(new FileOutputStream(filePath))) {
				output.writeObject(this);
			} 
		} finally {
			lockWriting(false);
		}
	}

	// Lock managment

	private void lock(Lock lock) {
		lock.lock();
	}

	private void unLock(Lock lock) {
		lock.unlock();
	}

	private void lockWriting(Boolean status) {
		if (status) {
			employeesLockWrite.lock();
			employeesDepLockWrite.lock();
			employeesSalaryLockWrite.lock();
			employeesAgeLockWrite.lock();
		} else {
			employeesLockWrite.unlock();
			employeesDepLockWrite.unlock();
			employeesSalaryLockWrite.unlock();
			employeesAgeLockWrite.unlock();
		}
	}


}
