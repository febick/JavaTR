package telran.employees.dto;

import java.io.Serializable;
import java.util.List;

public class SalaryRangeEmployees implements Serializable {

	private static final long serialVersionUID = 1L;
	public int minSalary;
	public int maxSalary;
	public List<Employee> employees; 
	
	public SalaryRangeEmployees(int minSalary, int maxSalary, List<Employee> employees) {
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.employees = employees;
	}
	
		
}
