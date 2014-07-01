package com.appirio.diageo.db.manager.goals;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class MultiSelectGoalCalculator implements GoalCalculator {

	@Override
	public int calculateGoalAchievement(ArrayNode results) {
		// TODO Ajay - the results object will contain all survey results of a particular 
		//             question of type multi select. Below please write the algorithm to 
		// 			   calculate the goal for single selects as defined in the google doc	

		return 0;
	}

}
