package com.appirio.diageo.db.manager.api20;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api17.SurveyDBManager17;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyDBManager20 extends SurveyDBManager17 {

	public SurveyDBManager20(String contactId) throws DiageoServicesException {
		super(contactId);
	}
	
	@Override
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query-20"), this.contactId));
		
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
		if(StringUtils.isNotBlank(surveyIds)){
			ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-20"), this.contactId, surveyIds.toString()));
			result = processSurveys(surveys, questions, true, true);
		}else{
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Survey not found.");
			result.add(error);
		}
		return result;
	}
}
