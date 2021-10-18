package telran.employess.net;

import telran.employees.dto.Employee;
import telran.employees.dto.UpdateDepartmentData;
import telran.employees.dto.UpdateSalaryData;
import telran.employees.services.EmployeesMethods;
import static telran.employees.api.RequestTypesApi.*;
import java.io.Serializable;
import java.util.*;


import telran.net.ApplProtocolJava;
import telran.net.dto.*;

public class EmployeesProtocol implements ApplProtocolJava {

	public EmployeesMethods employeesMethods;

	public EmployeesProtocol(EmployeesMethods employeesMethods) {
		this.employeesMethods = employeesMethods;
	}

	@Override
	public ResponseJava getResponse(RequestJava request) {
		System.out.println(request.data);
		
		switch (request.requestType) {
		case ADD_EMPLOYEE: return employees_add(request.data);
		case REMOVE_EMPLOYEE: return employees_remove(request.data);
		case UPDATE_SALARY: return employees_salary_update(request.data);
		case UPDATE_DEPARTAMENT: return employees_department_update(request.data);
		case GET_EMPLOYEE: return employees_get(request.data);
		case GET_ALL_EMPLOYEES: return employees(request.data);
		case GET_EMPLOYEES_SALARY: return employees_salary_get(request.data);
		case GET_EMPLOYEES_AGE: return employees_age_get(request.data);
		case GET_EMPLOYEES_DEPARTMENT: return employees_department_get(request.data);
		case GET_DEPARTMENTS_SALARY: return employees_departments_salary_get(request.data);
		case DISTRIBUTION_SALARY: return employees_distribution_salary(request.data);
		default: return new ResponseJava(ResponseCode.WRONG_REQUEST_TYPE, request.requestType);
		}
	}

	private ResponseJava employees_distribution_salary(Serializable data) {
		try {
			return new ResponseJava(ResponseCode.OK, new ArrayList<>(employeesMethods.distibutionSalary((int)data)));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_departments_salary_get(Serializable data) {
		try {
			return new ResponseJava(ResponseCode.OK, (Serializable)employeesMethods.getDepartmentsSalary());
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_department_get(Serializable data) {
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					new ArrayList<Employee>( (Collection<Employee>) employeesMethods.getEmployeesByDepartment( (String) data )));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_age_get(Serializable data) {
		int[] values = (int[]) data;
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					new ArrayList<Employee>( (Collection<Employee>) employeesMethods.getEmployeesByAge(values[0], values[1])));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_salary_get(Serializable data) {
		int[] values = (int[]) data;
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					new ArrayList<Employee>( (Collection<Employee>) employeesMethods.getEmployessBySalary(values[0], values[1])));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees(Serializable data) {
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					new ArrayList<Employee>( (Collection<Employee>) employeesMethods.getAllEmployees()));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_get(Serializable data) {
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					employeesMethods.getEmployee( (long) data));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_department_update(Serializable data) {
		UpdateDepartmentData depData = (UpdateDepartmentData) data;
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					employeesMethods.updateDepartment(depData.id, depData.department));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_salary_update(Serializable data) {
		UpdateSalaryData salData = (UpdateSalaryData) data;
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					employeesMethods.updateSalary(salData.id, salData.salary));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_remove(Serializable data) {
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					employeesMethods.removeEmployee( (long) data));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava employees_add(Serializable data) {
		try {
			return new ResponseJava(
					ResponseCode.OK, 
					employeesMethods.addEmployee( (Employee) data));
		} catch (Exception e) {
			return getWrongDataResponse(e);
		}
	}

	private ResponseJava getWrongDataResponse(Exception e) {
		return new ResponseJava(ResponseCode.WRONG_REQUEST_DATA, e);
	}


}
