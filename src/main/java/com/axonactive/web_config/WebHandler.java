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

import com.axonactive.converter.DepartmentConverter;
import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.DepartmentDTO;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entites.DepartmentEntity;
import com.axonactive.services.DepartmentService;
import com.axonactive.services.EmployeeService;

import exception.MyApplicationException;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@ManagedBean(name = "webHandler")
@ViewScoped
public class WebHandler {
	private @Getter @Setter DepartmentDTO department = new DepartmentDTO();
	private @Getter @Setter EmployeeDTO employee = new EmployeeDTO();
	private @Getter @Setter DepartmentEntity departmentEntity=new DepartmentEntity();
	private @Getter @Setter int id;

	@Inject
	private EmployeeService empService;

	@Inject
	private DepartmentService depService;
	
	DepartmentConverter departmentConverter = new DepartmentConverter();
	
	EmployeeConverter employeeConverter = new EmployeeConverter();

	private @Getter @Setter List<EmployeeDTO> employeeList = new ArrayList<>();

	private @Getter @Setter List<DepartmentDTO> departmentList = new ArrayList<>();

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

	public void updateEmployeeFromPage() throws MyApplicationException {
		employee.setDepartment(department);
		empService.updateEmployee(employee);
		employeeList =empService.getAll();
		PrimeFaces.current().executeScript("PF('UpdateEmployee').hide()");
	}

	public void deleteEmployeeFromPage(EmployeeDTO employeeDTO)  throws MyApplicationException {
		empService.deleteEmployeeForController(employeeConverter.toEntity(employeeDTO));
		employeeList =empService.getAll();
	}

	public void viewEmployee(EmployeeDTO emp) {
		setEmployee(emp);
		setId(emp.getDepartment().getId());
		PrimeFaces.current().executeScript("PF('UpdateEmployee').show()");
	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = departmentConverter.toDTO(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
	
	private void clear() {
		setEmployee(null);
	}
}
