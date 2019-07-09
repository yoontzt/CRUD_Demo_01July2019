package com.axonactive.restConfig;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.axonactive.bom.Employee;
import com.axonactive.entites.EmployeeEntity;
import com.axonactive.services.EmployeeService;

@Stateless
@Path("employee")
public class EmployeeResource {

	@EJB
	EmployeeService employeeService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Employee> showAll() {
		return employeeService.getAll();
	}

	@GET
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Employee read(@PathParam("EmployeeId") int id) {
		return employeeService.toBom(employeeService.findById(id));
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addEmployee(Employee emp) {
		employeeService.addEmployee(emp);
		return Response.status(Status.OK).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateEmployee(Employee emp) {
		employeeService.updateEmployee(emp);
		return Response.status(Status.OK).build();		
	}

	@DELETE
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response deleteEmployeebyId(@PathParam("EmployeeId") int id) {
		EmployeeEntity employeeEntity = employeeService.findById(id);
		if ( employeeEntity != null) {
			employeeService.deleteEmployeeForREST(employeeEntity);
			return Response.status(Status.OK).build();			
		}
		return Response.status(Status.NO_CONTENT).build();
	}
}
