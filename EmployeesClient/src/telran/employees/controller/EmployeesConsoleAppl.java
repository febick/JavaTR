package telran.employees.controller;

import java.util.Arrays;
import telran.employees.controller.actions.EmployeesActions;
import telran.employees.net.*;
import telran.employees.services.*;
import telran.net.UdpJavaClient;
import telran.view.*;

public class EmployeesConsoleAppl {

	private static final String DEFAULT_FILE_PATH = "data.base";
	private static final String HOST = "localhost";
	private static final int PORT = 5000;
	private static InputOutput io = new ConsoleInputOutput();
	private static EmployeesMethods employeesService = null;

	public static void main(String[] args) throws Exception {
		String filePath = args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
		
		try {
			employeesService = new EmployeesProxyNetJava(new UdpJavaClient(HOST, PORT));
//			employeesService = EmployeesMethods.getEmployees(filePath);
//			employeesService = new EmployeesProxyTcpJava("localhost", 5000);
		} catch (Exception e) {
			employeesService = EmployeesMethodsMapsImpl.getEmptyEmloyees();
			io.writeObjectLine("No persistent file, new DB was created.\n");
		}

		Menu menu = new Menu("Employees Managment", getItems());
		
		try {
			menu.perform(io);
		} catch (EndOfInputException e) {
			try {
				employeesService.save(filePath);
			} catch (Exception errorSavingFileException) {
				io.writeObjectLine(errorSavingFileException.getLocalizedMessage());
			};
		}
		
	}

	private static Item[] getItems() throws Exception {
		Item[] items = EmployeesActions.getEmployeesItems(
				employeesService, 
				Arrays.asList("PR", "QA", "IT"), 
				io);
		return items;
	}

}
