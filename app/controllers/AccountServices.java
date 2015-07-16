package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.AccountDBManager;
import com.appirio.diageo.db.manager.api14.AccountDBManager14;
import com.appirio.diageo.db.manager.api17.AccountDBManager17;
import com.appirio.diageo.db.manager.api20.AccountDBManager20;
import com.fasterxml.jackson.databind.JsonNode;

public class AccountServices extends Controller {
	
	@With(SecureAction.class)
    public static Result getAccounts(String latitude, String longitude, String radius) {
    	try {
    		AccountDBManager manager = new AccountDBManager();
    		JsonNode result = null;
    		
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
	
	@With(SecureAction.class)
    public static Result getAccountsLimited(String latitude, String longitude, String radius, String limit) {
    	try {
    		AccountDBManager14 manager = new AccountDBManager14();
    		JsonNode result = null;
    		
    		try {
    			result = manager.findAccounts(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius), Long.parseLong(limit));
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
