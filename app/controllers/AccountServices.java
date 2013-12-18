package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.AccountDBManager;

public class AccountServices extends Controller {
	
	@With(SecureAction.class)
    public static Result getAccounts(String latitude, String longitude, String radius) {
    	try {
    		AccountDBManager manager = new AccountDBManager();
    		ArrayNode result = null;
    		
    		try {
    			result = manager.findAccounts(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius));
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
