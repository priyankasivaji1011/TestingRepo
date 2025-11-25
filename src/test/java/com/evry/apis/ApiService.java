package com.evry.apis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.evry.bdd.utils.ConfigReader;
import com.evry.bdd.utils.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiService {

	private Response response;

	public void callApi(String apiKey) {
		try {
			//Loginapi
			//returns a list of strings that contain details for that  API.
			ArrayList<String> apiDetails = ExcelUtil.getRequestDetails(apiKey);
			//Each var stores data fetched from excel
			String method = apiDetails.get(0);
			String endpoint = apiDetails.get(1);
			String body = apiDetails.get(2);
			String headersJson = apiDetails.get(3);

			//Get the url
			RestAssured.baseURI = ConfigReader.getProperty("url");

			//to store header to convert str to java map,Convert headers from JSON to Map
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> headers = mapper.readValue(headersJson, Map.class);
			// System.out.println(headers.toString());
			RequestSpecification request = RestAssured.given().headers(headers).body(body);
			//System.out.println(request.toString());

			switch(method.toUpperCase()) {
			case "POST":
				response = request.post(endpoint).then().extract().response();
				break;
			case "GET":
				response = request.get(endpoint).then().extract().response();
				break;
			case "PUT":
				response = request.put(endpoint).then().extract().response();
				break;
			case "DELETE":
				response = request.delete(endpoint).then().extract().response();
				break;
			default:
				throw new RuntimeException("Unsupported HTTP method: " + method);
			}

		} catch(IOException e) {
			throw new RuntimeException("Error reading API data from Excel: " + e.getMessage(), e);
		}
	}

	public int getStatusCode() {
		if(response == null) throw new RuntimeException("No API call has been made yet.");
		return response.getStatusCode();
	}

	public String getResponseBody() {
		if(response == null) throw new RuntimeException("No API call has been made yet.");
		return response.getBody().asString();
	}

	public int getJsonStatusCode() {
		if (response == null)
			throw new RuntimeException("No API call has been made yet.");
		JsonPath jsonPath = response.jsonPath();
		return jsonPath.getInt("status.code");
	}

	public boolean getResponseSuccess() {
		if (response == null)
			throw new RuntimeException("No API call has been made yet.");
		try {
			Boolean success = response.jsonPath().getBoolean("response.success");
			return success != null ? success : false;
		} catch (Exception e) {
			return false;
		}
	}


	public String getResponseMessage() {
		if (response == null)
			throw new RuntimeException("No API call has been made yet.");
		try {
			String message = response.jsonPath().getString("status.message");
			if (message == null || message.isEmpty() || "null".equalsIgnoreCase(message)) {
				// Sometimes the message might come under response.msg instead of status.message
				message = response.jsonPath().getString("response.msg");
			}
			return (message != null && !message.isEmpty()) ? message : "No message field found";
		} catch (Exception e) {
			return "Error extracting message: " + e.getMessage();
		}
	}



}
