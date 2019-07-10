package com.axonactive.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.axonactive.dto.DepartmentDTO;
import com.axonactive.entites.DepartmentEntity;

public class DepartmentConverter {

	
	public DepartmentEntity toEntity(DepartmentDTO departmentDTO) {
		if (departmentDTO != null) {
			return DepartmentEntity.builder().id(departmentDTO.getId()).name(departmentDTO.getName()).build();
		}
		return null;
	}

	public DepartmentDTO toDTO(DepartmentEntity deparmentEntity) {
		if (deparmentEntity != null) {
			return new DepartmentDTO(deparmentEntity.getId(), deparmentEntity.getName());
		}
		return null;
	}
	
	public  List<DepartmentDTO> toDTOs(List<DepartmentEntity> deparmentEntities) {
		if (deparmentEntities == null) {
			return Collections.emptyList();
		}
		List<DepartmentDTO> departmentDTOs = new ArrayList<>();
		deparmentEntities.stream().map(each -> toDTO(each)).filter(Objects::nonNull).forEach(departmentDTO -> departmentDTOs.add(departmentDTO));
		return departmentDTOs;
	}

}
