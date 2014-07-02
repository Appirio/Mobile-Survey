package com.appirio.diageo.db.manager.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SingleSelectGoalCalculator implements GoalCalculator {

	@Override
	public int calculateGoalAchievement(ArrayNode questionGroup) {
		// TODO Ajay - the results object will contain all survey results of a particular 
		//             question of type single select. Below please write the algorithm to 
		// 			   calculate the goal for single selects as defined in the google doc	
		
		// TODO NOT TESTED
		HashMap<String,ArrayList<SurveyResults>> data = new HashMap<String, ArrayList<SurveyResults>>();
		int overallAchievement = 0;

		// Creating List of results grouped by account 
		for (JsonNode questionNode : questionGroup) {
		        
		        String key = questionNode.get("account__c").asText();
		        
		        SurveyResults resultObj = new SurveyResults();
		        resultObj.sfId = questionNode.get("sfid").asText() ;
		        resultObj.accountId = questionNode.get("account__c").asText() ;
		        resultObj.answer = questionNode.get("answer_text__c").asText();
		        resultObj.questionId =  questionNode.get("question__c").asText();
		        resultObj.goalAchievement = questionNode.get("goal_achievement__c").asInt(0);
		        
		        ArrayList<SurveyResults> accountResults = data.get(key);
		        if(accountResults == null){
		                accountResults = new ArrayList<SurveyResults>();
		        }
		        accountResults.add(resultObj);
		        data.put(key, accountResults);
		}
		
		// calculating overall achievement for all account 
		for (Entry<String, ArrayList<SurveyResults>> entry : data.entrySet()) {
		        String key = entry.getKey();
		        
		        // calculate account achievement for single account.
		        ArrayList<SurveyResults> resultList = entry.getValue();
		        Integer accountAchievement = 0;
		        for (SurveyResults resultObj : resultList) {
		                
		                if(accountAchievement < resultObj.goalAchievement){
		                        accountAchievement = resultObj.goalAchievement;
		                }
		        }
		        
		        overallAchievement += accountAchievement; 
		}
		return overallAchievement;
	}

}
