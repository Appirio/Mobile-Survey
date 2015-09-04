import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.mobilesurvey.db.MSServicesException;
import com.appirio.mobilesurvey.db.manager.AccountDBManager;
import com.appirio.mobilesurvey.db.manager.api14.AccountDBManager14;
import com.appirio.mobilesurvey.db.manager.api15.AccountDBManager15;
import com.appirio.mobilesurvey.db.manager.api17.AccountDBManager17;
import com.fasterxml.jackson.databind.JsonNode;


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
    public void testFindAccounts17() {
    	
    	try {
    		AccountDBManager17 mgr = new AccountDBManager17("1");
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertTrue(result.size() == 1);
        	
        	result = result.get(0);
        	
        	Assert.assertTrue(result.isObject());
        	Assert.assertTrue(result.has("proximityAccounts"));
        	Assert.assertTrue(result.has("myAccounts"));
        	
        	Assert.assertEquals(2, result.get("proximityAccounts").size());
        	Assert.assertEquals(2, result.get("myAccounts").size());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
    
    @Test
    public void testFindAccountsLimited17() {
    	
    	try {
    		AccountDBManager17 mgr = new AccountDBManager17("1");
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertTrue(result.size() == 1);
        	
        	result = result.get(0);
        	
        	Assert.assertTrue(result.isObject());
        	Assert.assertTrue(result.has("proximityAccounts"));
        	Assert.assertTrue(result.has("myAccounts"));
        	
        	Assert.assertEquals(2, result.get("proximityAccounts").size());
        	Assert.assertEquals(2, result.get("myAccounts").size());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
    
    @Test
    public void testFindAccounts15() {
    	
    	try {
    		AccountDBManager15 mgr = new AccountDBManager15();
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0, 100);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertEquals(2, result.size());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }    

    @Test
    public void testFindAccounts14() {
    	
    	try {
    		AccountDBManager14 mgr = new AccountDBManager14();
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0, 100);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertEquals(2, result.size());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
    
    @Test
    public void testFindAccounts() {
    	
    	try {
    		AccountDBManager mgr = new AccountDBManager();
        	
        	JsonNode result = mgr.findAccounts(10.0, 10.0, 200.0);
        	
        	Assert.assertTrue(result.isArray());
        	
        	Assert.assertEquals(2, result.size());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }    
    
    @Test
    public void testGetAccount() {
    	
    	try {
    		AccountDBManager mgr = new AccountDBManager();
        	
        	JsonNode result = mgr.getAccount("1");
        	
        	Assert.assertTrue(result.isObject());
        	
        	Assert.assertEquals("My Favorite Liquor Store", result.get("name").asText());
        	
        	mgr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }    
}
