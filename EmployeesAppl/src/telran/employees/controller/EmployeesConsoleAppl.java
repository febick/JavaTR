package telran.employees.controller;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

import telran.employees.controller.actions.EmployeesActions;
import telran.employees.net.*;
import telran.employees.services.*;
import telran.net.*;
import telran.view.*;

public class EmployeesConsoleAppl {

	private static final String DEFAULT_FILE_PATH = "data.base";
	private static InputOutput io = new ConsoleInputOutput();
	private static EmployeesMethods employeesService = null;
	private static final String BASE_PACKAGE = "telran.net.";

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("application.properties"));
		
		String type = props.getProperty("TRANSPORT");
		String host = props.getProperty("HOST");
		int port = Integer.parseInt(props.getProperty("PORT"));

		var clazz = Class.forName(BASE_PACKAGE + type);
		
		try {
			var protocol = (NetJavaClient) clazz.getConstructor(String.class, int.class).newInstance(host, port);
			employeesService = new EmployeesProxyNetJava(protocol);
		} catch (Exception e) {
			employeesService = EmployeesMethodsMapsImpl.getEmptyEmloyees();
			io.writeObjectLine("No persistent file, new DB was created.\n");
		}

		Menu menu = new Menu("Employees Managment", getItems());
		
		try {
			menu.perform(io);
		} catch (EndOfInputException e) {
			try {
				employeesService.save(DEFAULT_FILE_PATH);
			} catch (Exception errorSavingFileException) {
				io.writeObjectLine(errorSavingFileException.getLocalizedMessage());
			};
		}
		
	}

	private static Item[] getItems() {
		Item[] items = EmployeesActions.getEmployeesItems(
				employeesService, 
				Arrays.asList("PR", "QA", "IT"), 
				io);
		return items;
	}

}
