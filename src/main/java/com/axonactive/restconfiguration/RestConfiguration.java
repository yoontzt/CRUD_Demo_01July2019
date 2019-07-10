package com.axonactive.restconfiguration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@ApplicationPath("rest")
@SwaggerDefinition (info = @Info (
                        title = "Example Service",
                        description = "A simple example of apiee",
                        version = "1.0.0",
                        contact = @Contact (
                            name = "Phillip Kruger",
                            email = "apiee@phillip-kruger.com",
                            url = "http://phillip-kruger.com"
                        )
                    )
                )
public class RestConfiguration extends Application {
	
}
