package com.codeobe.integration;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codeobe.integration.CodeobeListener;
import org.codeobe.integration.CodeobeLog;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



@Component
public class ProductApiMessageHandler extends CodeobeListener {

	private static final Logger log = LogManager.getLogger(ProductApiMessageHandler.class);
	
	@Autowired
	ServiceConfig conf;
	
	@Autowired 
	CodeobeLog codeobeLog;
	
	String peid;
	
	@Override
	public String handleRequest(String msg) {
		log.info("handleRequest invoked " + msg);
		peid = UUID.randomUUID().toString();
		List<String> responses = processAndSend(msg, peid, false);
		return responses.get(0);
	}

	
	@Override
	public List<String> processAndSend(String msg, String peid, boolean isManual) {
		log.info("processAndSend peid=" + peid + " manual=" + isManual);

		codeobeLog.logMessageBeforeProcess(msg, peid, isManual);
		
		List<String> msgList =  null;
		try {
			msgList = processAndConvert(msg);
		} catch (JSONException e) {
			codeobeLog.logProcessingError(e.getMessage(), peid, isManual);
			e.printStackTrace();
		}
	    return send(msgList, peid, isManual);
	 }

	@Override
	public List<String> send(List<String> msgList, String peid, boolean isManual) {
		log.info("send peid=" + peid + " manual=" + isManual);

		List<String> responseList = new ArrayList<String>();
		int i = 0;
		for (String msg : msgList) {
			i++;
			if (i>=3) {
				break;
			}
			
			System.out.println("send msg= " + msg );

			codeobeLog.logMessageAfterProcess(msg, peid, isManual);

			try {
				String response = getResponse(conf.getHttpEndpoint(), conf.getHttpMethod(), msg,
						conf.getHeaderNames(), conf.getHeaderValues(),
						conf.getConnectTimeout(), conf.getReadTimeout());
				
				System.out.println("received response= " + response );
				codeobeLog.logResponse(response, peid, isManual);
				
				responseList.add(response);
				
			} catch (IOException e) {
				codeobeLog.logResponseError(e.getMessage(), peid, isManual);
				responseList.add(e.getMessage());
				e.printStackTrace();
			}
		}
	    
		return responseList;
	}
	
	
	public static List<String> processAndConvert(String jsonArray) {
	     
        List<String> resultArray = new ArrayList<String>();
        try {
        	
        	ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArrayNode = new ObjectMapper().readTree(jsonArray);
            for (JsonNode jsonNode : jsonArrayNode) {
                ObjectNode resultObject = objectMapper.createObjectNode();
                resultObject.put("id", jsonNode.get("id").asInt());
                resultObject.put("email", jsonNode.get("email").asText());
                resultObject.put("company", jsonNode.get("company").get("name").asText());
                String jsonResult = resultObject.toPrettyString();
                resultArray.add(jsonResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultArray;
    }
	
	public static String getResponse(String apiEndpoint, String httpMethod, String requestBody, String headNames,
			String headValues, String conTimeout, String readTimeout) throws IOException {
		URL url = new URL(apiEndpoint);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(httpMethod);

		if (httpMethod.equalsIgnoreCase("POST")) {
			conn.setDoOutput(true);
			conn.getOutputStream().write(requestBody.getBytes());
		}
		conn.setConnectTimeout(9000);
		conn.setReadTimeout(9000);
		if (conTimeout != null && !conTimeout.isEmpty()) {
			conn.setConnectTimeout(Integer.parseInt(conTimeout));
		}
		if (readTimeout != null && !readTimeout.isEmpty()) {
			conn.setReadTimeout(Integer.parseInt(readTimeout));
		}

		String[] headerNamesArr = null;
		String[] headerValuesArr = null;
		System.out.println("headerNames=" + headNames + " " + "headerValues=" + headValues);

		if (headNames != null && headValues != null && !headNames.isEmpty() && !headValues.isEmpty()) {
			headerNamesArr = headNames.split(", ");
			headerValuesArr = headValues.split(", ");

			if (headerNamesArr.length == headerValuesArr.length) {
				for (int i = 0; i < headerNamesArr.length; i++) {
					if (!headerNamesArr[i].isBlank() && !headerValuesArr[i].isBlank()) {
						conn.setRequestProperty(headerNamesArr[i], headerValuesArr[i]);
					}
				}
			} else {
				System.err.println("The app.backend.header.names and app.backend.header.values properties hasnt defined corectly");
			}
		}

		int responseCode = conn.getResponseCode();
		System.out.println("responseCode=" +responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String resOut = response.toString();
		return resOut;
	}
	
	
    

}
