package telran.employess.controller;

import java.io.FileInputStream;
import java.util.Properties;

import telran.employees.services.*;
import telran.employess.net.EmployeesProtocol;
import telran.net.ApplProtocolJava;

public class EmployeesServerAppl {
	
	private static final String BASE_PACKAGE = "telran.net.";

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("application.properties"));
		
		String type = props.getProperty("TRANSPORT");
		int port = Integer.parseInt(props.getProperty("PORT"));
		var protocol = new EmployeesProtocol(EmployeesMethods.getEmployees("employees.data"));
		
		var clazz = Class.forName(BASE_PACKAGE + type);
		var server = (Runnable) clazz.getConstructor(int.class, ApplProtocolJava.class).newInstance(port, protocol);
		server.run();

	}

}
