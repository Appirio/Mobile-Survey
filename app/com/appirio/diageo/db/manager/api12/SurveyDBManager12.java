package com.appirio.diageo.db.manager.api12;

import java.text.MessageFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.SurveyDBManager;

public class SurveyDBManager12 extends SurveyDBManager {

	public SurveyDBManager12(String contactId) throws DiageoServicesException {
		super(contactId);
	}
	
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query-12"), this.contactId));
		ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-12"), this.contactId));

		return processSurveys(surveys, questions, false);
	}
	
	public ArrayNode getSurveys(ObjectNode account) throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query-with-filter-12"), 
				account.get("tdlinx_sector__c").asText(),
				account.get("tdlinx_trade_channel__c").asText(),
				account.get("tdlinx_sub_channel__c").asText(),
				account.get("tdlinx_outlet_state__c").asText(),
				zip,
				account.get("national_account_group__c").asText(),
				account.get("marketing_group__c").asText(),
				account.get("tdlinx_account_level_e__c").asText(),
				this.contactId)); 
		
		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}
		
		ArrayNode questions = queryToJson("select answer_options__c, sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (" + surveyIds.toString() + ") order by dms_survey__c");
		
		return processSurveys(surveys, questions, false);
	}
	
}
