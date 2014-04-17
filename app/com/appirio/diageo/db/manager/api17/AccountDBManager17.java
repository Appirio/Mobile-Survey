package com.appirio.diageo.db.manager.api17;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api15.AccountDBManager15;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AccountDBManager17 extends AccountDBManager15 {

	private String userContactId;
	
	public AccountDBManager17() throws DiageoServicesException {
		super();
		userContactId = null;
	}

	public AccountDBManager17(String userContactId) throws DiageoServicesException {
		super();
		this.userContactId = userContactId;
	}

	protected JsonNode getMyAccounts(Double latitude, Double longitude) throws DiageoServicesException {
		if(userContactId != null) {
			String myAccountQuery = MessageFormat.format(getSQLStatement("my-accounts-query"), latitude, longitude, this.userContactId);
			
			ArrayNode result = queryToJson(myAccountQuery);
			
			return processAccounts(result, latitude, longitude);
		}
		
		return mapper.createArrayNode();
	}
	
	@Override
	public JsonNode findAccounts(Double latitude, Double longitude,
			Double radius) throws DiageoServicesException {
		JsonNode proximityAccounts = (ArrayNode) super.findAccounts(latitude, longitude, radius);
		JsonNode myAccounts = getMyAccounts(latitude, longitude);
		
		ObjectNode result = mapper.createObjectNode();
		
		result.put("proximityAccounts", proximityAccounts);
		result.put("myAccounts", myAccounts);
		
		System.out.println(result);
		
		return result;
	}

	@Override
	public JsonNode findAccounts(Double latitude, Double longitude,
			Double radius, long limit) throws DiageoServicesException {
		JsonNode proximityAccounts = (ArrayNode) super.findAccounts(latitude, longitude, radius, limit);;
		JsonNode myAccounts = getMyAccounts(latitude, longitude);
		
		ObjectNode result = mapper.createObjectNode();
		
		result.put("proximityAccounts", proximityAccounts);
		result.put("myAccounts", myAccounts);
		
		System.out.println(result);

		return result;
	}

}
