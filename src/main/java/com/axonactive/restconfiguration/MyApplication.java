package com.axonactive.restconfiguration;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;

import io.swagger.jaxrs.config.BeanConfig;

public class MyApplication {
	public MyApplication(@Context ServletConfig servletConfig) {
		  super();
		 
		  BeanConfig beanConfig = new BeanConfig();
		 
		  beanConfig.setVersion("1.0.0");
		  beanConfig.setTitle("Todo API");
		  beanConfig.setBasePath("/demo/api");
		  beanConfig.setResourcePackage("com.axonactive.restconfiguration");
		  beanConfig.setScan(true);
		}
}
