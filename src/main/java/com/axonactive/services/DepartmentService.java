package com.axonactive.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.axonactive.dto.DepartmentDTO;
import com.axonactive.entites.DepartmentEntity;

@Stateless
public class DepartmentService extends GenericService<DepartmentEntity, DepartmentDTO> {

	@EJB
	DepartmentService deptService;
	
	public List<DepartmentDTO> getAll() {
		TypedQuery<DepartmentEntity> q = em.createNamedQuery("showDepartmentList", DepartmentEntity.class);
		List<DepartmentEntity> departmentEntities = q.getResultList();
		return deptService.toBoms(departmentEntities);
	}

	public DepartmentEntity findDepartmentById(int deptId) {
		List<DepartmentEntity> department = em.createNamedQuery("findByDepartmentId", DepartmentEntity.class)
				.setParameter("deptid", deptId).getResultList();
		if (department.isEmpty())
			throw new NoResultException("No source found");
		else
			return department.get(0);
	}

	

	public DepartmentEntity toEntity(DepartmentDTO bom) {
		if (bom != null) {
			return new DepartmentEntity(bom.getId(), bom.getName());
		}
		return null;
	}

	public DepartmentDTO toBom(DepartmentEntity entity) {
		if (entity != null) {
			return new DepartmentDTO(entity.getId(), entity.getName());
		}
		return null;
	}
	@Override
	public List<DepartmentEntity> toEntities(List<DepartmentDTO> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<DepartmentEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}
	@Override
	public List<DepartmentDTO> toBoms(List<DepartmentEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<DepartmentDTO> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}

}
