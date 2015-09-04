import com.appirio.mobilesurvey.db.MSServicesException;
import com.appirio.mobilesurvey.db.manager.DBManager;


public class TestDBManager extends DBManager {

	public TestDBManager() throws MSServicesException {
		super();
		
		if(!DBManager.IS_TEST) {
			throw new MSServicesException("This manager can only be used when running tests!!!");
		}
	}
	
	public void clearDB() throws MSServicesException {
		executeStatement(getSQLStatement("clear-test-db"));
	}

	public void populateTestAccounts() throws MSServicesException {
		executeStatement(getSQLStatement("populate-test-accounts"));
	}

	public void populateTestSurveys() throws MSServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-and-questions"));
	}
	
	public void populateTestSurveysCategoryFilterData() throws MSServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-category-filter"));
	}
	

	public void populateGoalDbManagerTestData() throws MSServicesException {
		executeStatement(getSQLStatement("populate-goal-dbmanager-test-data"));
	}

	public void populateTestSurveyWithAnswerBrandsData() throws MSServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-with-answer-brand-data"));
	}

	public void populateContacts() throws MSServicesException {
		executeStatement(getSQLStatement("populate-contacts"));
	}

	public void populateTestSurveyWithoutAnswerBrandsData() throws MSServicesException {
		executeStatement(getSQLStatement("populate-test-surveys-without-answer-brand-data"));
	}
}
