
package com.axonactive.exception.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.axonactive.exception.ParameterMissingException;
import com.axonactive.exception.ParameterMissingExceptionMapper;

@RunWith(PowerMockRunner.class)

@PrepareForTest({ TimeZone.class, Response.class })
public class ParameterMissingExceptionMapperTest {

	@InjectMocks
	ParameterMissingExceptionMapper parameterMissingException;

	@Mock
	SimpleDateFormat timeGMT;

	@Test
	public void testToResponse_ShouldReturnBadRequest_WhenExceptionIsGiven() {

		Response.status(Status.BAD_REQUEST).entity("").build();

		Response response = parameterMissingException.toResponse(new ParameterMissingException(""));

		PowerMockito.verifyStatic(Response.class);
		Response.status(Status.BAD_REQUEST);

		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

}
