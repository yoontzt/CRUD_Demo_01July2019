package com.axonactive.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO {
	private int id;
	
	@Size(min = 2, max = 100, message = "{employee.name.Size}")
	private String name;
	private String age;
	private String email;
	private DepartmentDTO department;
}
