import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.AccountDBManager;
import com.appirio.diageo.db.manager.SurveyDBManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class TestSurveyServices {

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
		} catch (DiageoServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetSurveysByAccountId() {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			
			JsonNode result = manager.getSurveys("1");
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(1, result.size());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testGetSurveysByAccountObject() {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			AccountDBManager accountManager = new AccountDBManager();
			
			ObjectNode account = accountManager.getAccount("1");
			
			JsonNode result = manager.getSurveys(account);
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(1, result.size());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	// TODO: finish testing other survey manager methods
}
