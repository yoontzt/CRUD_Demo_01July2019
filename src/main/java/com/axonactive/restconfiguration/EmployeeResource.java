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

import com.axonactive.dto.EmployeeDTO;
import com.axonactive.exception.ParameterMissingException;
import com.axonactive.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Stateless
@Path("/example")
@SwaggerDefinition(schemes = { SwaggerDefinition.Scheme.HTTP,
		SwaggerDefinition.Scheme.HTTPS }, info = @Info(title = "Employee Management", description = "A simple example of apiee", version = "1.0.0"))
@Api(tags = "tags")
@Produces(MediaType.APPLICATION_JSON)

public class EmployeeResource {

	@EJB
	EmployeeService employeeService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Fetch all to dos")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public List<EmployeeDTO> getAllList() {

		return employeeService.getAllEmployeeList();
	}

	@GET
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Find employee by id")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response getEmployeeById(@PathParam("EmployeeId") Integer id) {

		EmployeeDTO employee = employeeService.findEmployeeById(id);
		return Response.status(Status.OK).entity(employee).build();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Add new Employee")
	@ApiResponses({ @ApiResponse(code = 201, message = "Success") })
	public Response addEmployee(EmployeeDTO employee) {
		try {
			employeeService.addEmployee(employee);
		} catch (Exception ex) {
			throw new ParameterMissingException("Some input parameters are missing!! Please check again.");
		}
		return Response.status(Status.OK).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Update  Employee")
	@ApiResponses({ @ApiResponse(code = 204, message = "Success"), @ApiResponse(code = 404, message = "Not Found") })
	public Response updateEmployee(EmployeeDTO employee) {
		try {
			employeeService.updateEmployee(employee);
		} catch (Exception ex) {
			throw new ParameterMissingException("Some input parameters are missing!! Please check again.");
		}
		return Response.status(Status.OK).build();
	}

	@DELETE
	@Path("{EmployeeId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Update  Employee")
	@ApiResponses({ @ApiResponse(code = 204, message = "Success"), @ApiResponse(code = 404, message = "Not Found") })
	public Response deleteEmployeebyId(@PathParam("EmployeeId") Integer id) {

		employeeService.deleteEmployeeById(id);

		return Response.status(Status.OK).build();
	}
}
