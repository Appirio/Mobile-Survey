package com.appirio.diageo.db.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode; 
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.appirio.diageo.db.DiageoServicesException;

public class SurveyDBManager extends DBManager {

	private static List<String> surveyResultFields = Arrays.asList("LASTMODIFIEDDATE", 
			"QUESTION__C", 
			"MANUALLY_ENTERED_UPC_DESCRIPTION__C",
			"MANUALLY_ENTERED_BASE_SIZE__C",
			"ACCOUNT__C",
			"ID",
			"MANUALLY_ENTERED_CITY__C",
			"ANSWER_VALUE__C",
			"ANSWER_TEXT__C",
			"UPC__C",
			"SFID",
			"NIELSON_PRODUCT__C",
			"MANUALLY_ENTERED_STORE_NUMBER__C",
			"CONNECTIONSENTID",
			"LASTMODIFIEDBYID",
			"QUESTION_TYPE__C",
			"SURVEY_DATE__C",
			"CONNECTIONRECEIVEDID",
			"MANUALLY_ENTERED_STORE_NAME__C",
			"NAME",
			"CREATEDDATE",
			"CREATEDBYID",
			"MANUALLY_ENTERED_BRAND_DESC__C",
			"OWNERID",
			"SYSTEMMODSTAMP",
			"_C5_SOURCE",
			"QUESTION_TEXT__C",
			"HARD_CODED_ONE__C",
			"MANUALLY_ENTERED_STORE_ADDRESS__C",
			"ISDELETED",
			"MANUALLY_ENTERED_STATE__C",
			"NOTE__C",
			"DIAGEO_CATEGORY2__C",
			"BRAND_FAMILY__C",
			"BRAND_EXTENSION__C",
			"DIAGEO_SEGMENT__C",
			"DMS_SURVEY__C",
			"BUSINESS_ENTRPS_HIGH__C",
			"CONTACT__C",
			"POSSIBLE_ANSWERS__C",
			"SELECTED_ANSWERS__C"
			);
	
	public SurveyDBManager() throws DiageoServicesException {
		super();
	}
	
	public ArrayNode getSurveys(String accountId) throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		// queryToJson("select * from dms_survey__c where sfid in (select dms_survey__c from dms_survey_to_account__c where account__c = '" + accountId + "')");
		ArrayNode surveys = queryToJson("select name, sfid from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product' order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (select sfid from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product') order by dms_survey__c");
		
		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt);
			ArrayNode surveyQuestions = mapper.createArrayNode();
			
			while(questionCt < questions.size() && (questions.get(questionCt).get("dms_survey__c").asText().equals(survey.get("sfid").asText()))) {
				surveyQuestions.add(questions.get(questionCt));
				
				questionCt++;
			}
			
			survey.put("questions", surveyQuestions);
			survey.put("account__c", accountId);
			
			result.add(survey);
		}
		
		return result;
	}
	
	public ArrayNode getSurveys(ObjectNode account) throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		ArrayNode surveys = queryToJson("select " +
											"name, sfid " +
										"from " +
											"dms_survey__c " +
										"where " +
											"survey_type__c != 'Non Product' and (universal_survey__c" +
											"or ((sector__c is null or sector__c = 'ALL' or sector__c = '' or sector__c like '%" + account.get("tdlinx_sector__c").asText() + "%')" +
												"and (trade_channel__c is null or trade_channel__c = 'ALL' or trade_channel__c = '' or trade_channel__c like '%" + account.get("tdlinx_trade_channel__c").asText() + "%')" +
												"and (sub_channel__c is null or sub_channel__c = 'ALL' or sub_channel__c = '' or sub_channel__c like '%" + account.get("tdlinx_sub_channel__c").asText() + "%')" +
												"and (state__c is null or state__c = '' or state__c like '%" + account.get("tdlinx_outlet_state__c").asText() + "%')" +
												"and (zip_codes__c is null or zip_codes__c = '' or zip_codes__c like '%" + zip + "%')" +
												"and (national_account__c is null or national_account__c = ''or national_account__c = '" + account.get("national_account_group__c").asText() + "')" +
												"and (marketing_group__c is null or marketing_group__c = ''or marketing_group__c = '" + account.get("marketing_group__c").asText() + "')" +
												"and (tdlinx_acct_level_e__c is null or tdlinx_acct_level_e__c = '' or tdlinx_acct_level_e__c = '" + account.get("tdlinx_account_level_e__c").asText() + "')" +
														"))" +
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
		
		ArrayNode questions = queryToJson("select answer_options__c, sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (" + surveyIds.toString() + ") order by dms_survey__c");
		
		return processSurveys(surveys, questions);
	}
	
	public void createSurvey(JsonNode survey) throws DiageoServicesException {
		if(survey.isArray()) {
			for(JsonNode node : survey) {
				createSurvey(node);
			}
		} else if(survey.isObject()) {
			
			if(survey.has("questions")) {
				ArrayNode questions = (ArrayNode) survey.get("questions");

				for(JsonNode question : questions) {
					ObjectNode newSurvey = mapper.createObjectNode();

					ObjectNode questionObj = (ObjectNode) question;
					
					if(questionObj.has("sfid")) {
						questionObj.put("question__c", questionObj.get("sfid").asText());
						questionObj.remove("sfid");
					}
					
					if(questionObj.has("parent_question__c")) {
						questionObj.remove("parent_question__c");
					}
					
					newSurvey.putAll((ObjectNode)survey);
					
					if(newSurvey.has("sfid")) {
						newSurvey.remove("sfid");
					}
					
					newSurvey.remove("questions");
					
					newSurvey.putAll(questionObj);
					
					newSurvey.put("survey_date__c", dateToPostgresString(new Date(System.currentTimeMillis())));
					
					if(newSurvey.has("id")) {
						newSurvey.remove("id");
					}
				
					if(newSurvey.has("question_type__c") && newSurvey.get("question_type__c").asText().equals("Multi-Select") && newSurvey.has("answer_text__c") && newSurvey.get("answer_text__c").asText().length() > 0) {
						if(newSurvey.get("answer_text__c").asText().equals("None of the Above")) {
							newSurvey.put("selected_answers__c", 0);
						} else {
							newSurvey.put("selected_answers__c", newSurvey.get("answer_text__c").asText().replaceAll("[^;]","").length() + 1);
						}
					}
					
					if(newSurvey.has("answer_options__c") && newSurvey.get("answer_options__c").asText().length() > 0) {
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println(newSurvey.get("answer_options__c").asText());
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						newSurvey.put("possible_answers__c", newSurvey.get("answer_options__c").asText().replaceAll("[^,]","").length() + 1);
					} else {
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println("No answer options found!!!");
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
						System.out.println("=== DEBUG ===");
					}

					clearTransientFields(newSurvey, surveyResultFields);
					
					insert((ObjectNode)newSurvey, "dms_survey_result__c");
				}
			} else {
				throw new DiageoServicesException("questions field is required to save survey");
			}
		} else {
			throw new DiageoServicesException("Json object or array expected");
		}
	}

	public ArrayNode getSurveys() throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		ArrayNode surveys = queryToJson("select name, sfid, survey_type__c from dms_survey__c where survey_type__c != 'Non Product' order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where question_type__c != 'Select' and dms_survey__c in (select sfid from dms_survey__c where survey_type__c != 'Non Product' order by sfid) order by dms_survey__c");
		
		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt);
			ArrayNode surveyQuestions = mapper.createArrayNode();
			
			while(questionCt < questions.size() && (questions.get(questionCt).get("dms_survey__c").asText().equals(survey.get("sfid").asText()))) {
				surveyQuestions.add(questions.get(questionCt));
				
				questionCt++;
			}
			
			survey.put("questions", surveyQuestions);
			
			result.add(survey);
		}
		
		return result;
	}

	protected ArrayNode processSurveys(ArrayNode surveys, ArrayNode questions) {
		ArrayNode result = mapper.createArrayNode();

		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt); 
			ArrayNode surveyQuestions = mapper.createArrayNode();
			
			while(questionCt < questions.size() && (questions.get(questionCt).get("dms_survey__c").asText().equals(survey.get("sfid").asText()))) {
				ObjectNode question = (ObjectNode) questions.get(questionCt); 
				
				if(question.has("answer_options__c") && !question.get("answer_options__c").asText().equalsIgnoreCase("null")) {
					StringTokenizer st = new StringTokenizer(question.get("answer_options__c").asText(), ",");
					
					ArrayNode options = mapper.createArrayNode();
					
					while(st.hasMoreTokens()) {
						options.add(st.nextToken());
					}
					
					question.put("answer_options__c", options);
				} else {
					question.remove("answer_options__c");
				}
				
				surveyQuestions.add(question);
				
				questionCt++;
			}
			
			survey.put("questions", surveyQuestions);
			
			result.add(survey);
		}
		
		return result;
	}
	
	/*
	public void updateAnalyticsFields() throws DiageoServicesException {
		ArrayNode surveyResults = queryToJson("select sr.id id, selected_answers__c, possible_answers__c, answer_options__c,  answer_text__c from dms_survey_result__c sr inner join dms_question__c q on sr.question__c = q.sfid;");
		
		for(JsonNode node : surveyResults) {
			int selectedAnswers = 0, possibleAnswers = 0;
			
			if(node.has("answer_text__c") && node.get("answer_text__c").asText().length() > 0) {
				selectedAnswers = node.get("answer_text__c").asText().replaceAll("[^;]","").length() + 1;
			}
			
			if(node.has("answer_options__c") && node.get("answer_options__c").asText().length() > 0) {
				possibleAnswers = node.get("answer_options__c").asText().replaceAll("[^,]","").length() + 1;
			}
			
			executeStatement("update dms_survey_result__c set selected_answers__c = " + selectedAnswers + ", possible_answers__c = " + possibleAnswers + " where id = " + node.get("id").asText());
		}
	}*/
	
}
