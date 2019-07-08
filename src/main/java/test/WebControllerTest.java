package test;
import static org.junit.Assert.assertEquals;

import javax.faces.event.ValueChangeEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import bom.Department;
import bom.Employee;
import entites.DepartmentEntity;
import services.DepartmentService;
import services.EmployeeService;
import web_config.WebController;


@RunWith(PowerMockRunner.class)
public class WebControllerTest {

	@InjectMocks	
	WebController webController;

	@Mock
	DepartmentService depService;

	@Mock
	EmployeeService empService;

	@Mock
	ValueChangeEvent valueChangeEvent;	

	@Test
	public void testDeleteEmployee_ShouldReturnPage_WhenDeletedIsSuccessful() {
		Employee employee = createEmployee();
		webController.setEmployee(employee);
	}

	@Test
	public void testViewEmployee_ShouldReturnUpdatePage_WhenViewEmployeeisSuccessful() {
		Department department = createDepartment();
		Employee employee = createEmployee();
		webController.setDepartment(department);
		webController.setEmployee(employee);

		Mockito.when(depService.toEntity(department)).thenReturn(createDepartmentEntity());

	}

	@Test
	public void testChangeDepartment() {
		
		// Init Variables
		Department expected = createDepartment();
		DepartmentEntity departmentEntity = createDepartmentEntity();

		// Mock
		Mockito.when(valueChangeEvent.getNewValue()).thenReturn(1);
		Mockito.when(depService.findDepartmentById(1)).thenReturn(departmentEntity);
		Mockito.when(depService.toBom(departmentEntity)).thenReturn(createDepartment());


		// Call Function
		webController.changeDepartment(valueChangeEvent);
		// AssertEquals
		Department department = webController.getDepartment();
		assertEquals(expected, department);

		// Verify
	}

	private Employee createEmployee() {
		Department department = createDepartment();
		return new Employee(1, "Yoon", 20, "yoon@gmail.com", department);
	}

	private Department createDepartment() {

		return new Department(1, "ICT");
	}

	private DepartmentEntity createDepartmentEntity() {
		return new DepartmentEntity(1, "Yoon");
	}


}