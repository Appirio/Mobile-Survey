import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.DBManager;


public class TestDBManager extends DBManager {

	public TestDBManager() throws DiageoServicesException {
		super();
		
		if(!DBManager.IS_TEST) {
			throw new DiageoServicesException("This manager can only be used when running tests!!!");
		}
	}
	
	public void clearDB() throws DiageoServicesException {
		executeStatement(getSQLStatement("clear-test-db"));
	}

	public void populateTestAccounts() throws DiageoServicesException {
		executeStatement(getSQLStatement("populate-test-accounts"));
	}

	public void populateTestSurveys() throws DiageoServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-and-questions"));
	}
	
	public void populateTestSurveysCategoryFilterData() throws DiageoServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-category-filter"));
	}
	

	public void populateGoalDbManagerTestData() throws DiageoServicesException {
		executeStatement(getSQLStatement("populate-goal-dbmanager-test-data"));
	}
}
