package com.axonactive.converter.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.DepartmentDTO;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.restconfiguration.EmployeeResource;
import com.axonactive.service.EmployeeService;

@RunWith(PowerMockRunner.class)
public class EmployeeResourceTest {
	
	@InjectMocks
	EmployeeResource employeeResource;
	
	@Mock
	EmployeeConverter employeeConverter;
	
	@Mock
	EmployeeService employeeService;
	
	@Test
	public void testGetAllList_ShouldReturnEmployeeList_WhenListExist() {
		List<EmployeeDTO> expected = Arrays.asList(createEmployeeDTO(),createEmployeeDTO());
		List<EmployeeDTO> employeeDTO = Arrays.asList(createEmployeeDTO(),createEmployeeDTO());
		
		Mockito.when(employeeService.getAllEmployeeList()).thenReturn(employeeDTO);
		
		assertEquals(expected, employeeResource.getAllList());
	}
	
	
	@Test
	public void testGetEmployeeById_ShouldReturnEntity_WhenValidEmployeeIdIsGiven() {
		Mockito.when(employeeService.findEmployeeById(1)).thenReturn(createEmployeeDTO());
	
		
		Response actual = employeeResource.getEmployeeById(1);
		assertEquals(200, actual.getStatus()); 
		assertEquals(createEmployeeDTO(),actual.getEntity());
	}
	
	@Test
	public void testAddEmployee_ShouldReturnOKStatusResponse_WhenEmployeeDTOIsGiven() {
		Response actual = employeeResource.addEmployee(createEmployeeDTO());
		Mockito.verify(employeeService).addEmployee(createEmployeeDTO());
		assertEquals(200, actual.getStatus());
	}
	
	@Test
	public void testUpdateEmployee_ShouldReturnOKStatusResponse_WhenEmployeeDTOIsGiven() {
		Response actual = employeeResource.updateEmployee(createEmployeeDTO());
		Mockito.verify(employeeService).updateEmployee(createEmployeeDTO());
		assertEquals(200, actual.getStatus());
	}

	
	@Test
	public void testDeleteEmployeeById_ShouldReturnOKStatus_WhenValidEmployeeIdIsGiven() {
		Mockito.when(employeeService.findEmployeeById(1)).thenReturn(createEmployeeDTO());
		
		Response actual = employeeResource.deleteEmployeebyId(1);
		Mockito.verify(employeeService).deleteEmployeeById(1);
		
		assertEquals(200, actual.getStatus()); 
	}
	
		private DepartmentDTO createDepartmentDTO() {
		return new DepartmentDTO(1, "ICT");
	}

	private EmployeeDTO createEmployeeDTO() {
		DepartmentDTO department = createDepartmentDTO();
		return new EmployeeDTO(1, "Yoon", "20", "yoon@gmail.com", department);
	}
	
	
	
}
