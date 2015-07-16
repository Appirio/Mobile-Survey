package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api20.GoalDBManager;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class GoalServices extends Controller {

	/*@With(SecureAction.class)
	public static Result getGoalAchievements(String startDate, String endDate, String includeDetails) {
		
		String contactId = request().getHeader("uid");
		
		try {
			GoalDBManager manager = new GoalDBManager();
			ArrayNode result = null;
				
			try {
				result = manager.getGoalAchievements(contactId, startDate, endDate, Boolean.parseBoolean(includeDetails));
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
		
	}*/
}
