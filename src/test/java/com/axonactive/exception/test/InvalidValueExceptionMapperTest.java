package com.axonactive.exception.test;

import static org.junit.Assert.*;

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

import com.axonactive.exception.InvalidValueException;
import com.axonactive.exception.InvalidValueExceptionMapper;

@RunWith(PowerMockRunner.class)

@PrepareForTest({ TimeZone.class, Response.class })
public class InvalidValueExceptionMapperTest {
	@InjectMocks
	InvalidValueExceptionMapper invalidValueExceptionMapper;

	@Mock
	SimpleDateFormat timeGMT;

	@Test
	public void testToResponse_ShouldReturnBadRequest_WhenExceptionIsGiven() {

		Response.status(Status.NOT_FOUND).entity("").build();

		Response response = invalidValueExceptionMapper.toResponse(new InvalidValueException(""));

		PowerMockito.verifyStatic(Response.class);
		Response.status(Status.NOT_FOUND);

		assertEquals(Status.NOT_FOUND, response.getStatusInfo());
	}

}
