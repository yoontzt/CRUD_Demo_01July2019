package com.axonactive.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.axonactive.dto.DepartmentDTO;
import com.axonactive.entites.DepartmentEntity;

public class DepartmentConverter {

	
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
	
	public List<DepartmentEntity> toEntities(List<DepartmentDTO> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<DepartmentEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}
	
	public List<DepartmentDTO> toBoms(List<DepartmentEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<DepartmentDTO> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}

}
