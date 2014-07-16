package com.appirio.diageo.db.manager.api17;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api15.SurveyDBManager15;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyDBManager17 extends SurveyDBManager15 {

	public SurveyDBManager17(String contactId) throws DiageoServicesException {
		super(contactId);
	}

	@Override
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query-17"), this.contactId));
		
		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}
		
		ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-17"), surveyIds.toString()));

		return processSurveys(surveys, questions, true, true);
	}

	@Override
	public ArrayNode getSurveys(ObjectNode account)
			throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		String[] category = new String[3];
		String rawCategory = account.get("category__c").asText();
		if(rawCategory!=null && rawCategory!=""){
			String[] tArray = rawCategory.split(";");
			for (int i = 0; i < tArray.length; i++) {
				category[i] = tArray[i];
			}
		}
		String surveyQuery = MessageFormat.format(getSQLStatement("survey-query-with-filter-17"),
				account.get("tdlinx_sector__c").asText(),
				account.get("tdlinx_trade_channel__c").asText(),
				account.get("tdlinx_sub_channel__c").asText(),
				account.get("tdlinx_outlet_state__c").asText(), 
				zip,
				account.get("national_account_group__c").asText(),
				account.get("marketing_group__c").asText(),
				account.get("tdlinx_account_level_e__c").asText(),
				account.get("account_segmentatiobn__c").asText(),
				category[0],
				category[1],
				category[2],
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
		
		String questionQuery = MessageFormat.format(getSQLStatement("question-query-with-filter-17"), surveyIds.toString());
		
		ArrayNode questions = queryToJson(questionQuery);
		
		return processSurveys(surveys, questions, true, true);	
	}

}
