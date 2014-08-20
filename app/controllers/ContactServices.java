package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.ContactDBManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContactServices extends Controller {

	public static Result authorizeUser() {
		try {
			JsonNode body = request().body().asJson();
			
			if(body != null) {
				if(body.has("email") && body.has("authorizationCode")) {
					ContactDBManager manager = new ContactDBManager();
					try {
						JsonNode user = manager.approveContact(body.get("email").asText(), Long.parseLong(body.get("authorizationCode").asText()));
						
						if(user != null) {
							return ok(user);
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
	
	@With(SecureAction.class)
	public static Result getUser() {
		try {
			ContactDBManager manager = new ContactDBManager();
		
			try {
				ObjectNode user = manager.getContact(request().getHeader("uid"));
				
				return ok(user);
			} finally {
				manager.close();
			}
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	}
	}
}
