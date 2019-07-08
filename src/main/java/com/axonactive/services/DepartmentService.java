package com.axonactive.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.axonactive.bom.Department;
import com.axonactive.entites.DepartmentEntity;
import com.axonactive.entites.EmployeeEntity;

@Stateless
public class DepartmentService extends GenericService<DepartmentEntity, Department> {

	@EJB
	DepartmentService deptService;

	public DepartmentEntity findDepartmentById(int deptId) {
		List<DepartmentEntity> department = em.createNamedQuery("findByDepartmentId", DepartmentEntity.class)
				.setParameter("deptid", deptId).getResultList();
		if (department.isEmpty())
			throw new NoResultException("No source found");
		else
			return department.get(0);
	}

	public List<Department> showAll() {
		TypedQuery<DepartmentEntity> q = em.createNamedQuery("showDepartmentList", DepartmentEntity.class);
		List<DepartmentEntity> departmentEntities = q.getResultList();
		return deptService.toBoms(departmentEntities);
	}

	public DepartmentEntity toEntity(Department bom) {
		if (bom != null) {
			return new DepartmentEntity(bom.getId(), bom.getName());
		}
		return null;
	}

	public Department toBom(DepartmentEntity entity) {
		if (entity != null) {
			return new Department(entity.getId(), entity.getName());
		}
		return null;
	}
	@Override
	public List<DepartmentEntity> toEntities(List<Department> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<DepartmentEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}
	@Override
	public List<Department> toBoms(List<DepartmentEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<Department> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}

}
