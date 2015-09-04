import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.mobilesurvey.db.MSServicesException;
import com.appirio.mobilesurvey.db.manager.ContactDBManager;
import com.appirio.mobilesurvey.db.manager.api17.AccountDBManager17;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class TestContactServices {

	private TestDBManager testDBManager;
	
	
	@Before
	public void setup() {
		try {
			testDBManager = new TestDBManager();
			
			// delete data
			testDBManager.clearDB();
			testDBManager.populateContacts();
			
			// create test data
		} catch (MSServicesException e) {
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
		} catch (MSServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetUser() {
    	try {
    		ContactDBManager mgr = new ContactDBManager();
        	
        	ObjectNode result = mgr.getContact("1");

        	Assert.assertTrue(result.has("sfid"));
        	Assert.assertTrue(result.has("email"));
        	Assert.assertTrue(result.has("assigned_goal_count__c"));
        	Assert.assertTrue(result.has("showDashboard"));
        	Assert.assertTrue(result.get("showDashboard").asBoolean());
        	
        	result = mgr.getContact("2");

        	Assert.assertTrue(result.has("sfid"));
        	Assert.assertTrue(result.has("email"));
        	Assert.assertTrue(result.has("assigned_goal_count__c"));
        	Assert.assertTrue(result.has("showDashboard"));
        	Assert.assertFalse(result.get("showDashboard").asBoolean());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
}
