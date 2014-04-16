package controllers;

import java.util.UUID;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.AccountDBManager;
import com.appirio.diageo.db.manager.SurveyDBManager;
import com.appirio.diageo.db.manager.api12.SurveyDBManager12;
import com.appirio.diageo.db.manager.api13.SurveyDBManager13;
import com.appirio.diageo.db.manager.api14.SurveyDBManager14;
import com.appirio.diageo.db.manager.api15.SurveyDBManager15;
import com.appirio.diageo.db.manager.api16.SurveyDBManager17;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyServices extends Controller {

	@With(SecureAction.class)
	public static Result getSurveysByAccount(String accountId) {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			ArrayNode result = null;
			
			try {
				result = manager.getSurveys(accountId);
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
	public static Result getSurveysByAccount12(String accountId) {
		try {
			SurveyDBManager12 manager = new SurveyDBManager12();
			AccountDBManager accountManager = new AccountDBManager();
			Result result = null;
			
			try {
				ObjectNode account = accountManager.getAccount(accountId);
				
				if(account != null) {
					ArrayNode surveys = manager.getSurveys(account);
					result = ok(surveys);
				} else {
					result = badRequest(ControllerUtils.messageToJson("Invalid account ID"));
				}
				
			} finally {
				manager.close();
				accountManager.close();
			}
			
			return result;
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}

	@With(SecureAction.class)
	public static Result getSurveysByAccount13(String accountId) {
		try {
			SurveyDBManager13 manager = new SurveyDBManager13();
			AccountDBManager accountManager = new AccountDBManager();
			Result result = null;
			
			try {
				ObjectNode account = accountManager.getAccount(accountId);
				
				if(account != null) {
					ArrayNode surveys = manager.getSurveys(account);
					result = ok(surveys);
				} else {
					result = badRequest(ControllerUtils.messageToJson("Invalid account ID"));
				}
				
			} finally {
				manager.close();
				accountManager.close();
			}
			
			return result;
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}

	@With(SecureAction.class)
	public static Result getSurveysByAccount14(String accountId) {
		try {
			SurveyDBManager13 manager = new SurveyDBManager14();
			AccountDBManager accountManager = new AccountDBManager();
			Result result = null;
			
			try {
				ObjectNode account = accountManager.getAccount(accountId);
				
				if(account != null) {
					ArrayNode surveys = manager.getSurveys(account);
					result = ok(surveys);
				} else {
					result = badRequest(ControllerUtils.messageToJson("Invalid account ID"));
				}
				
			} finally {
				manager.close();
				accountManager.close();
			}
			
			return result;
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}
	
	@With(SecureAction.class)
	public static Result getSurveysByAccount15(String accountId) {
		try {
			SurveyDBManager13 manager = new SurveyDBManager15();
			AccountDBManager accountManager = new AccountDBManager();
			Result result = null;
			
			try {
				ObjectNode account = accountManager.getAccount(accountId);
				
				if(account != null) {
					ArrayNode surveys = manager.getSurveys(account);
					result = ok(surveys);
				} else {
					result = badRequest(ControllerUtils.messageToJson("Invalid account ID"));
				}
				
			} finally {
				manager.close();
				accountManager.close();
			}
			
			return result;
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}
	
	@With(SecureAction.class)
	public static Result getSurveysByAccount17(String accountId) {
		try {
			SurveyDBManager17 manager = new SurveyDBManager17();
			AccountDBManager accountManager = new AccountDBManager();
			Result result = null;
			
			try {
				ObjectNode account = accountManager.getAccount(accountId);
				
				if(account != null) {
					ArrayNode surveys = manager.getSurveys(account);
					result = ok(surveys);
				} else {
					result = badRequest(ControllerUtils.messageToJson("Invalid account ID"));
				}
				
			} finally {
				manager.close();
				accountManager.close();
			}
			
			return result;
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson(e.getMessage()));
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}

	@With(SecureAction.class)
	public static Result getUniversalSurveys() {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			ArrayNode result = null;
			
			try {
				result = manager.getSurveys();
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
	public static Result getUniversalSurveys12() {
		try {
			SurveyDBManager12 manager = new SurveyDBManager12();
			ArrayNode result = null;
			
			try {
				result = manager.getUniversalSurveys();
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
	public static Result getUniversalSurveys13() {
		try {
			SurveyDBManager13 manager = new SurveyDBManager13();
			ArrayNode result = null;
			
			try {
				result = manager.getUniversalSurveys();
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
	public static Result getUniversalSurveys14() {
		try {
			SurveyDBManager14 manager = new SurveyDBManager14();
			ArrayNode result = null;
			
			try {
				result = manager.getUniversalSurveys();
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
	public static Result getUniversalSurveys15() {
		try {
			SurveyDBManager15 manager = new SurveyDBManager15();
			ArrayNode result = null;
			
			try {
				result = manager.getUniversalSurveys();
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
	public static Result getUniversalSurveys17() {
		try {
			SurveyDBManager17 manager = new SurveyDBManager17();
			ArrayNode result = null;
			
			try {
				result = manager.getUniversalSurveys();
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
	public static Result saveSurvey() {
	    try {
			JsonNode body = request().body().asJson();
			SurveyDBManager manager = new SurveyDBManager();
			// Auto generate random externalId that will be unique
			//String externalId = manager.md5Java();
			String externalId = UUID.randomUUID().toString();
			
			System.out.println(body);
			
			if(body != null) {
			    
			    String grade = null;
				String percentage = null;
				String message = null;
				
				try {
					manager.createSurvey15(body, externalId);
				} finally {
				    String query = "select grade__c, score__c, message__c from dd_survey_submission__c where external_id__c='"+ externalId +"'";
				    ArrayNode ss = manager.getSS(query);
				    
				    grade = ss.get(0).get("grade__c").asText();
				    percentage = ss.get(0).get("score__c").asText();
				    message = ss.get(0).get("message__c").asText();
				    
				    if (grade.equalsIgnoreCase("null") && percentage.equalsIgnoreCase("null")) {
				        return ok();
				    }
				    
				    manager.close();
				}
				
				return ok(ControllerUtils.gradeScoreToJson(grade, percentage, message));
			} else {
				return badRequest(ControllerUtils.messageToJson("json body expected"));
			}
    	} catch (DiageoServicesException e) {
    		e.printStackTrace();
    		
    		return internalServerError(e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		return internalServerError(ControllerUtils.messageToJson("An unexpected error occurred!"));
    	}
	}
	
}
