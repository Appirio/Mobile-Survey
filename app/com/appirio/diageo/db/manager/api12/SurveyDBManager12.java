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
		
		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}
		
		ArrayNode result = mapper.createArrayNode(); 
		if(surveyIds.toString()!=""){
			ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-12"), surveyIds.toString()));
			result = processSurveys(surveys, questions, false);
		}else{
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Survey not found.");
			result.add(error);
		}
		
		return result;
	}
	
	public ArrayNode getSurveys(ObjectNode account) throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		
		String conditionTemplate = new String(" or category__c like ''%{0}%'' ");
		StringBuffer categoryCondition = new StringBuffer();
		
		String rawCategory = account.get("category__c").asText();
		if(rawCategory!=null && rawCategory!=""){
			String[] tArray = null;
			tArray = rawCategory.split(";");
			for (int i = 0; i < tArray.length; i++) {
				if(!tArray[i].equalsIgnoreCase("null")){
					categoryCondition.append(MessageFormat.format(conditionTemplate, tArray[i]));
				}
			}
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
				account.get("account_segmentatiobn__c").asText(),
				categoryCondition.toString(),
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
		
		ArrayNode result = mapper.createArrayNode(); 
		if(surveyIds.toString()!=""){
			ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-with-filter-12"), surveyIds.toString()));
			result = processSurveys(surveys, questions, false);
		}else{
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Survey not found.");
			result.add(error);
		}
		
		return result;

	}
	
}
