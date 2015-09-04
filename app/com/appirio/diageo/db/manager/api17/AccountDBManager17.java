package com.appirio.mobilesurvey.db.manager.api17;

import java.text.MessageFormat;

import com.appirio.mobilesurvey.db.MSServicesException;
import com.appirio.mobilesurvey.db.manager.api15.AccountDBManager15;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AccountDBManager17 extends AccountDBManager15 {

	private String userContactId;
	
	public AccountDBManager17() throws MSServicesException {
		super();
		userContactId = null;
	}

	public AccountDBManager17(String userContactId) throws MSServicesException {
		super();
		this.userContactId = userContactId;
	}

	protected JsonNode getMyAccounts(Double latitude, Double longitude) throws MSServicesException {
		if(userContactId != null) {
			String myAccountQuery = MessageFormat.format(getSQLStatement("my-accounts-query"), latitude, longitude, this.userContactId);
			
			ArrayNode result = queryToJson(myAccountQuery);
			
			return processAccounts(result, latitude, longitude);
		}
		
		return mapper.createArrayNode();
	}
	
	@Override
	public JsonNode findAccounts(Double latitude, Double longitude,
			Double radius) throws MSServicesException {
		JsonNode proximityAccounts = (ArrayNode) super.findAccounts(latitude, longitude, radius);
		JsonNode myAccounts = getMyAccounts(latitude, longitude);
		
		ObjectNode resultObject = mapper.createObjectNode();
		
		resultObject.put("proximityAccounts", proximityAccounts);
		resultObject.put("myAccounts", myAccounts);
		
		ArrayNode result = mapper.createArrayNode();
		result.add(resultObject);
		
		return result;
	}

	@Override
	public JsonNode findAccounts(Double latitude, Double longitude,
			Double radius, long limit) throws MSServicesException {
		JsonNode proximityAccounts = (ArrayNode) super.findAccounts(latitude, longitude, radius, limit);;
		JsonNode myAccounts = getMyAccounts(latitude, longitude);
		
		ObjectNode resultObject = mapper.createObjectNode();
		
		resultObject.put("proximityAccounts", proximityAccounts);
		resultObject.put("myAccounts", myAccounts);
		
		ArrayNode result = mapper.createArrayNode();
		result.add(resultObject);
		
		return result;
	}

}
