package com.appirio.diageo.db.manager.goals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SingleSelectGoalCalculator implements GoalCalculator {

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public int calculateGoalAchievement(ArrayNode questionGroup) throws DiageoServicesException {
		try {
			Set<String> countedAccountIds = new HashSet<String>();
			int result = 0;

			if(questionGroup.size() > 0) {
				JsonNode firstQuestion = questionGroup.get(0);
				
				// For the answers received, we need to count the score once for each account, for that we use a set
				Map<String, Integer> positiveAnswers = new HashMap<String, Integer>();

				// need to parse the answer options to determine which answers are goals
				ArrayNode answerOptions = (ArrayNode) mapper.readTree(firstQuestion.get("answer_options__c").asText());
				
				for(JsonNode option : answerOptions) {
					if(option.has("goalScore") && option.get("goalScore").asInt() > 0) {
						positiveAnswers.put(option.get("value").asText(), option.get("goalScore").asInt());
					}
				}
				
				for(JsonNode question : questionGroup) {
					// Need to make sure we count positive answers to against an account only once
					if(!countedAccountIds.contains(question.get("account__c").asText())) {
						if(positiveAnswers.keySet().contains(question.get("answer_text__c").asText())) {
							countedAccountIds.add(question.get("account__c").asText());
							result += positiveAnswers.get(question.get("answer_text__c").asText());
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

}
