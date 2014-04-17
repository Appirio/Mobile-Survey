import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import play.api.libs.iteratee.Enumerator;
import play.mvc.Result;
import play.mvc.Results.Status;
import play.mvc.SimpleResult;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api17.AccountDBManager17;
import com.fasterxml.jackson.databind.JsonNode;

import controllers.AccountServices;


public class TestAccountServices {

	private TestDBManager testDBManager;
	
	
	@Before
	public void setup() {
		try {
			testDBManager = new TestDBManager();
			
			// delete data
			testDBManager.clearDB();
			testDBManager.populateTestAccounts();
			
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
		} catch (DiageoServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
    @Test
    public void testGetAccounts17() {
    	
    	try {
    		AccountDBManager17 mgr = new AccountDBManager17("1");
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertTrue(result.size() == 1);
        	
        	result = result.get(0);
        	
        	Assert.assertTrue(result.isObject());
        	Assert.assertTrue(result.has("proximityAccounts"));
        	Assert.assertTrue(result.has("myAccounts"));
        	
        	Assert.assertEquals(result.get("proximityAccounts").size(), 2);
        	Assert.assertEquals(result.get("myAccounts").size(), 2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
    
    @Test
    public void testGetAccountsLimited17() {
    	
    	try {
    		AccountDBManager17 mgr = new AccountDBManager17("1");
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertTrue(result.size() == 1);
        	
        	result = result.get(0);
        	
        	Assert.assertTrue(result.isObject());
        	Assert.assertTrue(result.has("proximityAccounts"));
        	Assert.assertTrue(result.has("myAccounts"));
        	
        	Assert.assertEquals(result.get("proximityAccounts").size(), 2);
        	Assert.assertEquals(result.get("myAccounts").size(), 2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
    
}
