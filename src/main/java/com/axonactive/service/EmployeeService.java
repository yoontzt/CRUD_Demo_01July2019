package com.axonactive.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entity.EmployeeEntity;

@Stateless
public class EmployeeService extends GenericService<EmployeeEntity> {
	@EJB
	DepartmentService deptService;

	@EJB
	EmployeeService empService;
	
	EmployeeConverter employeeConverter = new EmployeeConverter();
	/**
	 * Find all employee from database
	 * 
	 * @return List of employee
	 */
	public List<EmployeeDTO> getAllEmployeeList() {
		TypedQuery<EmployeeEntity> q = em.createNamedQuery(EmployeeEntity.FIND_ALL, EmployeeEntity.class);
		List<EmployeeEntity> employeeEntities = q.getResultList();
		return employeeConverter.toDTOs(employeeEntities);
	}

	/**
	 * Find employee by Id from database
	 * 
	 * @param id Id of employee
	 * @return Employee Information
	 */
	
	public EmployeeEntity findEmployeeById(int id) {
		return em.find(EmployeeEntity.class, id);
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
		EmployeeEntity newEntity = findEmployeeById(employeeConverter.toEntity(e).getId());
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.update(newEntity);
	}

	public void deleteEmployeeById(Integer id) {
		EmployeeEntity empEntity = findEmployeeById(id);
		this.remove(empEntity);
	}
	
	public void deleteEmployeeForController(EmployeeEntity employeeEntity) {
		EmployeeEntity empEntity = findEmployeeById(employeeEntity.getId());
		if (empEntity == null) {
			throw new NoResultException("No source found");
		}
		this.remove(empEntity);
	}

}
