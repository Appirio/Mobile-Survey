package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.ContactDBManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactServices extends Controller {

	public static Result authorizeUser() {
		try {
			JsonNode body = request().body().asJson();
			
			if(body != null) {
				if(body.has("email") && body.has("authorizationCode")) {
					ContactDBManager manager = new ContactDBManager();
					try {
						String email = body.get("email").asText().toLowerCase();
						
						String userId = manager.approveContact(email, Long.parseLong(body.get("authorizationCode").asText()));
						
						if(userId != null) {
							return ok(new ObjectMapper().readTree("{\"contact\": {\"id\": \"" + userId + "\"}}"));
						} else {
							return badRequest(ControllerUtils.messageToJson("Invalid code, code already used, or contact not authorized"));
						}
					} finally {
						manager.close();
					}
				} else {
					return badRequest(ControllerUtils.messageToJson("Json body missing required properties"));
				}
				
			} else {
				return badRequest(ControllerUtils.messageToJson("Json Body expected"));
			}
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}
}
