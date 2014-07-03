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

				// need to parse the answer options to determine which answers are goals
				ArrayNode answerOptions = (ArrayNode) mapper.readTree(firstQuestion.get("answer_options__c").asText());
				
				for(JsonNode option : answerOptions) {
					answerScoreMap.put(option.get("value").asText(), option.get("goalScore").asInt());
				}
				
				for(JsonNode question : questionGroup) {
					String accountId = question.get("account__c").asText();
					Set<String> accountAnswers = positiveAnswersPerAccount.get(accountId);

					if(accountAnswers == null) {
						positiveAnswersPerAccount.put(accountId, new HashSet<String>());
						accountAnswers = positiveAnswersPerAccount.get(accountId);
					}
					
					String answer = question.get("answer_text__c").asText();
					
					for(String s : answer.split(";")) {
						accountAnswers.add(s);
					}
				}
				
				for(String key : positiveAnswersPerAccount.keySet()) {
					Set<String> answers = positiveAnswersPerAccount.get(key);
					for(String answer: answers) {
						if(answerScoreMap.containsKey(answer)) {
							result += answerScoreMap.get(answer);
						}
					}
				}
			}

			// Return calculated score
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}	}

}
