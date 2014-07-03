package com.appirio.diageo.db.manager.goals;

import java.util.Map;

import com.appirio.diageo.db.DiageoServicesException;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MultiSelectGoalCalculator implements GoalCalculator {

	@Override
	public int calculateGoalAchievement(ArrayNode questionGroup,
			Map<Integer, Integer> surveyResultAchievement)
			throws DiageoServicesException {
		// TODO Ajay - the results object will contain all survey results of a particular 
		//             question of type multi select. Below please write the algorithm to 
		// 			   calculate the goal for single selects as defined in the google doc	

		return 0;
	}

}
