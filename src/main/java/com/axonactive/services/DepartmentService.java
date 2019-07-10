package com.axonactive.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.axonactive.converter.DepartmentConverter;
import com.axonactive.dto.DepartmentDTO;
import com.axonactive.entites.DepartmentEntity;

@Stateless
public class DepartmentService extends GenericService<DepartmentEntity, DepartmentDTO> {

	@EJB
	DepartmentService deptService;
	
	DepartmentConverter departmentConverter = new DepartmentConverter();
	
	public List<DepartmentDTO> getAll() {
		TypedQuery<DepartmentEntity> q = em.createNamedQuery("showDepartmentList", DepartmentEntity.class);
		List<DepartmentEntity> departmentEntities = q.getResultList();
		return departmentConverter.toDTOs(departmentEntities);
	}

	public DepartmentEntity findDepartmentById(int deptId) {
		List<DepartmentEntity> department = em.createNamedQuery("findByDepartmentId", DepartmentEntity.class)
				.setParameter("deptid", deptId).getResultList();
		if (department.isEmpty())
			throw new NoResultException("No source found");
		else
			return department.get(0);
	}

}
