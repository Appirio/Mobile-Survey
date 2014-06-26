package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api20.GoalDBManager;
import com.fasterxml.jackson.databind.JsonNode;

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
		
}
