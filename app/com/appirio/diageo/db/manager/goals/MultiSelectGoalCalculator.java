package com.appirio.diageo.db.manager.goals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MultiSelectGoalCalculator implements GoalCalculator {

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public int calculateGoalAchievement(ArrayNode questionGroup,
			Map<Integer, Integer> surveyResultAchievement)
			throws DiageoServicesException {
		try {
			Map<String, Set<String>> positiveAnswersPerAccount = new HashMap<String, Set<String>>();
			int result = 0;

			if(questionGroup.size() > 0) {
				JsonNode firstQuestion = questionGroup.get(0);
				
				// For the answers received, we need to count the score once for each account, for that we use a set
				Map<String, Integer> answerScoreMap = new HashMap<String, Integer>();

				// need to parse the answer options and create a data structure to retrieve answer / goal score easily 
				ArrayNode answerOptions = (ArrayNode) mapper.readTree(firstQuestion.get("answer_options__c").asText());
				
				for(JsonNode option : answerOptions) {
					answerScoreMap.put(option.get("value").asText(), option.get("goalScore").asInt());
				}
				
				// For survey result
				for(JsonNode question : questionGroup) {
					
					// Get the account id, we should only count a response for a particular account once
					String accountId = question.get("account__c").asText();
					Set<String> accountAnswers = positiveAnswersPerAccount.get(accountId);

					// Store answers per account in a data structure to count later
					if(accountAnswers == null) {
						positiveAnswersPerAccount.put(accountId, new HashSet<String>());
						accountAnswers = positiveAnswersPerAccount.get(accountId);
					}
					
					String answer = question.get("answer_text__c").asText();
					
					// Need to calculate how the achievement will be counted for this particular survey result
					// and add to data structure that will later be used by the invoking function to update the
					// survey results with the result level achievement calculated achievement
					Integer resultAchievement = surveyResultAchievement.get(question.get("id").asInt());
					
					if(resultAchievement == null) {
						resultAchievement = 0;
					}

					for(String s : answer.split(";")) {
						if(!accountAnswers.contains(s)) {
							accountAnswers.add(s);
							
							resultAchievement += answerScoreMap.get(s) != null ? answerScoreMap.get(s) : 0;
						}
					}
					
					surveyResultAchievement.put(question.get("id").asInt(), resultAchievement); 
				}
				
				// sum the result achievement values for each survey submissions
				for(Integer key : surveyResultAchievement.keySet()) {
					result += surveyResultAchievement.get(key);
				}
			}

			// Return calculated score
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}	
	}

	@Override
	public Map<Integer, Boolean> processBrands(ArrayNode brands) {
		Map<Integer, Boolean> result = new HashMap<Integer, Boolean>();
		
		for(JsonNode brand : brands) {
			result.put(brand.get("id").asInt(), 
					(brand.has("is_goal__c") && brand.get("is_goal__c").asText().equals("t")) && 
					(brand.has("goal_achievement__c") && brand.get("goal_achievement__c").asInt() > 0) && 
					(brand.has("answer_text__c") && brand.has("answer__c") && brand.get("answer_text__c").asText().indexOf(brand.get("answer__c").asText()) > 0));
		}
		
		return result;
	}
	
}
