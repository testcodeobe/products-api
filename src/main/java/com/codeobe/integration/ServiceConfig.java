package com.codeobe.integration;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration
@RefreshScope
public class ServiceConfig {
	
	public ServiceConfig() {
		
	}
	
	@EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
		 printProperties();
    }
	
		
	@Value( "${spring.application.name}" )
	private String appName;
	
    @Value( "${server.port}" )
    private String serverPort;
    
    
	@Value("${solace.jms.host}" )
	private String solaceHost;
	
	@Value("${solace.jms.clientUsername}" ) 
	private String solaceClientUserName;
	
	@Value("${solace.jms.clientPassword}" )
	private String solaceClientPassword;
	
	@Value("${solace.jms.msgVpn}" ) 
	private String solaceMsgVpn;
		
	
	@Value( "${app.source.endpoint}" )
	private String sourceEndpoint;
	
	@Value( "${app.backend.endpoint}" )
	private String httpEndpoint;

	@Value( "${app.backend.appendPath}" )
	private boolean appendPath;

	@Value( "${app.backend.http.method}" )
	private String httpMethod;
	
	@Value( "${app.backend.header.names}" )
	private String headerNames;
	
	@Value( "${app.backend.header.values}" )
	private String headerValues;
	
	@Value( "${app.backend.connect.timeout}" )
	private String connectTimeout;
	
	@Value( "${app.backend.read.timeout}" )
	private String readTimeout;
	
	@Value( "${app.listener.queue}" )
	private String appListenerQueue;
    

	public String getAppName() {
		return appName;
	}

	public String getServerPort() {
		return serverPort;
	}
	
	public String getSolaceHost() {
		return solaceHost;
	}

	public String getSolaceClientUserName() {
		return solaceClientUserName;
	}
	
	@PostConstruct
	private void printProperties() {
		System.out.print("CentralConfig " + this.toString());
	}

	public String getSolaceMsgVpn() {
		return solaceMsgVpn;
	}

	public String getHttpEndpoint() {
		return httpEndpoint;
	}

	public boolean isAppendPath() {
		return appendPath;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getHeaderNames() {
		return headerNames;
	}

	public String getHeaderValues() {
		return headerValues;
	}

	public String getConnectTimeout() {
		return connectTimeout;
	}

	public String getReadTimeout() {
		return readTimeout;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public void setSolaceHost(String solaceHost) {
		this.solaceHost = solaceHost;
	}
	
	public String getAppListenerQueue() {
		return appListenerQueue;
	}
	
	public String getSourceEndpoint() {
		return sourceEndpoint;
	}

	@Override
	public String toString() {
		return "CentralConfig [appName=" + appName + ", serverPort=" + serverPort + ", solaceHost=" + solaceHost
				+ ", solaceClientUserName=" + solaceClientUserName + ", solaceMsgVpn=" + solaceMsgVpn
				+ ", httpEndpoint=" + httpEndpoint + ", appendPath=" + appendPath + ", httpMethod=" + httpMethod
				+ ", headerNames=" + headerNames + ", headerValues=" + headerValues + ", connectTimeout="
				+ connectTimeout + ", readTimeout=" + readTimeout + ", appListenerQueue=" + appListenerQueue + "]";
	}

	
	
	
}
