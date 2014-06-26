import java.text.DateFormat;
import java.util.Calendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api17.AccountDBManager17;
import com.appirio.diageo.db.manager.api20.GoalDBManager;
import com.fasterxml.jackson.databind.JsonNode;


public class TestGoalDBManager {

	private TestDBManager testDBManager;
	
	
	@Before
	public void setup() {
		try {
			testDBManager = new TestDBManager();
			
			// delete data
			testDBManager.clearDB();
			testDBManager.populateTestSurveys();
			
			// create test data
		} catch (DiageoServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			// delete data
			testDBManager.clearDB();
			
			testDBManager.close();
		} catch (DiageoServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetGoals() {
    	try {
    		GoalDBManager mgr = new GoalDBManager();
        	
    		Calendar c = Calendar.getInstance();
    		
    		c.set(2014, 5, 1);
    		
        	JsonNode result = mgr.getGoals("1", "2014-06-01", "2014-06-01");
        	
        	Assert.assertTrue(result.isArray());

        	Assert.assertTrue(result.size() == 1);
        	
        	/*
        	result = result.get(0);
        	
        	Assert.assertTrue(result.isObject());
        	Assert.assertTrue(result.has("proximityAccounts"));
        	Assert.assertTrue(result.has("myAccounts"));
        	
        	Assert.assertEquals(2, result.get("proximityAccounts").size());
        	Assert.assertEquals(2, result.get("myAccounts").size());
        	*/
        	
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
}
