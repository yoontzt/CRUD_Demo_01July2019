package com.axonactive.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entites.EmployeeEntity;

@Stateless
public class EmployeeService extends GenericService<EmployeeEntity, EmployeeDTO> {
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
	public List<EmployeeDTO> getAll() {
		TypedQuery<EmployeeEntity> q = em.createNamedQuery("showEmployeeList", EmployeeEntity.class);
		List<EmployeeEntity> employeeEntities = q.getResultList();
		return employeeConverter.toBoms(employeeEntities);
	}

	/**
	 * Find employee by Id from database
	 * 
	 * @param id Id of empl
	 * @return Employee Information
	 */
	
	public EmployeeEntity findById(int id) {
		return em.find(EmployeeEntity.class, id);
	}

	public void addEmployee(EmployeeDTO e) {
		EmployeeEntity newEntity = employeeConverter.toEntity(e);
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.save(newEntity);
	}

	public void updateEmployee(EmployeeDTO e) {
		EmployeeEntity newEntity = findById(employeeConverter.toEntity(e).getId());
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.update(newEntity);
	}

	public void deleteEmployeeForREST(EmployeeEntity empEntity) {
		this.remove(empEntity);
	}
	
	public void deleteEmployeeForController(EmployeeEntity employeeEntity) {
		EmployeeEntity empEntity = findById(employeeEntity.getId());
		if (empEntity == null) {
			throw new NoResultException("No source found");
		}
		this.remove(empEntity);
	}

}
