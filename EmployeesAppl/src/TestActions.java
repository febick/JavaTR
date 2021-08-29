import java.util.ArrayList;
import java.util.List;

import telran.employees.controller.actions.EmployeesActions;
import telran.employees.services.EmployeesMethods;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Menu;

public class TestActions {

	public static void main(String[] args) throws Exception {
	    EmployeesMethods employeesService = EmployeesMethods.getEmployees("data.base");
	    List<String> departments = new ArrayList<String>();
	    InputOutput inputOutput = new ConsoleInputOutput();

	    departments.add("PR");
	    departments.add("QA");
	    departments.add("IT");
	    
	    Menu menu = new Menu("Employee Appl", EmployeesActions.getEmployeesItems(employeesService, departments, inputOutput));
	    menu.perform(inputOutput);

	  }
}
