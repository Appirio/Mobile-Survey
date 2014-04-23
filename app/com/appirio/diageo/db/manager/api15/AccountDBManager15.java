package com.appirio.diageo.db.manager.api15;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api14.AccountDBManager14;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class AccountDBManager15 extends AccountDBManager14 {

	public AccountDBManager15() throws DiageoServicesException {
		super();
	}
	
	public JsonNode findAccounts(Double latitude, Double longitude, Double radius, long limit) throws DiageoServicesException {
		double degrees = radius / 50;
		
		ArrayNode result = queryToJson(MessageFormat.format(getSQLStatement("accounts-query-15"), latitude, longitude, latitude - degrees, latitude + degrees, longitude - degrees, longitude + degrees, limit));
		
		return processAccounts(result, latitude, longitude);
	}
}
