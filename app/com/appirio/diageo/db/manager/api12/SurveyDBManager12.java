package com.appirio.diageo.db.manager.api12;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.SurveyDBManager;

public class SurveyDBManager12 extends SurveyDBManager {

	public SurveyDBManager12() throws DiageoServicesException {
		super();
	}
	
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson("select name, sfid, grading_scale__c from dms_survey__c order by sfid");
		ArrayNode questions = queryToJson("select answer_options__c, sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c order by dms_survey__c");

		return processSurveys(surveys, questions, false);
	}
}
