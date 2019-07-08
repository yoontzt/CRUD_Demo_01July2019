package com.axonactive.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.axonactive.bom.Employee;
import com.axonactive.entites.EmployeeEntity;

@Stateless
public class EmployeeService extends GenericService<EmployeeEntity, Employee> {
	@EJB
	DepartmentService deptService;

	@EJB
	EmployeeService empService;

	public EmployeeService() {
		super();
	}

	public List<Employee> showAll() {
		TypedQuery<EmployeeEntity> q = em.createNamedQuery("showEmployeeList", EmployeeEntity.class);
		List<EmployeeEntity> employeeEntities = q.getResultList();
		return empService.toBoms(employeeEntities);
	}

	public EmployeeEntity findById(int id) {
		EmployeeEntity newemp = em.find(EmployeeEntity.class, id);
		return newemp;
	}

	public void addEmployee(Employee e) {
		EmployeeEntity newEntity = empService.toEntity(e);
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.save(newEntity);
	}

	public void updateEmployee(Employee e) {
		EmployeeEntity newEntity = findById(empService.toEntity(e).getId());
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
		System.out.println("Error");
		}
		this.remove(empEntity);
	}

	@Override
	public EmployeeEntity toEntity(Employee bom) {
		if (bom != null) {
			return EmployeeEntity.builder().id(bom.getId()).name(bom.getName()).email(bom.getEmail()).age(bom.getAge())
					.department(deptService.toEntity(bom.getDepartment())).build();
		}
		return null;
	}

	@Override
	public Employee toBom(EmployeeEntity entity) {
		if (entity != null) {
			return  new Employee(entity.getId(), entity.getName(), entity.getAge(), entity.getEmail(),
					deptService.toBom(entity.getDepartment()));
		}
		return null;
	}
}
