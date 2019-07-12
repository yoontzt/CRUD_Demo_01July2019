package com.axonactive.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entity.EmployeeEntity;
import com.axonactive.exception.InvalidValueException;

@Stateless
public class EmployeeService extends GenericService<EmployeeEntity> {
	@EJB
	private DepartmentService deptService;

	@EJB
	private EmployeeService empService;
	
	private EmployeeConverter employeeConverter = new EmployeeConverter();
	
	public List<EmployeeDTO> getAllEmployeeList() {
		TypedQuery<EmployeeEntity> q = em.createNamedQuery(EmployeeEntity.FIND_ALL, EmployeeEntity.class);
		List<EmployeeEntity> employeeEntities = q.getResultList();
		return employeeConverter.toDTOs(employeeEntities);
	}
	
	public EmployeeDTO findEmployeeById(int id) {
		EmployeeDTO employee = employeeConverter.toDTO(em.find(EmployeeEntity.class, id));	
		if(Objects.isNull(employee)) {
			throw new InvalidValueException("Requested id is not in the list !!");
		}
		return employee;
	}

	public void addEmployee(EmployeeDTO employee) {
		EmployeeEntity newEntity = employeeConverter.toEntity(employee);
		newEntity.setName(employee.getName());
		newEntity.setAge(employee.getAge());
		newEntity.setEmail(employee.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(employee.getDepartment().getId()));
		this.save(newEntity);
	}

	public void updateEmployee(EmployeeDTO e) {
		EmployeeEntity newEntity = employeeConverter.toEntity(findEmployeeById(e.getId()));
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.update(newEntity);
	}

	public void deleteEmployeeById(Integer id) {
		EmployeeEntity empEntity = employeeConverter.toEntity(findEmployeeById(id));
		this.remove(empEntity);
	}
	
}
