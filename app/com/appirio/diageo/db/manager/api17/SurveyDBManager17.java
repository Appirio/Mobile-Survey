package com.appirio.diageo.db.manager.api17;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api15.SurveyDBManager15;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SurveyDBManager17 extends SurveyDBManager15 {

	public SurveyDBManager17() throws DiageoServicesException {
		super();
	}

	@Override
	public ArrayNode getUniversalSurveys() throws DiageoServicesException {
		ArrayNode surveys = queryToJson(getSQLStatement("survey-query-17"));
		ArrayNode questions = queryToJson(getSQLStatement("question-query-17"));

		return processSurveys(surveys, questions, true, true);
	}

	@Override
	public ArrayNode getSurveys(ObjectNode account)
			throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		ArrayNode surveys = queryToJson("select " +
											"isparent__c, parent_survey__c, enable_edit_on_review_screen__c, name, sfid, survey_type__c, first_question__c, grading_scale__c, total_possible_score__c " +
										"from " +
											"dms_survey__c " +
										"where " +
										"(universal_survey__c " +
											"or ((sector__c is null or sector__c = 'ALL' or sector__c = '' or sector__c like '%" + account.get("tdlinx_sector__c").asText() + "%')" +
												"and (trade_channel__c is null or trade_channel__c = 'ALL' or trade_channel__c = '' or trade_channel__c like '%" + account.get("tdlinx_trade_channel__c").asText() + "%')" +
												"and (sub_channel__c is null or sub_channel__c = 'ALL' or sub_channel__c = '' or sub_channel__c like '%" + account.get("tdlinx_sub_channel__c").asText() + "%')" +
												"and (state__c is null or state__c = '' or state__c like '%" + account.get("tdlinx_outlet_state__c").asText() + "%')" +
												"and (zip_codes__c is null or zip_codes__c = '' or zip_codes__c like '%" + zip + "%')" +
												"and (national_account__c is null or national_account__c = ''or national_account__c = '" + account.get("national_account_group__c").asText() + "')" +
												"and (marketing_group__c is null or marketing_group__c = ''or marketing_group__c = '" + account.get("marketing_group__c").asText() + "')" +
												"and (tdlinx_acct_level_e__c is null or tdlinx_acct_level_e__c = '' or tdlinx_acct_level_e__c = '" + account.get("tdlinx_account_level_e__c").asText() + "')" +
														")) AND (Active__c is null or Active__c = true)" +
										"order by sfid"); 
		
		StringBuilder surveyIds = new StringBuilder();
		String separator = "";
		
		for(JsonNode survey : surveys) {
			surveyIds.append(separator);
			surveyIds.append("'");
			surveyIds.append(survey.get("sfid").asText());
			surveyIds.append("'");
			separator = ",";
		}
		
		ArrayNode questions = queryToJson("select include_none_of_the_above__c, conditional_answer__c, next_question__c, label_for_add_l_comments__c, answer_options__c, sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (" + surveyIds.toString() + ") order by dms_survey__c");
		
		return processSurveys(surveys, questions, true, true);	
	}

}
