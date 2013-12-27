package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.ProductDBManager;
import com.appirio.diageo.db.manager.api14.ProductDBManager14;

public class ProductServices extends Controller {

	@With(SecureAction.class)
	public static Result getProduct(String upcCode) {
		try {
			ProductDBManager manager = new ProductDBManager();
			JsonNode result = null;
			
			try {
				result = manager.getProduct(upcCode);
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
	
	//@With(SecureAction.class)
	public static Result getProducts() {
		try {
			ProductDBManager14 manager = new ProductDBManager14();
			JsonNode result = null;
			
			try {
				result = manager.getProducts();
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
