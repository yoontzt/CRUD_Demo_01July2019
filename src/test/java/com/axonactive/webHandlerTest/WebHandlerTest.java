package com.axonactive.testing;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.PrimeFaces;

import com.axonactive.bom.Department;
import com.axonactive.bom.Employee;
import com.axonactive.entites.DepartmentEntity;
import com.axonactive.entites.EmployeeEntity;
import com.axonactive.services.DepartmentService;
import com.axonactive.services.EmployeeService;
import com.axonactive.web_config.WebHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PrimeFaces.class })
public class WebHandlerTest {

	@InjectMocks
	WebHandler webHandler;

	@Mock
	DepartmentService depService;

	@Mock
	EmployeeService empService;

	@Mock
	ValueChangeEvent valueChangeEvent;

	@Mock
	PrimeFaces primeFaces;

	@Before
	public void init() {
		PowerMockito.mockStatic(PrimeFaces.class);
		Mockito.when(PrimeFaces.current()).thenReturn(primeFaces);
	}

	@Test
	public void testInit() {
		Department department = createDepartment();
		List<Employee> employees = Arrays.asList(createEmployee());
		List<Department> departments = Arrays.asList(createDepartment());

		Mockito.when(empService.getAll()).thenReturn(employees);

		Mockito.when(depService.getAll()).thenReturn(departments);

		webHandler.init();
		assertEquals(department, webHandler.getDepartment());
	}
	
	@Test(expected = NoResultException.class)
	public void testInitException() {
		List<Employee> employees=Arrays.asList();
		List<Department> departments=Arrays.asList();
		Mockito.when(depService.getAll()).thenReturn(departments);
		Mockito.when(empService.getAll()).thenReturn(employees);
		webHandler.init();				
	}
	
	@Test
	public void testAddNewEmployee_ShouldHideDialog_WhenAddNewEmployee() {
		Department department = createDepartment();
		Employee employee = createEmployee();
		webHandler.setDepartment(department);
		webHandler.setEmployee(employee);

		Mockito.when(empService.getAll()).thenReturn(Arrays.asList(createEmployee()));
		webHandler.addNewEmployee();
		Mockito.verify(empService).addEmployee(employee);
		Mockito.verify(primeFaces).executeScript("PF('addEmployee').hide()");

	}

	@Test
	public void testUpdateEmployeeFromPage_ShouldHideDialog_WhenUpdatedIsSuccessful() {
		// Init Variables
		Department department = createDepartment();
		Employee employee = createEmployee();
		webHandler.setDepartment(department);
		webHandler.setEmployee(employee);

		// Mock when
		Mockito.when(empService.getAll()).thenReturn(Arrays.asList(createEmployee()));

		// Call function
		webHandler.updateEmployeeFromPage();
		// verify
		Mockito.verify(empService).updateEmployee(employee);
		Mockito.verify(primeFaces).executeScript("PF('UpdateEmployee').hide()");
	}

	@Test
	public void testDeleteEmployeeFromPage_ShouldShowList_WhenDeletedIsSuccessful() {
		// Init Variables
		Employee employee = createEmployee();
		webHandler.setEmployee(employee);

		// Mock when
		Mockito.when(empService.toEntity(employee)).thenReturn(createEmployeeEntity());
		// Call function
		webHandler.deleteEmployeeFromPage(employee);

		// verify
		Mockito.verify(empService).getAll();
	}

	@Test
	public void testViewEmployee_ShouldShowDialog_WhenViewEmployeeisSuccessful() {
		// Init Variables
		Employee employee = createEmployee();

		// Call function
		webHandler.viewEmployee(employee);

		// verify
		Mockito.verify(primeFaces).executeScript("PF('UpdateEmployee').show()");

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
		webHandler.changeDepartment(valueChangeEvent);
		// AssertEquals
		Department department = webHandler.getDepartment();
		assertEquals(expected, department);

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

	private EmployeeEntity createEmployeeEntity() {
		DepartmentEntity department = createDepartmentEntity();
		return new EmployeeEntity(1, "Yoon", 20, "yoon@gmail.com", department);
	}

}