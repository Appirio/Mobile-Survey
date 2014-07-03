package com.appirio.diageo.db.manager.api12;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.SurveyDBManager;

public class SurveyDBManager12 extends SurveyDBManager {

	public SurveyDBManager12(String contactId) throws DiageoServicesException {
		super(contactId);
	}
	
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(getSQLStatement("survey-query-12"));
		ArrayNode questions = queryToJson(getSQLStatement("question-query-12"));

		return processSurveys(surveys, questions, false);
	}
}
