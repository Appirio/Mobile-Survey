package com.appirio.diageo.db.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode; 
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;

import com.appirio.diageo.db.manager.api15.AnswerOptions;
import com.appirio.diageo.db.DiageoServicesException;

public class SurveyDBManager extends DBManager {
    
    private static List<String> surveySubmissionFields = Arrays.asList("CONNECTIONRECEIVEDID",
            "CONNECTIONSENTID",
            "CONTACT__C",
            "CREATEDBYID",
            "CREATEDDATE",
            "DD_SURVEY__C",
            "EXTERNAL_ID__C",
            "GRADE__C",
            "ID",
            "ISDELETED",
            "LASTMODIFIEDBYID",
            "LASTMODIFIEDDATE", 
			"NAME",
			"OWNERID",
			"SCORE__C", 
			"SYSTEMMODSTAMP"
			);
			
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
			"SELECTED_ANSWERS__C",
			"PERCENT_ANSWERS_SELECTED__C",
			"QUESTION_NUMBER__C",
			"SCORE__C",
			"SURVEY_SUBMISSION__C",
			"DD_SURVEY_SUBMISSION__C__EXTERNAL_ID__C"
			);
	
	public SurveyDBManager() throws DiageoServicesException {
		super();
	}
	
	public ArrayNode getSurveys(String accountId) throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		// queryToJson("select * from dms_survey__c where sfid in (select dms_survey__c from dms_survey_to_account__c where account__c = '" + accountId + "')");
		ArrayNode surveys = queryToJson("select name, sfid, grading_scale__c from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product' order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (select sfid from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product') order by dms_survey__c");
		
		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt);
			ArrayNode surveyQuestions = mapper.createArrayNode();
			
			if (survey.get("grading_scale__c") == null) {
			    continue;
			}
			
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
											"name, sfid, grading_scale__c " +
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
		
		return processSurveys(surveys, questions, false);
	}
	
	public String md5Java() { 
	    String digest = null; 
	    String message = dateToPostgresString(new Date(System.currentTimeMillis()), true);
	    
	    try { 
	        MessageDigest md = MessageDigest.getInstance("MD5"); 
	        byte[] hash = md.digest(message.getBytes("UTF-8")); 
	        
	        //converting byte array to Hexadecimal String 
	        StringBuilder sb = new StringBuilder(2*hash.length); 
	        
	        for(byte b : hash) { 
	            sb.append(String.format("%02x", b&0xff)); 
	        } 
	        
	        digest = sb.toString(); 
	    } 
	    catch (UnsupportedEncodingException ex) { 
	        System.out.println("Error: "+ ex);
	    } 
	    catch (NoSuchAlgorithmException ex) { 
	        System.out.println("Error: "+ ex);
	    } 
	    
	    return digest; 
	}
	
	public void createSurvey15(JsonNode survey, String externalId) throws DiageoServicesException {
	    if(survey.isArray()) {
	        System.out.println("Survey is array");
			for(JsonNode node : survey) {
				createSurvey(node, externalId);
			}
		}
		else if(survey.isObject()) {
		    System.out.println("Survey is object");
		    createSurvey(survey, externalId);
		}
		else {
			throw new DiageoServicesException("Json object or array expected");
		}
	}
	
	public void createSurvey(JsonNode survey, String externalId) throws DiageoServicesException {
		Boolean grading = false;
		int scoreTot = 0;
		int scorePotential = 0;
		String gradingScaleID = null;
			
		if(survey.has("questions")) {
			ArrayNode questions = (ArrayNode) survey.get("questions");
			List<ObjectNode> surveyResultsList = new ArrayList<ObjectNode>();
			
			// Check if grading_scale exist
			if(survey.has("grading_scale__c") && !survey.get("grading_scale__c").asText().equals("null")) {
			    // Get grading scale ID
                gradingScaleID = survey.get("grading_scale__c").asText();
			    // Grading=true
			    grading = true;
			}
			
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
				
				// Get Answer Options
			    String answerOptionsText = newSurvey.get("original_answer_options__c").asText();
				// Grading Survey Evals
				if (grading && answerOptionsText.matches("\\[\\{(.*)")) {
				    // Bool to see if answer needs to match
				    boolean needMatch = false;
				    // Get answer Value
				    String answerValue = newSurvey.get("answer_value__c").asText();
				    // Check for answerText value if answer_value is null
				    if (answerValue.equals("null")) {
				        answerValue = newSurvey.get("answer_text__c").asText();
				        needMatch = true;
				    }
				    if (answerValue.equals("null")) {
				        scoreTot += 0;
				    }
				    
				    List<AnswerOptions> answerOptions = getAnswerOptions(answerOptionsText);
				    // temp Int
				    int tempHigh = 0;
				    int scoredSurveyResult = 0;
				    for(int i=0;i < answerOptions.size();i++) {
				        int answerOptionScore = Integer.parseInt(answerOptions.get(i).score);
				        // Potential Score:
					    // 1. Add up all scores for one that has Multi-Select
					    if (newSurvey.get("question_type__c").asText().equals("Multi-Select")) {
					        StringTokenizer multiSelectValues = new StringTokenizer(answerValue, ";");
					        while(multiSelectValues.hasMoreTokens()) {
        						String msv = multiSelectValues.nextToken();
        						if (msv.equals(answerOptions.get(i).value)) {
        						    // Total score: Total only scores where value matches
        						    scoreTot += answerOptionScore;
        						    scoredSurveyResult += answerOptionScore;
        						}
        					}
					        scorePotential += Integer.parseInt(answerOptions.get(i).score);
					    }
					    // 2. For others, take the highest/only score
					    else {
					        if (needMatch && answerValue.equals(answerOptions.get(i).value)) {
					            // Total score: Total only scores where value matches
					            scoreTot += answerOptionScore;
					            scoredSurveyResult += answerOptionScore;
					        }
					        // Text/Price/QTY as long as there is some value to this, you get the full score
					        else if (!needMatch && !answerValue.equals("null")) {
					            scoreTot += answerOptionScore;
					            scoredSurveyResult += answerOptionScore;
					        }
					        
					        if (answerOptionScore > tempHigh) {
					            tempHigh = answerOptionScore;
					        }
					    }
			        }
			        
			        if (tempHigh > 0) {
			            scorePotential += tempHigh;
			        }
				    
				    newSurvey.put("score__c", Integer.toString(scoredSurveyResult));
				}
				
				newSurvey.put("survey_date__c", dateToPostgresString(new Date(System.currentTimeMillis()), false));
				
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
				
				if(newSurvey.has("question_type__c") && newSurvey.get("question_type__c").asText().equals("Multi-Select") && newSurvey.has("original_answer_options__c") && newSurvey.get("original_answer_options__c").asText().length() > 0) {
					newSurvey.put("possible_answers__c", newSurvey.get("original_answer_options__c").asText().replaceAll("[^,]","").length() + 1);
				}
				
				// Some kind of increment?
				//newSurvey.put("question_number__c", );
				// Survey Submission sfid
				newSurvey.put("DD_Survey_Submission__c__External_Id__c", externalId);
				
				clearTransientFields(newSurvey, surveyResultFields);
				
				surveyResultsList.add((ObjectNode)newSurvey);
			}
			
			// Survey Submissions DB
			ObjectNode newSurveySubmission = mapper.createObjectNode();
    	    newSurveySubmission.put("external_id__c", externalId);
    	    newSurveySubmission.put("contact__c", survey.get("contact__c").asText());
    	    newSurveySubmission.put("dd_survey__c", survey.get("sfid").asText());
    	    
    	    // Survey has Grading
			if (scoreTot > 0) {
			    // Percentage is based on Total Score/Potential Score off of what has been submitted
			    System.out.println("Score Total: "+ Integer.toString(scoreTot) + ", Score Potential: "+ Integer.toString(scorePotential));
			    int percentage = (scoreTot * 100) / scorePotential;
			    // Lookup Grade
			    //System.out.println(gradingScaleID);
			    String query = "select grade__c, low_range__c from dd_grading_range__c where dd_grading_scale__c='"+ gradingScaleID +"' and "+ Integer.toString(percentage) +" >= low_range__c order by low_range__c desc";
			    ArrayNode grades = queryToJson(query);
			    ObjectNode grade = (ObjectNode) grades.get(0);
			    
			    newSurveySubmission.put("grade__c", grade.get("grade__c").asText());
			    newSurveySubmission.put("score__c", Integer.toString(percentage));
			    
			    String message = null;
			    try {
			        message = grade.get("message__c").asText();
			    }
			    catch(Exception e) {
			        System.out.println("Message is null: "+ e);
			    }
			    
			    newSurveySubmission.put("message__c", message);
			}
			
			// Inserting to Survey Submission DB
			System.out.println("Inserting into Survey Submission Table");
    	    insert((ObjectNode)newSurveySubmission, "dd_survey_submission__c");
    	    
    	    // Insert Survey Results
    	    insertSurveyResults(surveyResultsList);
		} else {
			throw new DiageoServicesException("questions field is required to save survey");
		}
	} 

    public void insertSurveyResults(List<ObjectNode> surveyResults) {
        for (ObjectNode surveyR : surveyResults) {
            insert(surveyR, "dms_survey_result__c");
        }
    }
    
    public ArrayNode getSS(String query) throws DiageoServicesException {
        return queryToJson(query);
    }
    
	public ArrayNode getSurveys() throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		ArrayNode surveys = queryToJson("select name, sfid, survey_type__c, grading_scale__c from dms_survey__c where survey_type__c != 'Non Product' order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where question_type__c != 'Select' and dms_survey__c in (select sfid from dms_survey__c where survey_type__c != 'Non Product' order by sfid) order by dms_survey__c");
		
		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt);
			ArrayNode surveyQuestions = mapper.createArrayNode();
			
			if (survey.get("grading_scale__c") == null) {
			    continue;
			}
			
			while(questionCt < questions.size() && (questions.get(questionCt).get("dms_survey__c").asText().equals(survey.get("sfid").asText()))) {
			    surveyQuestions.add(questions.get(questionCt));
				
				questionCt++;
			}
			
			survey.put("questions", surveyQuestions);
			
			result.add(survey);
		}
		
		return result;
	}
	
	public List<AnswerOptions> getAnswerOptions(String answerOptions) {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    
	    try {
	        return mapper.readValue(answerOptions, new TypeReference<List<AnswerOptions>>() {});
	    }
	    catch(UnrecognizedPropertyException e) {
	        System.out.println("Unrecognized: "+ answerOptions +" Error: "+ e);
	    }
	    catch (JsonMappingException e) {
            System.out.println("JSON Mapping: "+ answerOptions +" Error: "+ e);
        }
	    catch (IOException e) {
	        System.out.println("ERROR Reading Answers: "+ answerOptions +" Error: "+ e);
	    }
	    return null;
	}

	protected ArrayNode processSurveys(ArrayNode surveys, ArrayNode questions, boolean graded) {
	    ArrayNode result = mapper.createArrayNode();
        
		int questionCt = 0; 

		for(int surveyCt = 0 ; surveyCt < surveys.size(); surveyCt ++) {
			ObjectNode survey = (ObjectNode) surveys.get(surveyCt); 
            ArrayNode surveyQuestions = mapper.createArrayNode();
            Boolean grademe = false;
            Boolean skipQs = false;
            
            // If it came from API < 1.5, and grading_scale is NOT NULL, skip this new survey
            if (!graded && !survey.get("grading_scale__c").asText().equalsIgnoreCase("null")) {
                System.out.println("Skipping survey: "+ survey.get("name").asText());
                skipQs = true;
			}
			// If it came from API 1.5 and grading scale is NOT null
			if (graded && !survey.get("grading_scale__c").asText().equalsIgnoreCase("null")) {
			    grademe = true;
			}
			
			//System.out.println("Survey: "+ survey.get("name").asText() +", Survey ID: "+ survey.get("sfid").asText());
			//System.out.println("Survey Questions num: "+ Integer.toString(questions.size()));
			//System.out.println("Survey Questions Count: "+ Integer.toString(questionCt));
			
			while(questionCt < questions.size() && (questions.get(questionCt).get("dms_survey__c").asText().equals(survey.get("sfid").asText()))) {
				//System.out.println("Survey Question SFID Ref: "+ questions.get(questionCt).get("dms_survey__c").asText());
				ObjectNode question = (ObjectNode) questions.get(questionCt); 
				
				if(question.has("answer_options__c") && !question.get("answer_options__c").asText().equalsIgnoreCase("null")) {
					question.put("original_answer_options__c", question.get("answer_options__c").asText());
					
					ArrayNode options = mapper.createArrayNode();
					String answerOptionsText = question.get("answer_options__c").asText();
					
					if (grademe && answerOptionsText.matches("\\[\\{(.*)")) {
					    //System.out.println("AnswerOptions TEXT: "+ answerOptionsText);
					    List<AnswerOptions> answerOptions = getAnswerOptions(answerOptionsText);
					    if (answerOptions != null) {
					        for(int i=0;i < answerOptions.size();i++) {
					            //System.out.println(answerOptions.get(i).value);
					            options.add(answerOptions.get(i).value);
					        }
					    }
					}
					else {
					    StringTokenizer st = new StringTokenizer(answerOptionsText, ",");
					    while(st.hasMoreTokens()) {
    						options.add(st.nextToken());
    					}
					}
					
					question.put("answer_options__c", options);
				} else {
					question.remove("answer_options__c");
				}
				
				surveyQuestions.add(question);
				
				questionCt++;
			}
			
			// If Questions are to be skipped, not add survey
			if (!skipQs) {
			    survey.put("questions", surveyQuestions);

    			result.add(survey);
			}
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
