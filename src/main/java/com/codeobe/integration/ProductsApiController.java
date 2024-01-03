package com.codeobe.integration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sync")
public class ProductsApiController {

	@Value("${spring.application.name}")
	String appName;

	@Autowired
	ProductApiMessageHandler msgHandler;
	
	@Autowired
	ServiceConfig conf;
	
	@RequestMapping(value="/app-name", method=RequestMethod.GET)
	public String serviceName() {
		return appName;
	}
	
	@RequestMapping(value="/version", method=RequestMethod.GET)
	public String serviceVersion() {
		return "NEW4 server.servlet.context-path added and swagger-ui properties removed from SDK";
	}
    
	@RequestMapping(value="/users", method=RequestMethod.GET)
    public String getProduct() {
		
		String response = null;
		try {
			response = ProductApiMessageHandler.getResponse(conf.getSourceEndpoint(), "GET", "",
					conf.getHeaderNames(), conf.getHeaderValues(),
					conf.getConnectTimeout(), conf.getReadTimeout());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msgHandler.handleRequest(response);
    }
}
