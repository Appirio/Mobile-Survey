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
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

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

public class SurveyDBManager extends DBManager {
    
	protected String contactId;
	
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
			"RESULT_EXT_ID__C",
			"RESULT_BRAND_EXT_ID__C"
			);
	
	public SurveyDBManager(String contactId) throws DiageoServicesException {
		super();
		
		this.contactId = contactId;
	}
	
	public ArrayNode getSurveys(String accountId) throws DiageoServicesException {
		
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query"), this.contactId));

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
			ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query"), surveyIds.toString()));
			
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
		}else{
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Survey not found.");
			result.add(error);
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

	// TODO: Offline - The existing Heroku "saveResult()" service assumes that ONE logical survey is submitted.  The JSON data structure actually looks like multiple surveys if a 
	//       PRODUCT survey includes multiple scans/products, but we ASSUME that a single submission to saveResult() is a single dd_survey_submission__c record with many related dms_survey_result 
	//       records.  This logic will need to be updated if/when we can submit results from multiple surveys in the same saveResult() invocation.  
	public void createSurvey(JsonNode survey, String externalId) throws DiageoServicesException {
		ObjectNode surveySubmission = null;
		
		Map<String, List<List<ObjectNode>>> data = new HashMap<String, List<List<ObjectNode>>>();
		
		data.put("results", new ArrayList<List<ObjectNode>>());
		data.put("photos", new ArrayList<List<ObjectNode>>());
		data.put("brands", new ArrayList<List<ObjectNode>>());
		
	    if(survey.isArray()) {
	        System.out.println("Survey is array");
			for(JsonNode node : survey) {
				surveySubmission = createSurvey(node, externalId, surveySubmission, data);
			}
		}
		else if(survey.isObject()) {
		    System.out.println("Survey is object");
		    surveySubmission = createSurvey(survey, externalId, surveySubmission, data);
		}
		else {
			throw new DiageoServicesException("Json object or array expected");
		}
	    
	    if(surveySubmission != null) {
		    // Inserting to Survey Submission DB
			System.out.println("Inserting into Survey Submission Table");
		    insert((ObjectNode)surveySubmission, "dd_survey_submission__c");
		    
	    }
	    
	    if(data.get("results").size() > 0) {
	    	for(List<ObjectNode> results : data.get("results")) {
			    insertSurveyResults(results);
	    	}
	    }
	    
	    if(data.get("photos").size() > 0) {
	    	for(List<ObjectNode> results : data.get("photos")) {
	    		insertSurveyResultsPhotos(results);
	    	}
	    }
	    
	    if(data.get("brands").size() > 0) {
	    	for(List<ObjectNode> results : data.get("brands")) {
	    		insertSurveyResultsBrands(results);
	    	}
	    }
	    
	    calculateGoals(externalId);
	}
	
	public ObjectNode createSurvey(JsonNode survey, String externalId, ObjectNode surveySubmission, Map<String, List<List<ObjectNode>>> data) throws DiageoServicesException {
		Boolean grading = false;
		int scoreTot = 0;
		int scorePotential = 0;
		String gradingScaleID = null;
			
		if(survey.has("questions")) {
			ArrayNode questions = (ArrayNode) survey.get("questions");
			List<ObjectNode> surveyResultsList = new ArrayList<ObjectNode>();
			List<ObjectNode> photosList = new ArrayList<ObjectNode>();
			List<ObjectNode> surveyResultBrandList = new ArrayList<ObjectNode>();

			data.get("results").add(surveyResultsList);
			data.get("photos").add(photosList);
			data.get("brands").add(surveyResultBrandList);
			
			// Check if grading_scale exist
			if(survey.has("grading_scale__c") && !survey.get("grading_scale__c").asText().equals("null")) {
			    // Get grading scale ID
                gradingScaleID = survey.get("grading_scale__c").asText();
			    // Grading=true
			    grading = true;
			}
			
			for(JsonNode question : questions) {
				ObjectNode newSurveyResult = mapper.createObjectNode();
				ObjectNode questionObj = (ObjectNode) question;
				
				
				boolean isGoal = false;
				if(questionObj.has("is_goal__c")) {
					String isGoalValue = questionObj.get("is_goal__c").asText();
					if (isGoalValue!=null && isGoalValue.equals("t")) {
						isGoal = true;
					}
				}
				
				String goalBrand = "";
				if(questionObj.has("goal_brand__c")) {
					JsonNode jsonNode = questionObj.get("goal_brand__c");
					goalBrand = jsonNode.asText() != null ? jsonNode.asText() : "";
				}
				
				// fetching parent goal and parent goal brand values.
				boolean isParentGoal = false;
				String parentGoalBrand = "";
				
				String parentQuestionId = questionObj.get("parent_question__c") != null ?  questionObj.get("parent_question__c").asText() : "";
				
				if(!parentQuestionId.isEmpty()) {
					for (JsonNode qust : questions) {
						if(parentQuestionId.equals(qust.get("question__c") != null ?  qust.get("question__c").asText() : "")){
							if(qust.has("is_goal__c")) {
								String isGoalValue = qust.get("is_goal__c").asText();
								if (isGoalValue!=null && isGoalValue.equals("t")) {
									isParentGoal = true;
								}
							}
							if(qust.has("goal_brand__c")) {
								JsonNode jsonNode = qust.get("goal_brand__c");
								parentGoalBrand = jsonNode.asText() != null ? jsonNode.asText() : "";
							}
							// if parent found then further loop is not required.
							break;
						}
					}
				}
				
				
				if(questionObj.has("sfid")) {
					questionObj.put("question__c", questionObj.get("sfid").asText());
					questionObj.remove("sfid");
				}
				if(questionObj.has("parent_question__c")) {
					questionObj.remove("parent_question__c");
				}
				
				newSurveyResult.putAll((ObjectNode)survey);
				
				if(newSurveyResult.has("sfid")) {
					newSurveyResult.remove("sfid"); 
				}
				
				newSurveyResult.remove("questions");
				
				newSurveyResult.putAll(questionObj);
				
				// Get Answer Options
				String answerOptionsText = "";
				try {
				    answerOptionsText = newSurveyResult.get("original_answer_options__c").asText();
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
				    String answerValue = newSurveyResult.get("answer_value__c").asText();
				    // Check for answerText value if answer_value is null
				    if (answerValue.equals("null")) {
				        answerValue = newSurveyResult.get("answer_text__c").asText();
				        needMatch = true;
				    }
				    if (answerValue.equals("null")) {
				        scoreTot += 0;
				    }
				    
				    List<AnswerOptions> answerOptions = getAnswerOptions(answerOptionsText);
				    // temp Int
				    int tempHigh = 0;
				    int scoredSurveyResult = 0;
				    
				    String resultBrandExternalId = UUID.randomUUID().toString();
					newSurveyResult.put("result_brand_ext_id__c", resultBrandExternalId);
					
				    for(int i=0;i < answerOptions.size();i++) {
				        int answerOptionScore = Integer.parseInt(answerOptions.get(i).score);
				        int answerGoalScore = Integer.parseInt(answerOptions.get(i).goalScore);
				        String valueBrandId = answerOptions.get(i).valueBrandId;
				        // Potential Score:
					    // 1. Add up all scores for one that has Multi-Select
					    if (newSurveyResult.get("question_type__c").asText().equals("Multi-Select")) {
					        StringTokenizer multiSelectValues = new StringTokenizer(answerValue, ";");
					        while(multiSelectValues.hasMoreTokens()) {
        						String msv = multiSelectValues.nextToken();
        						if (msv.equals(answerOptions.get(i).value)) {
        						    // Total score: Total only scores where value matches
        						    scoreTot += answerOptionScore;
        						    scoredSurveyResult += answerOptionScore;
        						    if(valueBrandId!=null && valueBrandId!=""){
    					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, valueBrandId));
    					            } else if(isGoal && !goalBrand.equals("")){
    					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, goalBrand));
    					            } else if(isParentGoal && !parentGoalBrand.equals("")){
    					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, parentGoalBrand));
    					            }
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
					            if(valueBrandId!=null && valueBrandId!=""){
					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, valueBrandId));
					            } else if(isGoal && !goalBrand.equals("")){
					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, goalBrand));
					            } else if(isParentGoal && !parentGoalBrand.equals("")){
					            	surveyResultBrandList.add(createSurveyResultBrand(resultBrandExternalId, parentGoalBrand));
					            }
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
				    
				    newSurveyResult.put("score__c", Integer.toString(scoredSurveyResult));
				}
				
				newSurveyResult.put("survey_date__c", dateToPostgresString(new Date(System.currentTimeMillis()), false));
				
				if(newSurveyResult.has("id")) {
					newSurveyResult.remove("id");
				}
			
				if(newSurveyResult.has("question_type__c") && newSurveyResult.get("question_type__c").asText().equals("Multi-Select") && newSurveyResult.has("answer_text__c") && newSurveyResult.get("answer_text__c").asText().length() > 0) {
					if(newSurveyResult.get("answer_text__c").asText().equals("None of the Above")) {
						newSurveyResult.put("selected_answers__c", 0);
					} else {
						newSurveyResult.put("selected_answers__c", newSurveyResult.get("answer_text__c").asText().replaceAll("[^;]","").length() + 1);
					}
				}
				
				if(newSurveyResult.has("question_type__c") && newSurveyResult.get("question_type__c").asText().equals("Multi-Select") && newSurveyResult.has("delimitedAnswerOptions") && newSurveyResult.get("delimitedAnswerOptions").asText().length() > 0) {
					newSurveyResult.put("possible_answers__c", newSurveyResult.get("delimitedAnswerOptions").asText().replaceAll("[^,]","").length() + 1);
				}
				
				// Some kind of increment?
				//newSurvey.put("question_number__c", );
				// Survey Submission sfid
				newSurveyResult.put("DD_Survey_Submission__c__External_Id__c", externalId);
				
				// creating photo records 
				if(question.has("photos")){
					String photoExternalId = UUID.randomUUID().toString();
					newSurveyResult.put("Result_Ext_ID__c", photoExternalId);

					ArrayNode questionPhotos= (ArrayNode) question.get("photos");
					for (JsonNode jsonNode : questionPhotos) {
						ObjectNode newPhoto = mapper.createObjectNode();
						newPhoto.put("DMS_Survey_Result__c__Result_Ext_ID__c", photoExternalId);
						newPhoto.put("Photo_URL__c", jsonNode.get("externalPath"));
						photosList.add(newPhoto);
					}
				}
				
				
				clearTransientFields(newSurveyResult, surveyResultFields);
				// Add it to an arrayList
				surveyResultsList.add((ObjectNode)newSurveyResult);
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
			
		} else {
			throw new DiageoServicesException("questions field is required to save survey");
		}
		
		return surveySubmission;
	} 

    private ObjectNode createSurveyResultBrand(String externalId, String brandId ) {
    	ObjectNode newResultBrand = mapper.createObjectNode();
	    newResultBrand.put("dms_survey_result__c__result_brand_ext_id__c", externalId);
	    newResultBrand.put("Brand__c", brandId);
		return newResultBrand;
	}

    private void insertSurveyResults(List<ObjectNode> surveyResults) throws DiageoServicesException {
    	// TODO: clear the name field that is being copied from question
        for (ObjectNode surveyR : surveyResults) {
            insert(surveyR, "dms_survey_result__c");
        }
    }
	
    private void insertSurveyResultsBrands(List<ObjectNode> surveyResultsBrandList) throws DiageoServicesException {
    	for (ObjectNode srBrand : surveyResultsBrandList) {
    		insert(srBrand, "dd_survey_result_brands__c");
    	}
    }
	
    private void insertSurveyResultsPhotos(List<ObjectNode> photosList) throws DiageoServicesException {
    	for (ObjectNode photoR : photosList) {
    		insert(photoR, "dd_survey_result_photos__c");
    	}
    }
    
    public ArrayNode getSS(String query) throws DiageoServicesException {
        return queryToJson(query);
    }
    
	public ArrayNode getSurveys() throws DiageoServicesException {
		
		ArrayNode surveys = queryToJson(MessageFormat.format(getSQLStatement("survey-query-universal"), this.contactId));

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
			ArrayNode questions = queryToJson(MessageFormat.format(getSQLStatement("question-query-universal"), surveyIds.toString()));
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
			
		}else{
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Survey not found.");
			result.add(error);
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
		ArrayNode goalsForProcessing = queryToJson(MessageFormat.format(getSQLStatement("query-goals-for-processing"), surveySubmissionExternalId, contactId));
	
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
				
				currentObject = nextObject;
			}
			
			System.out.println(groupedQuestions);
			
			StringBuilder inStatement = new StringBuilder();
			// For each question instantiate the appropriate goal calculator depending on the question type
			for(int i = 0; i < groupedQuestions.size(); i++) {
				ArrayNode questionGroup = (ArrayNode) groupedQuestions.get(i);
				
				if(questionGroup.size() > 0) {
					Map<Integer, Integer> surveyResultAchievemetns = new HashMap<Integer, Integer>();
					
					ObjectNode firstQuestion = (ObjectNode) questionGroup.get(0);
					
					GoalCalculator calc = GoalCalculatorFactory.getInstance().getGoalCalculator(firstQuestion);
					
					if(calc != null) {
						executeStatement(MessageFormat.format(getSQLStatement("update-goal-achievement"), calc.calculateGoalAchievement(questionGroup, surveyResultAchievemetns), firstQuestion.get("assigned_goal__c").asText()));
					}

					String separator = "";
					// Update survey results
					for(Integer id : surveyResultAchievemetns.keySet()) {
						// Get list of ids that is the parameter for the query
						inStatement.append(separator);
						inStatement.append(id);
						separator=",";

						executeStatement(MessageFormat.format(getSQLStatement("update-goal-achievement-on-survey-result"), String.valueOf(surveyResultAchievemetns.get(id)), String.valueOf(id)));
					}
				}
			}
			
			// Get brands for processing
			ArrayNode brands = queryToJson(MessageFormat.format(getSQLStatement("query-brand-results-for-goal-processing"), inStatement));
			
			for(JsonNode brand : brands) {
				// Process the brands using the goal calculator
				GoalCalculator calc = GoalCalculatorFactory.getInstance().getGoalCalculator((ObjectNode)brand);

				executeStatement(MessageFormat.format(getSQLStatement("update-flag-on-survey-result-brands"), String.valueOf(brand.get("id").asText()), calc.processBrands((ObjectNode)brand).booleanValue() ? "t" : "f"));
			}
		}
	}
}
