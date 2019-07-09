package com.axonactive.web_config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.primefaces.PrimeFaces;

import com.axonactive.bom.Department;
import com.axonactive.bom.Employee;
import com.axonactive.entites.DepartmentEntity;
import com.axonactive.services.DepartmentService;
import com.axonactive.services.EmployeeService;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@ManagedBean(name = "WebHandler")
@ViewScoped
public class WebHandler {
	private @Getter @Setter Department department = new Department();
	private @Getter @Setter Employee employee = new Employee();
	private @Getter @Setter DepartmentEntity departmentEntity=new DepartmentEntity();
	private @Getter @Setter int id;

	@Inject
	private EmployeeService empService;

	@Inject
	private DepartmentService depService;

	private @Getter @Setter List<Employee> employeeList = new ArrayList<>();

	private @Getter @Setter List<Department> departmentList = new ArrayList<>();

	@PostConstruct
	public void init() {
		employeeList = empService.getAll();
		departmentList = depService.getAll();
		if (departmentList.isEmpty())
			throw new NoResultException("No source found");
		else
			department = departmentList.get(0);
	}

	public void addNewEmployee() {
		employee.setDepartment(department);
		empService.addEmployee(employee);
		employeeList = empService.getAll();
		clear();
		PrimeFaces.current().executeScript("PF('addEmployee').hide()");
	}

	public void updateEmployeeFromPage() {
		employee.setDepartment(department);
		empService.updateEmployee(employee);
		employeeList =empService.getAll();
		PrimeFaces.current().executeScript("PF('UpdateEmployee').hide()");
	}

	public void deleteEmployeeFromPage(Employee employeeBOM) {
		empService.deleteEmployeeForController(empService.toEntity(employeeBOM));
		employeeList =empService.getAll();
	}

	public void viewEmployee(Employee emp) {
		setEmployee(emp);
		setId(emp.getDepartment().getId());
		PrimeFaces.current().executeScript("PF('UpdateEmployee').show()");
	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = depService.toBom(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
	
	private void clear() {
		setEmployee(null);
	}
}
