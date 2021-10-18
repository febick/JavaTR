package telran.employees.controller.actions;

import java.time.LocalDate;
import java.util.*;

import telran.employees.dto.Employee;
import telran.employees.dto.EmployeesCodes;
import telran.employees.services.EmployeesMethods;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class EmployeesActions {
	
	private static EmployeesMethods employeesService;
	private static List<String> departments;
	private static InputOutput io;

	private EmployeesActions() {}

	public static Item[] getEmployeesItems(EmployeesMethods employeesService, List<String> departments, InputOutput io) {
		EmployeesActions.employeesService = employeesService;
		EmployeesActions.departments = departments;
		EmployeesActions.io = io;
		return selectItems();
	}
	
	private static Item[] selectItems() {
		List<Item> res = new ArrayList<>();
		Menu adminMenu = new Menu("Adminstator", getAdminItems().toArray(new Item[0]));
		Menu userMenu = new Menu("User", getUserItems().toArray(new Item[0]));
		res.add(adminMenu);
		res.add(userMenu);
		res.addAll(getExitItems());
		return res.toArray(new Item[0]);
	}
	
	private static List<Item> getAdminItems() {
		List<Item> res = new ArrayList<>();
		try {
			res.add(Item.of("Add Employee", t -> {
				try {
					addEmployee(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Remove Employee", t -> {
				try {
					removeEmployee(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Update Salary", t -> {
				try {
					updateSalary(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Update Department", t -> {
				try {
					updateDepartment(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.back());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private static List<Item> getUserItems() {
		List<Item> res = new ArrayList<>();
		try {
			res.add(Item.of("Get Employee", t -> {
				try {
					getEmployee(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Get All Employees", t -> {
				try {
					getAllEmployee(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Get Employess By Salary", t -> {
				try {
					getEmployeeBySalary(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Get Employees By Age", t -> {
				try {
					getEmployeeByAge(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Get Employees By Department", t -> {
				try {
					getEmployeeByDepartment(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.of("Get Departments Salary", t -> {
				try {
					getDepartmentsSalary(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.back());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	private static List<Item> getExitItems() {
		List<Item> res = new ArrayList<>();
		try {
			res.add(Item.of("Save & Exit", EmployeesActions::saveAndExit, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res.add(Item.exit());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	private static void addEmployee(InputOutput io) throws Exception {
		var res = employeesService.addEmployee(getNewEmployee());
		io.writeObjectLine(res == EmployeesCodes.ALREADY_EXISTS ? "Employee already exists" : "Employee added");
	}

	private static void removeEmployee(InputOutput io) throws Exception {
		var res = employeesService.removeEmployee(getEmployeeID());
		io.writeObjectLine(res == EmployeesCodes.NOT_FOUND ? "Employee not found" : "Employee removed");
	}

	private static void updateSalary(InputOutput io) throws Exception {
		var res = employeesService.updateSalary(getEmployeeID(), getSalary());
		io.writeObjectLine(res == EmployeesCodes.NOT_FOUND ? "Employee not found" : "Salary updated");
	}

	private static void updateDepartment(InputOutput io) throws Exception {
		var res = employeesService.updateDepartment(getEmployeeID(), selectDepartment());
		io.writeObjectLine(res == EmployeesCodes.OK ? "Employee was moved to new department." : "Employee not found or updated no need.");
	}

	private static void getEmployee(InputOutput io) throws Exception {
		var res = employeesService.getEmployee(getEmployeeID());
		io.writeObjectLine(res == null ? "Employee not found" : res);
	}

	private static void getAllEmployee(InputOutput io) throws Exception {
		io.writeObjectLine("List of all Employees:");
		displayEmployees(employeesService.getAllEmployees());
	}

	private static void getEmployeeBySalary(InputOutput io) throws Exception {
		int minSalary = io.readInt("Enter minumim salary:");
		int maxSalary = io.readInt("Enter maximum salary:", minSalary + 1, Integer.MAX_VALUE);
		io.writeObjectLine(String.format("List of Employees with salary in range on [%d-%d]:\n", minSalary, maxSalary));
		displayEmployees(employeesService.getEmployessBySalary(minSalary, maxSalary));
	}

	private static void getEmployeeByAge(InputOutput io) throws Exception {
		int minAge = io.readInt("Enter minimum age:");
		int maxAge = io.readInt("Enter maximum age: ", minAge + 1, 100);
		io.writeObjectLine(String.format("List of Employees with age in range on [%d-%d]:\n", minAge, maxAge));
		displayEmployees(employeesService.getEmployeesByAge(minAge + 1, maxAge));
	}

	private static void getEmployeeByDepartment(InputOutput io) throws Exception {
		var department = selectDepartment();
		io.writeObjectLine(String.format("List of Emloyees in the «%s» department:\n", department));
		displayEmployees(employeesService.getEmployeesByDepartment(department));
	}


	private static void getDepartmentsSalary(InputOutput io) throws Exception {
		io.writeObjectLine("Average salary by department:\n");
		employeesService.getDepartmentsSalary()
		.forEach((department, salary) -> 
			io.writeObjectLine(String
					.format("Department: %s,\tAverage salary: %d", department, salary)));
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
	
	private static void displayEmployees(Iterable<Employee> employees) {
		if (employees.iterator().hasNext()) {
			employees.forEach(io::writeObjectLine);
		} else {
			io.writeObjectLine("No employees.");
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
