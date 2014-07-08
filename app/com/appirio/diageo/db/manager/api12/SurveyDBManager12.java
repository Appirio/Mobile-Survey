package com.appirio.diageo.db.manager.api12;

import java.text.MessageFormat;

import com.fasterxml.jackson.databind.node.ArrayNode;
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
}
