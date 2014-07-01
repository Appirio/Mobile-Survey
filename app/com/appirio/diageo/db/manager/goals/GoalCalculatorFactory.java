package com.appirio.diageo.db.manager.goals;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GoalCalculatorFactory {

	private static GoalCalculatorFactory instance;
	
	private SingleSelectGoalCalculator singleSelectGoalCalculator;
	private MultiSelectGoalCalculator multiSelectGoalCalculator;
	
	private GoalCalculatorFactory() {
		
	}
	
	public static GoalCalculatorFactory getInstance() {
		if(instance == null) {
			instance = new GoalCalculatorFactory();
		}
		
		return instance;
	}
	
	/**
	 * Receives object node that represents a question, question_type__c is required to determine the type of
	 * goal calculator to be generated
	 * 
	 * @param question
	 * @return
	 * @throws DiageoServicesException 
	 */
	public GoalCalculator getGoalCalculator(ObjectNode question) throws DiageoServicesException {
		if(question.get("question_type__c").asText().equalsIgnoreCase("Multi-Select")) {
			return multiSelectGoalCalculator;
		} else if (question.get("question_type__c").asText().equalsIgnoreCase("Multi-Select")) {
			return singleSelectGoalCalculator;
		}
		
		throw new DiageoServicesException("Question type not supported for goal calculation");
	}
}
