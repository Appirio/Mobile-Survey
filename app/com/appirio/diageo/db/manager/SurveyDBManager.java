package com.appirio.diageo.db.manager;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api15.AnswerOptions;
import com.appirio.diageo.db.manager.goals.GoalCalculator;
import com.appirio.diageo.db.manager.goals.GoalCalculatorFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

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
			"DD_SURVEY_SUBMISSION__C__EXTERNAL_ID__C",
			"SCORE_ROLLUP__C",
			"SCORE_ROLLUP_TOTAL_POSSIBLE__C",
			"GOAL_ACHIEVEMENT__C",
			"RESULT_EXT_ID__C"
			);
	
	public SurveyDBManager() throws DiageoServicesException {
		super();
	}
	
	public ArrayNode getSurveys(String accountId) throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		// queryToJson("select * from dms_survey__c where sfid in (select dms_survey__c from dms_survey_to_account__c where account__c = '" + accountId + "')");
		ArrayNode surveys = queryToJson("select name, sfid, grading_scale__c, total_possible_score__c from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product' and (Active__c is null or Active__c = true) and (IsParent__c is null or IsParent__c = false) and parent_survey__c is null order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where dms_survey__c in (select sfid from dms_survey__c where universal_survey__c and survey_type__c != 'Non Product' and (Active__c is null or Active__c = true) and (IsParent__c is null or IsParent__c = false) and parent_survey__c is null ) order by dms_survey__c");
		
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
		
		return sortSurveysByName(result);
	}
	
	private class SurveyNameComparator implements Comparator<JsonNode> {

		@Override
		public int compare(JsonNode o1, JsonNode o2) {
			if(o1 == null || !o1.has("name")) {
				return -1;
			}

			if(o2 == null || !o2.has("name")) {
				return 1;
			}
			
			return o1.get("name").asText().toUpperCase().compareTo(o2.get("name").asText().toUpperCase());			
		}
		
	}
	
	public ArrayNode sortSurveysByName(ArrayNode surveys) {
		ArrayList<JsonNode> nodes = new ArrayList<JsonNode>();
		
		for(JsonNode node : surveys) {
			nodes.add(node);
		}
		
		Collections.sort(nodes, new SurveyNameComparator());
		
		ArrayNode result = mapper.createArrayNode();
		
		for(JsonNode node : nodes) {
			result.add(node);
		}
		
		return result;
	}
	
	public ArrayNode getSurveySubmissions(String externalId) throws DiageoServicesException {
		String query = MessageFormat.format(getSQLStatement("survey-submission-by-external-id-query"), externalId);
		
		return queryToJson(query);
	}
	
	public ArrayNode getSurveyResults(String submissionExternalId) throws DiageoServicesException {
		String query = MessageFormat.format(getSQLStatement("survey-results-by-external-id-query"), submissionExternalId);
		
		return queryToJson(query);
	}

	public ArrayNode getSurveys(ObjectNode account) throws DiageoServicesException {
		String zip = account.get("tdlinx_outlet_zip_code__c").asText();
		
		if(zip.length() > 5) {
			zip = zip.substring(0, 5);
		}
		ArrayNode surveys = queryToJson("select " +
											"name, sfid, grading_scale__c, total_possible_score__c " +
										"from " +
											"dms_survey__c " +
										"where " +
											"survey_type__c != 'Non Product' and (Active__c is null or Active__c = true) and  (IsParent__c is null or IsParent__c = false) and parent_survey__c is null and (universal_survey__c " +
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
	
	// TODO: Offline - The existing Heroku "saveResult()" service assumes that ONE logical survey is submitted.  The JSON data structure actually looks like multiple surveys if a 
	//       PRODUCT survey includes multiple scans/products, but we ASSUME that a single submission to saveResult() is a single dd_survey_submission__c record with many related dms_survey_result 
	//       records.  This logic will need to be updated if/when we can submit results from multiple surveys in the same saveResult() invocation.  
	public void createSurvey(JsonNode survey, String externalId) throws DiageoServicesException {
		ObjectNode surveySubmission = null;
		
	    if(survey.isArray()) {
	        System.out.println("Survey is array");
			for(JsonNode node : survey) {
				surveySubmission = createSurvey(node, externalId, surveySubmission);
			}
		}
		else if(survey.isObject()) {
		    System.out.println("Survey is object");
		    surveySubmission = createSurvey(survey, externalId, surveySubmission);
		}
		else {
			throw new DiageoServicesException("Json object or array expected");
		}
	    
	    if(surveySubmission != null) {
		    // Inserting to Survey Submission DB
			System.out.println("Inserting into Survey Submission Table");
		    insert((ObjectNode)surveySubmission, "dd_survey_submission__c");
	    }
	}
	
	public ObjectNode createSurvey(JsonNode survey, String externalId, ObjectNode surveySubmission) throws DiageoServicesException {
		Boolean grading = false;
		int scoreTot = 0;
		int scorePotential = 0;
		String gradingScaleID = null;
			
		if(survey.has("questions")) {
			ArrayNode questions = (ArrayNode) survey.get("questions");
			List<ObjectNode> surveyResultsList = new ArrayList<ObjectNode>();
			List<ObjectNode> photosList = new ArrayList<ObjectNode>();

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
				String answerOptionsText = "";
				try {
				    answerOptionsText = newSurvey.get("original_answer_options__c").asText();
				}
				catch(Exception e) {
				    System.out.println("WARN: No original_answer_options__c: "+ e);
				}
				
				// Grading Survey Evals
				if (answerOptionsText.matches("\\[\\{(.*)")) {
				    grading = true;
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
				        int answerGoalScore = Integer.parseInt(answerOptions.get(i).goalScore);
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
				
				if(newSurvey.has("question_type__c") && newSurvey.get("question_type__c").asText().equals("Multi-Select") && newSurvey.has("delimitedAnswerOptions") && newSurvey.get("delimitedAnswerOptions").asText().length() > 0) {
					newSurvey.put("possible_answers__c", newSurvey.get("delimitedAnswerOptions").asText().replaceAll("[^,]","").length() + 1);
				}
				
				// Some kind of increment?
				//newSurvey.put("question_number__c", );
				// Survey Submission sfid
				newSurvey.put("DD_Survey_Submission__c__External_Id__c", externalId);
				
				// creating photo records 
				if(question.has("photos")){
					String photoExternalId = UUID.randomUUID().toString();
					newSurvey.put("Result_Ext_ID__c", photoExternalId);

					ArrayNode questionPhotos= (ArrayNode) question.get("photos");
					for (JsonNode jsonNode : questionPhotos) {
						ObjectNode newPhoto = mapper.createObjectNode();
						newPhoto.put("DMS_Survey_Result__c__Result_Ext_ID__c", photoExternalId);
						newPhoto.put("Photo_URL__c", jsonNode.get("externalPath"));
						photosList.add(newPhoto);
					}
				}
				
				clearTransientFields(newSurvey, surveyResultFields);
				// Add it to an arrayList
				surveyResultsList.add((ObjectNode)newSurvey);
			}
			
			// Survey Submissions DB
			if(surveySubmission == null) {
				surveySubmission = mapper.createObjectNode();
	    	    surveySubmission.put("external_id__c", externalId);
	    	    surveySubmission.put("contact__c", survey.get("contact__c").asText());
	    	    surveySubmission.put("dd_survey__c", survey.get("sfid").asText());
	    	    surveySubmission.put("Total_Possible_Score__c", 0);
	    	    surveySubmission.put("Total_Actual_Score__c", 0);
			}
    	    
    	    // Survey has Grading
			if (scoreTot >= 0 && gradingScaleID != null && !gradingScaleID.isEmpty()) {
				scorePotential = surveySubmission.get("Total_Possible_Score__c").asInt() + Integer.parseInt(survey.get("total_possible_score__c").asText());
			    // Percentage is based on Total Score/Potential Score off of what has been submitted
				scoreTot += surveySubmission.get("Total_Actual_Score__c").asInt();
			    System.out.println("Score Total: "+ Integer.toString(scoreTot) + ", Score Potential: "+ Integer.toString(scorePotential));
			    int percentage = (scoreTot * 100) / scorePotential;
			    // Lookup Grade
			    //System.out.println(gradingScaleID);
			    String query = "select grade__c, low_range__c, message__c from dd_grading_range__c where dd_grading_scale__c='"+ gradingScaleID +"' and "+ Integer.toString(percentage) +" >= low_range__c order by low_range__c desc";
			    ArrayNode grades = queryToJson(query);
			    ObjectNode grade = (ObjectNode) grades.get(0);
			    
			    surveySubmission.put("grade__c", grade.get("grade__c").asText());
			    surveySubmission.put("score__c", Integer.toString(percentage));
				surveySubmission.put("Total_Actual_Score__c", scoreTot);
				surveySubmission.put("Total_Possible_Score__c", scorePotential);
			    
			    String message = null;
			    try {
			        message = grade.get("message__c").asText();
			    }
			    catch(Exception e) {
			        System.out.println("Message is null: "+ e);
			    }
			    
			    surveySubmission.put("message__c", message);
			}
			
    	    // Insert Survey Results
    	    insertSurveyResults(surveyResultsList);
		} else {
			throw new DiageoServicesException("questions field is required to save survey");
		}
		
		return surveySubmission;
	} 

    public void insertSurveyResults(List<ObjectNode> surveyResults) throws DiageoServicesException {
    	// TODO: clear the name field that is being copied from question
        for (ObjectNode surveyR : surveyResults) {
            insert(surveyR, "dms_survey_result__c");
        }
    }
    
    public void insertSurveyResultsPhotos(List<ObjectNode> photosList) throws DiageoServicesException {
    	for (ObjectNode photoR : photosList) {
    		insert(photoR, "DD_Survey_Result_Photos__c");
    	}
    }
    
    public ArrayNode getSS(String query) throws DiageoServicesException {
        return queryToJson(query);
    }
    
	public ArrayNode getSurveys() throws DiageoServicesException {
		ArrayNode result = mapper.createArrayNode();
		
		ArrayNode surveys = queryToJson("select name, sfid, survey_type__c, grading_scale__c from dms_survey__c where survey_type__c != 'Non Product' and (Active__c is null or Active__c = true) and (IsParent__c is null or IsParent__c = false) and parent_survey__c is null order by sfid");
		ArrayNode questions = queryToJson("select sfid, question_text__c, parent_question__c, name, sfid, question_type__c, dms_survey__c from dms_question__c where question_type__c != 'Select' and dms_survey__c in (select sfid from dms_survey__c where survey_type__c != 'Non Product' and (Active__c is null or Active__c = true) and (IsParent__c is null or IsParent__c = false) and parent_survey__c is null) order by dms_survey__c");
		
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
		
		return sortSurveysByName(result);
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
		return processSurveys(surveys, questions, graded, false);
	}

	protected ArrayNode processSurveys(ArrayNode surveys, ArrayNode questions, boolean graded, boolean supportsChapters) {
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
					
					if (answerOptionsText.matches("\\[\\{(.*)")) {
						// Evaluate the original answer options field 
						try {
							JsonNode answerOptions = (ArrayNode)new ObjectMapper().readTree(answerOptionsText);
							String delimitedAnswerOptions = "";
							String separator = "";
							
							if(answerOptions.isArray()) {
								for(JsonNode answer : answerOptions) {
									delimitedAnswerOptions += separator + answer.get("value").asText();
									separator = ",";
								}
							}
							
							question.put("delimitedAnswerOptions", delimitedAnswerOptions);
						} catch (Exception e) {
							// If this fails it is not critical
							e.printStackTrace();
						} 
						
					    //System.out.println("AnswerOptions TEXT: "+ answerOptionsText);
					    grademe = true;
                        
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
		
		if(supportsChapters) {
			result = processChapters(result);
		}
		
		return sortSurveysByName(result);
	}
	
	public ArrayNode processChapters(ArrayNode surveys) {
		ArrayNode result = mapper.createArrayNode();
		Map<String, ObjectNode> parentSurveys = new HashMap<String, ObjectNode>();
		List<JsonNode> childSurveys = new ArrayList<JsonNode>();
		
		// create result array node with non parent and non child surveys
		// find parent surveys and create a map keyed by id
		// find child surveys and add them to child surveys list
		for(JsonNode survey : surveys) {
			if(survey.has("isparent__c") && survey.get("isparent__c").asText().equals("t")) {
				parentSurveys.put(survey.get("sfid").asText(), (ObjectNode)survey);
			} else if(survey.has("parent_survey__c") && !survey.get("parent_survey__c").asText().equals("null")) {
				childSurveys.add(survey);
			} else {
				result.add(survey);
			}
		}
		
		// add child surveys to the childSurveys list of the parents
		for(JsonNode childSurvey : childSurveys) {
			ObjectNode parentSurvey = parentSurveys.get(childSurvey.get("parent_survey__c").asText());
			
			if(parentSurvey != null) {
				if(!parentSurvey.has("childSurveys")) {
					parentSurvey.put("childSurveys", mapper.createArrayNode());
				}
				
				((ArrayNode)parentSurvey.get("childSurveys")).add(childSurvey);
			} 
		}
		
		// sort child surveys and add parent surveys to result array node
		for(ObjectNode survey : parentSurveys.values()) {
			if(survey.has("childSurveys")) {
				survey.put("childSurveys", sortSurveysByName((ArrayNode)survey.get("childSurveys")));
			}
			
			result.add(survey);
		}
		
		// return the result
		return result;
	}
	
	public void calculateGoals(String surveySubmissionExternalId) throws DiageoServicesException {
		// Fetch all goals that need to be processed for the identified survey submission
		ArrayNode goalsForProcessing = queryToJson(MessageFormat.format(getSQLStatement("query-goals-for-processing"), surveySubmissionExternalId));
	
		// If there are goals to process
		if(goalsForProcessing.size() > 0) {
			// Group the survey results together by question
			ObjectNode currentObject = (ObjectNode) goalsForProcessing.get(0);
			ArrayNode currentGroup = mapper.createArrayNode();
			ArrayNode groupedQuestions = mapper.createArrayNode();

			currentGroup.add(currentObject);
			groupedQuestions.add(currentGroup);
			
			for(int i = 1; i < goalsForProcessing.size(); i++) {
				ObjectNode nextObject = (ObjectNode) goalsForProcessing.get(i);
				
				if(nextObject.get("question__c").asText().equals(currentObject.get("question__c").asText())) {
					currentGroup.add(nextObject);
				} else {
					currentGroup = mapper.createArrayNode();
					groupedQuestions.add(currentGroup);
					currentGroup.add(nextObject);
				}
			}
			
			// For each question instantiate the appropriate goal calculator depending on the question type
			for(int i = 0; i < groupedQuestions.size(); i++) {
				ArrayNode questionGroup = (ArrayNode) groupedQuestions.get(i);
				
				if(questionGroup.size() > 0) {
					ObjectNode firstQuestion = (ObjectNode) questionGroup.get(0);
					
					GoalCalculator calc = GoalCalculatorFactory.getInstance().getGoalCalculator(firstQuestion);
					
					int goalAchievement = calc.calculateGoalAchievement(questionGroup);
					
					executeStatement(MessageFormat.format(getSQLStatement("update-goal-achievement"), goalAchievement, firstQuestion.get("assigned_goal__c").asText()));
				}
			}
		}
	}
}
