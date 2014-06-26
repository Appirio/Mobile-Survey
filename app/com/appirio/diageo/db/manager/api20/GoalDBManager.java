package com.appirio.diageo.db.manager.api20;

import java.text.MessageFormat;
import java.util.Date;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.DBManager;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class GoalDBManager extends DBManager {

	public GoalDBManager() throws DiageoServicesException {
		super();
	}

	public ArrayNode getGoals(String contactId, String startDate, String endDate) throws DiageoServicesException {
		return this.queryToJson(MessageFormat.format(getSQLStatement("goals-query-20"), contactId, startDate, endDate));
	}
}
