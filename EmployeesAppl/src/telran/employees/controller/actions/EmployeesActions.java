package telran.employees.controller.actions;

import java.time.LocalDate;
import java.util.*;

import telran.employees.dto.Employee;
import telran.employees.services.EmployeesMethods;
import telran.view.InputOutput;
import telran.view.Item;

public class EmployeesActions {
	
	private static EmployeesMethods employeesService;
	private static List<String> departments;
	private static InputOutput io;

	private EmployeesActions() {}

	public static Item[] getEmployeesItems(EmployeesMethods employeesService, List<String> departments, InputOutput io) {
		EmployeesActions.employeesService = employeesService;
		EmployeesActions.departments = departments;
		EmployeesActions.io = io;
		return getItems();
	}

	private static Item[] getItems() {
		List<Item> adminItems = getAdminItems();
		List<Item> userItems = getUserItems();
		List<Item> exitItems = getExitItems();
		
		adminItems.addAll(userItems);
		adminItems.addAll(exitItems);
		
		return adminItems.toArray(new Item[0]);
	}

	private static List<Item> getAdminItems() {
		List<Item> res = new ArrayList<>();
		res.add(Item.of("Add Employee", EmployeesActions::addEmployee));
		res.add(Item.of("Remove Employee", EmployeesActions::removeEmployee));
		res.add(Item.of("Update Salary", EmployeesActions::updateSalary));
		res.add(Item.of("Update Department", EmployeesActions::updateDepartment));
		return res;
	}

	private static List<Item> getUserItems() {
		List<Item> res = new ArrayList<>();
		res.add(Item.of("Get Employee", EmployeesActions::getEmployee));
		res.add(Item.of("Get All Employees", EmployeesActions::getAllEmployee));
		res.add(Item.of("Get Employess By Salary", EmployeesActions::getEmployeeBySalary));
		res.add(Item.of("Get Employees By Age", EmployeesActions::getEmployeeByAge));
		res.add(Item.of("Get Employees By Department", EmployeesActions::getEmployeeByDepartment));
		res.add(Item.of("Get Departments Salary", EmployeesActions::getDepartmentsSalary));
		return res;
	}
	
	private static List<Item> getExitItems() {
		List<Item> res = new ArrayList<>();
		res.add(Item.of("Save & Exit", EmployeesActions::saveAndExit, true));
		res.add(Item.exit());
		return res;
	}
	
	private static void addEmployee(InputOutput io) {
		var res = employeesService.addEmployee(getNewEmployee());
		io.writeObjectLine(res.toString());
	}

	private static void removeEmployee(InputOutput io) {
		var res = employeesService.removeEmployee(getEmployeeID());
		io.writeObjectLine(res.toString());
	}

	private static void updateSalary(InputOutput io) {
		var res = employeesService.updateSalary(getEmployeeID(), getSalary());
		io.writeObjectLine(res.toString());
	}

	private static void updateDepartment(InputOutput io) {
		employeesService.updateDepartment(getEmployeeID(), selectDepartment());
		io.writeObjectLine("Employee was moved to new department.");
	}

	private static void getEmployee(InputOutput io) {
		var res = employeesService.getEmployee(getEmployeeID());
		io.writeObjectLine(res);
	}

	private static void getAllEmployee(InputOutput io) {
		io.writeObjectLine("List of all Employees:");
		employeesService.getAllEmployees().forEach(io::writeObjectLine);
	}

	private static void getEmployeeBySalary(InputOutput io) {
		int minSalary = io.readInt("Enter minumim salary:");
		int maxSalary = io.readInt("Enter maximum salary:", minSalary, Integer.MAX_VALUE);
		io.writeObjectLine(String.format("List of Employees with salary in range on [%d-%d]", minSalary, maxSalary));
		employeesService.getEmployessBySalary(minSalary, maxSalary).forEach(io::writeObjectLine);
	}

	private static void getEmployeeByAge(InputOutput io) {
		int minAge = io.readInt("Enter minimum age:");
		int maxAge = io.readInt("Enter maximum age: ", minAge, 100);
		var result = employeesService.getEmployeesByAge(minAge, maxAge);
		io.writeObjectLine(String.format("List of Employees with age in range on [%d-%d]", minAge, maxAge));
		result.forEach(io::writeObjectLine);
	}

	private static void getEmployeeByDepartment(InputOutput io) {
		var department = selectDepartment();
		var res = employeesService.getEmployeesByDepartment(department);
		io.writeObjectLine(String.format("List of Emloyees in the «%s» department", department));
		res.forEach(io::writeObjectLine);
	}


	private static void getDepartmentsSalary(InputOutput io) {
		var result = employeesService.getDepartmentsSalary().entrySet();
		io.writeObjectLine("Average salary by department:");
		result.forEach(io::writeObjectLine);
	}
	
	private static void saveAndExit(InputOutput io) {
		var filePath = io.readString("Enter filepath for saving:");
		try {
			employeesService.save(filePath);
			io.writeObjectLine("Changes was saved.");
		} catch (Exception e) {
			throw new RuntimeException("Wrong filepath.");
		}
	}

	private static Employee getNewEmployee() {
		long id = getEmployeeID();
		int salary = getSalary();
		LocalDate birthDate = io.readDate("Enter birthday");
		String department = selectDepartment();
		return new Employee(id, salary, birthDate, department);
	}

	private static long getEmployeeID() {
		return io.readLong("Enter Employee ID:");
	}
	
	private static int getSalary() {
		return io.readInt("Enter salary");
	}

	private static String selectDepartment() {
		return io.readStringOption("Select deparment: " + departments, new HashSet<>(departments));
	}


}
