package web_config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.primefaces.PrimeFaces;

import bom.Department;
import bom.Employee;
import entites.DepartmentEntity;
import lombok.Getter;
import lombok.Setter;
import services.DepartmentService;
import services.EmployeeService;

@SuppressWarnings("deprecation")
@ManagedBean(name = "webController")
@ViewScoped
public class WebController implements Serializable {

	private static final long serialVersionUID = 8495411679904641370L;
	private transient  @Getter @Setter Department department = new Department();
	private transient  @Getter @Setter Employee employee = new Employee();
	private transient  @Getter @Setter DepartmentEntity departmentEntity=new DepartmentEntity();
	private transient  @Getter @Setter int id;

	@Inject
	private EmployeeService empService;

	@Inject
	private DepartmentService depService;

	private transient @Getter @Setter List<Employee> employeeList = new ArrayList<>();

	private transient  @Getter @Setter List<Department> departmentList = new ArrayList<>();

	@PostConstruct
	public void init() {
		employeeList = empService.showAll();
		departmentList = depService.toBoms(depService.showAll());
		if (departmentList.isEmpty())
			throw new NoResultException("No source found");
		else
			department = departmentList.get(0);
	}

	public void addNewEmployee() {
		employee.setDepartment(department);
		empService.addEmployee(employee);
		employeeList = empService.showAll();
		clear();
		PrimeFaces.current().executeScript("PF('addEmployee').hide()");
	}

	public void updateEmployeeFromPage() {
		employee.setDepartment(department);
		empService.updateEmployee(employee);
		employeeList =empService.showAll();
		PrimeFaces.current().executeScript("PF('UpdateEmployee').hide()");
	}

	public void deleteEmployeeFromPage(Employee employeeBOM) {
		empService.deleteEmployeeForController(empService.toEntity(employeeBOM));
		employeeList =empService.showAll();
	}

	public void viewEmployee(Employee emp) {
		setEmployee(emp);
		setId(emp.getDepartment().getId());
		PrimeFaces.current().executeScript("PF('UpdateEmployee').show()");
	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = depService.toBom(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
	
	private void clear() {
		setEmployee(null);
	}
}
