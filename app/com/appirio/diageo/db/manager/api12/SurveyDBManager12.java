package com.appirio.diageo.db.manager.api12;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.SurveyDBManager;

public class SurveyDBManager12 extends SurveyDBManager {

	public SurveyDBManager12() throws DiageoServicesException {
		super();
	}
	
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson("select name, sfid, grading_scale__c, total_possible_score__c from dms_survey__c where (IsParent__c is null or IsParent__c = false) and parent_survey__c is null  order by sfid");
		ArrayNode questions = queryToJson("select answer_options__c, sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (select sfid from dms_survey__c where (IsParent__c is null or IsParent__c = false) and parent_survey__c is null) order by dms_survey__c");

		return processSurveys(surveys, questions, false);
	}
}
