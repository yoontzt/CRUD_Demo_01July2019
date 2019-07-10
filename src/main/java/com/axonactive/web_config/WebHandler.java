package com.axonactive.web_config;

import java.io.Serializable;
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
import com.axonactive.exception.InvalidValueException;
import com.axonactive.services.DepartmentService;
import com.axonactive.services.EmployeeService;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@ManagedBean(name = "webHandler")
@ViewScoped
public class WebHandler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7889451587603628722L;
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
		employeeList = empService.getAllEmployeeList();
		departmentList = depService.getAllDepartmentList();
		if (departmentList.isEmpty())
			throw new NoResultException("No source found");
		else
			department = departmentList.get(0);
	}
	
	public void addNewEmployee() {
		employee.setDepartment(department);
		empService.addEmployee(employee);
		employeeList = empService.getAllEmployeeList();	
		clear();
		PrimeFaces.current().executeScript("PF('addEmployee').hide()");
	}

	public void updateEmployeeFromPage() throws InvalidValueException {
		employee.setDepartment(department);
		empService.updateEmployee(employee);
		employeeList =empService.getAllEmployeeList();
		PrimeFaces.current().executeScript("PF('UpdateEmployee').hide()");
	}

	public void deleteEmployeeFromPage(EmployeeDTO employeeDTO)  throws InvalidValueException {
		empService.deleteEmployeeForController(employeeConverter.toEntity(employeeDTO));
		employeeList =empService.getAllEmployeeList();
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
		employee.setName("");
		employee.setAge("");
		employee.setEmail("");
	}
}
