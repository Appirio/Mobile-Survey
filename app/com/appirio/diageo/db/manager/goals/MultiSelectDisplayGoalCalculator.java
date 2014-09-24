package com.appirio.diageo.db.manager.goals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MultiSelectDisplayGoalCalculator implements GoalCalculator {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public int calculateGoalAchievement(ArrayNode questionGroup,
			Map<Integer, Integer> surveyResultAchievement)
			throws DiageoServicesException {
		try {
			Set<String> positiveAnswerAccounts = new HashSet<String>();
			Set<String> positiveAnswer = new HashSet<String>();
			int result = 0;

			if(questionGroup.size() > 0) {
				JsonNode firstQuestion = questionGroup.get(0);
				
				// need to parse the answer options and create a data structure to retrieve answer / goal score easily 
				ArrayNode answerOptions = (ArrayNode) mapper.readTree(firstQuestion.get("answer_options__c").asText());
				
				for(JsonNode option : answerOptions) {
					if(option.get("goalScore").asInt() > 0) {
						positiveAnswer.add(option.get("value").asText());
					}
				}
				
				// For survey result
				for(JsonNode question : questionGroup) {
					
					// Get the account id, we should only count a response for a particular account once
					String accountId = question.get("account__c").asText();
					
					String answer = question.get("answer_text__c").asText();
					
					// Need to calculate how the achievement will be counted for this particular survey result
					// and add to data structure that will later be used by the invoking function to update the
					// survey results with the result level achievement calculated achievement
					for(String s : answer.split(";")) {
						if(!positiveAnswerAccounts.contains(accountId) && positiveAnswer.contains(s)) {
							positiveAnswerAccounts.add(accountId);
							
							surveyResultAchievement.put(question.get("id").asInt(), 1);

							result += 1;
						} else {
							surveyResultAchievement.put(question.get("id").asInt(), 0);
						}
					}
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
	public Boolean processBrands(ObjectNode brand) {
		return (brand.has("goal_achievement__c") && brand.get("goal_achievement__c").asInt() > 0) && 
					(brand.has("is_goal__c") && brand.get("is_goal__c").asText().equals("t"));
	}	

}
