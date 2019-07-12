package com.axonactive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = DepartmentEntity.FIND_BY_ID, query = "SELECT d FROM DepartmentEntity d where d.id = :deptid")
@NamedQuery(name = DepartmentEntity.FIND_ALL, query = "select d from DepartmentEntity d")
@Entity
@Table(name = "department")
public class DepartmentEntity {
	private static final String PREFIX = "com.axonactive.entites.Department.";
	
	public static final String FIND_BY_ID= PREFIX + "find by Id";
	
	public static final String FIND_ALL= PREFIX + "find all";
	
	@Id
	private int id;

	@Column(name = "name")
	private String name;
}