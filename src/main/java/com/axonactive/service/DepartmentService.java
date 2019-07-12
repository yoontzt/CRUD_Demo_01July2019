package com.axonactive.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.axonactive.converter.DepartmentConverter;
import com.axonactive.dto.DepartmentDTO;
import com.axonactive.entity.DepartmentEntity;

@Stateless
public class DepartmentService extends GenericService<DepartmentEntity> {

	@EJB
	private DepartmentService deptService;

	private DepartmentConverter departmentConverter = new DepartmentConverter();

	public List<DepartmentDTO> getAllDepartmentList() {
		TypedQuery<DepartmentEntity> query = em.createNamedQuery(DepartmentEntity.FIND_ALL, DepartmentEntity.class);
		List<DepartmentEntity> departmentEntities = query.getResultList();
		return departmentConverter.toDTOs(departmentEntities);
	}

	public DepartmentEntity findDepartmentById(int deptId) {
		return em.createNamedQuery(DepartmentEntity.FIND_BY_ID, DepartmentEntity.class)
				.setParameter("deptid", deptId).getSingleResult();
	}

}
