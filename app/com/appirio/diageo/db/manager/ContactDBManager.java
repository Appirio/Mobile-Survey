package com.appirio.diageo.db.manager;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContactDBManager extends DBManager {

	private static String DEFAULT_CONTACT_REG = System.getenv("DEFAULT_CONTACT_REG");
	
	public ContactDBManager() throws DiageoServicesException {
		super();
	}

	public boolean isApprovedContact(String userId) throws DiageoServicesException {
		JsonNode users = queryToJson("select id from Contact where sfid = '" + userId + "' and Direct_Dial_Activated__c and Direct_Dial_Mobile_User__c;");
		
		return users.size() > 0;
	}

	public JsonNode approveContact(String email /*Long authorizationCode*/) throws DiageoServicesException, Exception {
		// TODO remove this, temporary for testing
		//if(DEFAULT_CONTACT_REG != null && !DEFAULT_CONTACT_REG.equals("FALSE") && authorizationCode.longValue() == 12345) {
		//	return DEFAULT_CONTACT_REG;
		//}
		
		JsonNode users = queryToJson("select sfid, Name, Email, MobilePhone from Contact where Email = '" + email + "';");
		
		if(users.size() > 0) {
			ObjectNode user = (ObjectNode) users.get(0);
			
			saveApprovedContact(user.get("sfid").asText());
			
			//Integer goalCount = user.get("assigned_goal_count__c").asInt(0);
			Boolean showDashboard = false;
			
			/*if(goalCount > 0){
				showDashboard = true;
			}*/
			
			
			return new ObjectMapper().readTree("{\"contact\": {\"id\": \"" + user.get("sfid").asText() + "\", \"name\": \"" + user.get("name").asText()  + "\", \"email\": \"" + user.get("email").asText()   + "\", \"mobilePhone\": \"" + user.get("mobilephone").asText()   + "\", \"showDashboard\": \"" + showDashboard  + "\"}}") ;
		} else {
			return null;
		}
	}
	
	private void saveApprovedContact(String sfid) throws DiageoServicesException {
		executeStatement("update contact set Direct_Dial_Activation_Code__c = null, Direct_Dial_Activation_Code_Time_Set__c = null, Direct_Dial_Activated__c = true where sfid = '"+ sfid +"';");		
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
