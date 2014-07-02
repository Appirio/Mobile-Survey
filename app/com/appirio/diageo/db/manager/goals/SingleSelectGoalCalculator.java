package com.appirio.diageo.db.manager.goals;

import java.util.HashSet;
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
			Set<String> accountIdsWithScore = new HashSet<String>();

			if(questionGroup.size() > 0) {
				JsonNode firstQuestion = questionGroup.get(0);
				
				// For the answers received, we need to count the score once for each account, for that we use a set
				Set<String> positiveAnswers = new HashSet<String>();

				// need to parse the answer options to determine which answers are goals
				ArrayNode answerOptions = (ArrayNode) mapper.readTree(firstQuestion.get("answer_options__c").asText());
				for(JsonNode option : answerOptions) {
					if(option.has("goalScore") && option.get("goalScore").asInt() > 0) {
						positiveAnswers.add(option.get("value").asText());
					}
				}
				
				for(JsonNode question : questionGroup) {
					// if the answer value matches a value with score add account to the resulting list
					if(positiveAnswers.contains(question.get("answer_text__c").asText())) {
						accountIdsWithScore.add(question.get("account__c").asText());
					}
				}
			}

			// Return how for how many accounts the question was answered positively
			return accountIdsWithScore.size();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}
	}

}
