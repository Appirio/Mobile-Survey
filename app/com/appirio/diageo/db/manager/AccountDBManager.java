package com.appirio.diageo.db.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.appirio.diageo.DistanceComparator;
import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.geolocation.HaversineCalculator;

public class AccountDBManager extends DBManager {

	private static final int ACCOUNT_LOCATION_LIMIT = 1000;//Integer.parseInt(System.getenv("ACCOUNT_LOCATION_LIMIT"));
	private static String LATITUDE_FIELD_NAME = "latitude__c";
	private static String LONGITUDE_FIELD_NAME = "longitude__c";
	
	public AccountDBManager() throws DiageoServicesException {
		super();
	}

	public ArrayNode _findAccounts(Double latitude, Double longitude, Double radius) throws DiageoServicesException {
		StringBuilder in = new StringBuilder();
		String separator = "";
		Map<String, Double> distances = new HashMap<String, Double>();
		TreeSet<ObjectNode> sortedObjects = new TreeSet<ObjectNode>(new DistanceComparator());
		
		double degrees = Math.floor((radius / 69)) + 1;

		JsonNode coordinateCache = queryToJson("select " + LATITUDE_FIELD_NAME + ", " + LONGITUDE_FIELD_NAME + ", id " +
				                      "from account " +
				                      "where " + LATITUDE_FIELD_NAME + " between " + (latitude - degrees) + " and " + (latitude + degrees) +
				                      "and " + LONGITUDE_FIELD_NAME + " between " + (longitude - degrees) + " and " + (longitude + degrees) + ";");
		
		for(JsonNode coordinate : coordinateCache) {
			double distance = HaversineCalculator.calculateHaversine(coordinate.get(LATITUDE_FIELD_NAME).asDouble(),
					   coordinate.get(LONGITUDE_FIELD_NAME).asDouble(),
					   latitude, longitude);
			
			if(distance <= radius) {
				in.append(separator);
				separator = ",";
				
				in.append(coordinate.get("id").asText());
				
				distances.put(coordinate.get("id").asText(), distance);
			}
		}

		if(in.length() > 0) {
			ArrayNode result = queryToJson("select tdlinx_outlet_city__c, tdlinx_outlet_zip_code__c, tdlinx_outlet_state__c, tdlinx_outlet_addr__c, billingstate, billingcountry, billingpostalcode, billingcity, billingstreet, id, sfid, TDLinx_Outlet_Desc__c, Name, TDLinx_Outlet_State__c from account where id in (" + in.toString() + ")");
			
			for(JsonNode node : result) {
				if(distances.containsKey(node.get("id").asText())) {
					((ObjectNode)node).put("distance", distances.get(node.get("id").asText()));
					
				}
				
				sortedObjects.add((ObjectNode)node);
			}
			
			result = mapper.createArrayNode();
			
			for(ObjectNode o : sortedObjects) {
				o.remove("id");

				result.add(o);
				
				if(result.size() >= ACCOUNT_LOCATION_LIMIT) {
					break;
				}
			}
			
			return result;
		} else {
			return mapper.createArrayNode();
		}
	}
	
	public ArrayNode findAccounts(Double latitude, Double longitude, Double radius) throws DiageoServicesException {
		double degrees = radius / 50;
		
		ArrayNode result = queryToJson("select latitude__c, longitude__c, tdlinx_outlet_city__c,  tdlinx_outlet_zip_code__c,  tdlinx_outlet_state__c,  tdlinx_outlet_addr__c,  billingstate,  billingcountry,  billingpostalcode, billingcity,  billingstreet,  sfid,  TDLinx_Outlet_Desc__c,  Name,  TDLinx_Outlet_State__c,  (|/( power(Latitude__c - " + latitude.toString() + ", 2) + power(Longitude__c - " + longitude.toString() + ", 2))) as distance from account where Latitude__c between " + Double.toString(latitude - degrees) + " and " + Double.toString(latitude + degrees) + " and Longitude__c between " + Double.toString(longitude - degrees) + " and " + Double.toString(longitude + degrees) + " order by distance limit " + Integer.toString(ACCOUNT_LOCATION_LIMIT) + ";");
		
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
	
	public ObjectNode getAccount(String id) throws DiageoServicesException {
		return queryToJsonObject("SELECT national_account_group__c, marketing_group__c, tdlinx_account_level_e__c, tdlinx_sector__c, tdlinx_trade_channel__c, tdlinx_sub_channel__c, tdlinx_outlet_state__c, tdlinx_outlet_zip_code__c FROM account WHERE sfid = '" + id + "';");
	}
}
