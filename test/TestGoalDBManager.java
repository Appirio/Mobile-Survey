import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.diageo.db.DiageoServicesException;
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
			testDBManager.populateGoalDbManagerTestData();
			
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
        	
    		/*Calendar c = Calendar.getInstance();
    		
    		c.set(2014, 5, 1);*/
    		
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
	
	@Test
	public void testGetGoalAchievements() {
		try {
			GoalDBManager manager = new GoalDBManager();
			
			// Test for goal achievement when records not available for contact 
			JsonNode result = manager.getGoalAchievements("1","2014-11-16","2014-12-20", false);
			Assert.assertEquals(0, result.size());
			
			// Test for goal achievement when records available for contact (when all records fall between start and end dates)
			JsonNode result1 = manager.getGoalAchievements("2","2014-11-16","2014-12-20", false);
			Assert.assertEquals(4, result1.size());
			
			// Test for goal achievement when records available for contact (when no records fall between start and end dates)
			JsonNode result2 = manager.getGoalAchievements("2","2014-05-16","2014-06-20", false);
			Assert.assertEquals(0, result2.size());
			
			// Test for goal achievement when records available for contact (when only one record fall between start and end dates)
			JsonNode result3 = manager.getGoalAchievements("2","2014-11-16","2014-11-20", false);
			Assert.assertEquals(1, result3.size());
			
			// Test for goal achievement when records available for contact (when three record fall between start and end dates)
			// passing true in includeDetail param, returned result should have survey results associated with questions.
			JsonNode result4 = manager.getGoalAchievements("2","2014-12-10","2014-12-26", true);
			Assert.assertEquals(3, result4.size());
			for (JsonNode jsonNode : result4) {
				JsonNode surveyQuestionNode = jsonNode.get("surveyQuestion");
				JsonNode surveyResults = surveyQuestionNode.get("surveyResults");
				Assert.assertNotNull(surveyResults); // this should not be null
			}
			
			// Test for goal achievement when records available for contact (when three record fall between start and end dates)\
			// passing false in includeDetail param, returned result should not have survey results associated with questions.
			JsonNode result5 = manager.getGoalAchievements("2","2014-12-10","2014-12-26", false);
			Assert.assertEquals(3, result5.size());
			for (JsonNode jsonNode : result5) {
				JsonNode surveyQuestionNode = jsonNode.get("surveyQuestion");
				JsonNode surveyResults = surveyQuestionNode.get("surveyResults");
				Assert.assertNull(surveyResults);// includeDetail is false so this should be null
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
