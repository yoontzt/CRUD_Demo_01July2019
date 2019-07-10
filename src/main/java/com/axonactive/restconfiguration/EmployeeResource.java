package com.axonactive.restconfiguration;

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

import com.axonactive.converter.EmployeeConverter;
import com.axonactive.dto.EmployeeDTO;
import com.axonactive.entites.EmployeeEntity;
import com.axonactive.services.EmployeeService;

import exception.AttributeMissingException;
import exception.MyApplicationException;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
@Stateless
@Path("/example")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Api(value = "Employee service")
@Log
public class EmployeeResource {

	@EJB
	EmployeeService employeeService;

	EmployeeConverter employeeConverter = new EmployeeConverter();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<EmployeeDTO> getAllList() {
		List<EmployeeDTO> employeeList = employeeService.getAllEmployeeList();
		if (employeeList.isEmpty()) {
			throw new MyApplicationException("Currently there is no employee to be showed.");
		}
		return employeeList;
	}

	@GET
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getEmployeeById(@PathParam("EmployeeId") String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException ex) {
			throw new MyApplicationException("Id should be a number!! Please Check the Id value.");
		}
		EmployeeDTO employee = employeeConverter.toDTO(employeeService.findEmployeeById(Integer.parseInt(id)));
		if (employee == null) {
			throw new MyApplicationException("Requested id is not in the list !!");
		}
		return Response.status(Status.OK).entity(employee).build();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addEmployee(EmployeeDTO employee) {
		try {
			employeeService.addEmployee(employee);
		} catch (Exception ex) {
			throw new AttributeMissingException("Some input parameters are missing!! Please check again.");
		}
		return Response.status(Status.OK).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateEmployee(EmployeeDTO employee) {
		try {
			employeeService.updateEmployee(employee);
		} catch (Exception ex) {
			throw new AttributeMissingException("Some input parameters are missing!! Please check again.");
		}
		return Response.status(Status.OK).build();		
	}

	@DELETE
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response deleteEmployeebyId(@PathParam("EmployeeId") String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException exception) {
			throw new MyApplicationException("Id should be a number!! Please Check the Id value.");
		}
		EmployeeEntity employeeEntity = employeeService.findEmployeeById(Integer.parseInt(id));
		if (employeeEntity != null) {
			employeeService.deleteEmployeeForREST(employeeEntity);
			return Response.status(Status.OK).build();
		}
		throw new MyApplicationException("Fail to delete Employee!! Requested id is not in the employee list.");
	}
}
