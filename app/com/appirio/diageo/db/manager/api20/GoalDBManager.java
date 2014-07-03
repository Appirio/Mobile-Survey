package com.appirio.diageo.db.manager.api20;

import java.text.MessageFormat;
import java.util.HashMap;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.DBManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GoalDBManager extends DBManager {

	public GoalDBManager() throws DiageoServicesException {
		super();
	}

	/**
	 * 
	 **/
	public ArrayNode getGoalAchievements(String contactId, String startDate, String endDate, 
			Boolean includeDetails)  throws DiageoServicesException{
		
		ArrayNode result = mapper.createArrayNode();
		
		// Fetching assigned goals for contact where goal start and end date overlap given start and end date.
		ArrayNode assignedGoalList = queryToJson(MessageFormat.format(getSQLStatement("assigned-goal-query-20"), contactId, startDate, endDate));
		
		if(assignedGoalList!=null && assignedGoalList.size()>0){
			StringBuilder questionIds = new StringBuilder();
			String separator = "";
			for(JsonNode survey : assignedGoalList) {
				questionIds.append(separator);
				questionIds.append("'");
				questionIds.append(survey.get("dd_survey_question__c").asText());
				questionIds.append("'");
				separator = ",";
			}
			
			// Fetching survey question related to above assigned goals
			ArrayNode surveyQuestionList =queryToJson(MessageFormat.format(getSQLStatement("survey-question-query-with-id-filter-20"), questionIds.toString()));
			
			// if includeDetails == true then fetching survey results for questions.
			HashMap<String,ArrayNode> surveyResultMap =new HashMap<String, ArrayNode>();
			if(includeDetails){
				// Fetching survey results for contact and assigned goal questions 
				ArrayNode surveyResultList = queryToJson(MessageFormat.format(getSQLStatement("survey-results-query-with-question-id-filter-20"), contactId, questionIds.toString()));
				for (JsonNode jsonNode : surveyResultList) {
					String key = jsonNode.get("question__c").asText();
					ArrayNode valueList = surveyResultMap.get(key); 
					if(valueList==null){
						valueList = mapper.createArrayNode();
					}
					valueList.add(jsonNode);
					surveyResultMap.put(key, valueList);
				}
			}
			
			// building JSON result 
			int questionCt = 0; 
			for(int assignedGoalCt = 0 ; assignedGoalCt < assignedGoalList.size(); assignedGoalCt ++) {
				
				ObjectNode assignedGoalObj = (ObjectNode) assignedGoalList.get(assignedGoalCt);
				ObjectNode resultObject = mapper.createObjectNode();
				resultObject.put("assignedGoal", assignedGoalObj);
				
				while(questionCt < surveyQuestionList.size() && (surveyQuestionList.get(questionCt).get("sfid").asText().equals(assignedGoalObj.get("dd_survey_question__c").asText()))) {
					
					ObjectNode question = (ObjectNode)surveyQuestionList.get(questionCt);
					if(includeDetails){ // adding survey results with questions if includeDetails == true
						question.put("surveyResults", surveyResultMap.get(question.get("sfid").asText()));
					}
					resultObject.put("surveyQuestion", question);
					questionCt++;
				}
				
				result.add(resultObject);
			}
		}
		return result;
	}
	
	public ArrayNode getContactGoals(String contactId) throws DiageoServicesException {
		return queryToJson(MessageFormat.format(getSQLStatement("query-goals-for-contact"), contactId));
	}
}
