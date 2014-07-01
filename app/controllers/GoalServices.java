package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api20.GoalDBManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class GoalServices extends Controller {

	@With(SecureAction.class)
    public static Result getGoals(String startDate, String endDate) {
    	try {
    		GoalDBManager manager = new GoalDBManager();
    		JsonNode result = null;
    		
    		try {
    			String contactId = request().getHeader("uid");
    			result = manager.getGoals(contactId, startDate, endDate);
    		} finally {
    			manager.close();
    		}
    		
    		return ok(result);
    		
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
    }
	
	@With(SecureAction.class)
	public static Result getGoalAchievements() {
		
		JsonNode body = request().body().asJson();
		String contactId = request().getHeader("uId");
		
		if(body != null && body.has("startdate") && body.has("enddate") && body.has("includeDetails")) {
			
			String startDate = body.get("startdate").asText();
			String endDate = body.get("enddate").asText();
			Boolean includeDetails = body.get("includeDetails").asBoolean(false);	

			try {
				GoalDBManager manager = new GoalDBManager();
				ArrayNode result = null;
				
				try {
					result = manager.getGoalAchievements(contactId, startDate, endDate, includeDetails);
				} finally {
					manager.close();
				}

				return ok(result);
	    	} catch (DiageoServicesException e) {
	    		e.printStackTrace();
	    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		
	    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
	    	}
		}else {
			return badRequest(ControllerUtils.messageToJson("json body expected, \'startdate\', \'enddate\' and \'includeDetails\' fields are required to fetch goals."));
		}
		
	}
}
