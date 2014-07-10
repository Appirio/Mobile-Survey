package com.appirio.diageo.db.manager.api14;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api13.SurveyDBManager13;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyDBManager14 extends SurveyDBManager13 {

	public SurveyDBManager14(String contactId) throws DiageoServicesException {
		super(contactId);
	}

	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-universal-query-14"), this.contactId));

		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}

		ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-universal-query-14"), surveyIds.toString()));

		return processSurveys(surveys, questions, false);
	}

	public ArrayNode getSurveys(ObjectNode account) throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		
		String surveyQuery = MessageFormat.format(getSQLStatement("survey-query-with-filter-14"),
				account.get("tdlinx_sector__c").asText(), 
				account.get("tdlinx_trade_channel__c").asText(), 
				account.get("tdlinx_sub_channel__c").asText(),
				account.get("tdlinx_outlet_state__c").asText(), 
				zip, 
				account.get("national_account_group__c").asText(), 
				account.get("marketing_group__c").asText(), 
				account.get("tdlinx_account_level_e__c").asText(),
				this.contactId);
		
		ArrayNode surveys = queryToJson(surveyQuery);
		
		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}
		
		String questionQuery = MessageFormat.format(getSQLStatement("question-query-with-filter-14"), surveyIds.toString());
		
		ArrayNode questions = queryToJson(questionQuery);
		
		return processSurveys(surveys, questions, false);
	}	
}
