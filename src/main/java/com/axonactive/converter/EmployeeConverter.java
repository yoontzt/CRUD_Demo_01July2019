package com.axonactive.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entites.EmployeeEntity;

public class EmployeeConverter {

	DepartmentConverter departmentConverter = new DepartmentConverter();
	
	public EmployeeEntity toEntity(EmployeeDTO bom) {
		if (bom != null) {
			return EmployeeEntity.builder().id(bom.getId()).name(bom.getName()).email(bom.getEmail()).age(bom.getAge())
					.department(departmentConverter.toEntity(bom.getDepartment())).build();
		}
		return null;
	}

	
	public EmployeeDTO toBom(EmployeeEntity entity) {
		if (entity != null) {
			return  new EmployeeDTO(entity.getId(), entity.getName(), entity.getAge(), entity.getEmail(),
					departmentConverter.toBom(entity.getDepartment()));
		}
		return null;
	}
	
	public List<EmployeeDTO> toBoms(List<EmployeeEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<EmployeeDTO> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}
}
