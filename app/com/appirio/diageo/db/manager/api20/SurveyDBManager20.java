package com.appirio.diageo.db.manager.api20;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api17.SurveyDBManager17;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyDBManager20 extends SurveyDBManager17 {

	@Override
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(getSQLStatement("survey-query-17"));
		ArrayNode questions = queryToJson(getSQLStatement("question-query-20"));

		return processSurveys(surveys, questions, true, true);
	}

	@Override
	public ArrayNode getSurveys(ObjectNode account)
			throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		
		String surveyQuery = MessageFormat.format(getSQLStatement("survey-query-with-filter-17"),account.get("tdlinx_sector__c").asText(),account.get("tdlinx_trade_channel__c").asText(),account.get("tdlinx_sub_channel__c").asText(),
						account.get("tdlinx_outlet_state__c").asText(), zip,account.get("national_account_group__c").asText(),account.get("marketing_group__c").asText(),account.get("tdlinx_account_level_e__c").asText() );
		
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
		
		String questionQuery = MessageFormat.format(getSQLStatement("question-query-with-filter-20"), surveyIds.toString());
		
		ArrayNode questions = queryToJson(questionQuery);
		
		return processSurveys(surveys, questions, true, true);	
	}
	
	public SurveyDBManager20() throws DiageoServicesException {
		super();
	}

	
}
