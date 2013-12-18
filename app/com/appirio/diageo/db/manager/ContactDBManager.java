package com.appirio.diageo.db.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.appirio.diageo.db.DiageoServicesException;

public class ContactDBManager extends DBManager {

	private static String DEFAULT_CONTACT_REG = System.getenv("DEFAULT_CONTACT_REG");
	
	public ContactDBManager() throws DiageoServicesException {
		super();
	}

	public boolean isApprovedContact(String userId) throws DiageoServicesException {
		JsonNode users = queryToJson("select id from Contact where sfid = '" + userId + "' and Direct_Dial_Activated__c and Direct_Dial_Mobile_User__c;");
		
		return users.size() > 0;
	}


	public String approveContact(String email, Long authorizationCode) throws DiageoServicesException {
		// TODO remove this, temporary for testing
		if(DEFAULT_CONTACT_REG != null && !DEFAULT_CONTACT_REG.equals("FALSE") && authorizationCode.longValue() == 12345) {
			return DEFAULT_CONTACT_REG;
		}
		
		JsonNode users = queryToJson("select sfid from Contact where direct_dial_activation_code__c = " + authorizationCode + " and Direct_Dial_Mobile_User__c and Email = '" + email + "';");
		
		if(users.size() > 0) {
			ObjectNode user = (ObjectNode) users.get(0);
			
			saveApprovedContact(user.get("sfid").asText());
			
			return user.get("sfid").asText();
		} else {
			return null;
		}
	}
	
	private void saveApprovedContact(String sfid) throws DiageoServicesException {
		executeStatement("update contact set Direct_Dial_Activation_Code__c = null, Direct_Dial_Activation_Code_Time_Set__c = null, Activation_Notification_sent__c = null, Direct_Dial_Activated__c = true where sfid = '"+ sfid +"';");		
	}
	
	public String getEmail(String contactId) throws DiageoServicesException {
		ArrayNode result = queryToJson("select email from Contact where sfid = '" + contactId + "'");
		
		if(result.size() > 0) {
			if(result.get(0).has("email")) {
				return result.get(0).get("email").asText();
			}
		}
		
		return null;
	}
}
