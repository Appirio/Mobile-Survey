package com.appirio.diageo.db.manager.goals;

import java.util.Map;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface GoalCalculator {
	
	/** Expect array of json objects with the following fields:
	 *  answer_options__c representing the question answer options 
	 *  answer_text__c representing the answer provided by the user
	 * 
	 * @param results
	 * @param surveyResultAchievement survey result level achievement to update
	 * @return
	 * @throws DiageoServicesException
	 *  
	 */
	int calculateGoalAchievement(ArrayNode questionGroup,
			Map<Integer, Integer> surveyResultAchievement)
			throws DiageoServicesException;
	
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
	
	Map<Integer, Boolean> processBrands(ArrayNode brands);
	
}
