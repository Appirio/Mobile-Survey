package com.appirio.diageo.db.manager.goals;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface GoalCalculator {
	
	/** Expect array of json objects with the following fields:
	 *  answer_options__c representing the question answer options 
	 *  answer_text__c representing the answer provided by the user
	 * 
	 * @param results
	 * @return
	 * @throws DiageoServicesException 
	 */
	public int calculateGoalAchievement(ArrayNode results) throws DiageoServicesException;
	
	/**
	 * 
	*/
	class SurveyResults{
		String sfId;
		String questionId;
		String accountId;
		String question; 
		String answer; 
		Integer goalAchievement;
	}
	
}
