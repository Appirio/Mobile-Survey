package com.appirio.diageo.db.manager;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.geolocation.HaversineCalculator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AccountDBManager extends DBManager {

	private static final int ACCOUNT_LOCATION_LIMIT = Integer.parseInt(System.getenv("ACCOUNT_LOCATION_LIMIT"));
	
	public AccountDBManager() throws DiageoServicesException {
		super();
	}

	public JsonNode findAccounts(Double latitude, Double longitude, Double radius) throws DiageoServicesException {
		double degrees = radius / 50;
		
		ArrayNode result = queryToJson(MessageFormat.format(getSQLStatement("accounts-query"), latitude, longitude, latitude - degrees, latitude + degrees, longitude - degrees, longitude + degrees, ACCOUNT_LOCATION_LIMIT));
		
		return processAccounts(result, latitude, longitude);
	}
	
	public ObjectNode getAccount(String id) throws DiageoServicesException {
		return queryToJsonObject(MessageFormat.format(getSQLStatement("account-query"), id));
	}
	
	protected ArrayNode processAccounts(ArrayNode result, Double latitude, Double longitude) {
		for(JsonNode item : result) {
			ObjectNode obj = (ObjectNode) item;
			
			obj.put("distance", HaversineCalculator.calculateHaversine(obj.get("latitude__c").asDouble(),
					   obj.get("longitude__c").asDouble(),
					   latitude, longitude));
			
			obj.remove("latitude__c");
			obj.remove("longitude__c"); 
		}
		
		return result;
	}
	
}
