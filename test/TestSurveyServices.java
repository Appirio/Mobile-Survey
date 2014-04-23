import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.AccountDBManager;
import com.appirio.diageo.db.manager.SurveyDBManager;
import com.appirio.diageo.db.manager.api12.SurveyDBManager12;
import com.appirio.diageo.db.manager.api13.SurveyDBManager13;
import com.appirio.diageo.db.manager.api14.SurveyDBManager14;
import com.appirio.diageo.db.manager.api15.SurveyDBManager15;
import com.appirio.diageo.db.manager.api17.SurveyDBManager17;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
			
			testDBManager.close();
		} catch (DiageoServicesException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	public boolean isAlphabeticalOrder(JsonNode surveys) {
		String currentSurveyName = null;
		
		for(JsonNode survey : surveys) {
			if(currentSurveyName == null) {
				currentSurveyName = survey.get("name").asText();
			}
			
			if(currentSurveyName.compareToIgnoreCase(survey.get("name").asText()) > 0) {
				return false;
			}
		}
		
		return true;
	}
	
	@Test
	public void testGetSurveysByAccountId() {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			
			JsonNode result = manager.getSurveys("1");
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
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
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
			accountManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSurveys() {
		try {
			SurveyDBManager manager = new SurveyDBManager();
			
			JsonNode result = manager.getSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(4, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testGetUniversalSurveys12() {
		try {
			SurveyDBManager12 manager = new SurveyDBManager12();
			
			JsonNode result = manager.getUniversalSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testGetUniversalSurveys13() {
		try {
			SurveyDBManager13 manager = new SurveyDBManager13();
			
			JsonNode result = manager.getUniversalSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSurveysByAccountObject13() {
		try {
			SurveyDBManager13 manager = new SurveyDBManager13();
			AccountDBManager accountManager = new AccountDBManager();
			
			ObjectNode account = accountManager.getAccount("1");
			
			JsonNode result = manager.getSurveys(account);
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
			accountManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetUniversalSurveys14() {
		try {
			SurveyDBManager14 manager = new SurveyDBManager14();
			
			JsonNode result = manager.getUniversalSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSurveysByAccountObject14() {
		try {
			SurveyDBManager14 manager = new SurveyDBManager14();
			AccountDBManager accountManager = new AccountDBManager();
			
			ObjectNode account = accountManager.getAccount("1");
			
			JsonNode result = manager.getSurveys(account);
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(2, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
			accountManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testGetUniversalSurveys15() {
		try {
			SurveyDBManager15 manager = new SurveyDBManager15();
			
			JsonNode result = manager.getUniversalSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(4, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSurveysByAccountObject15() {
		try {
			SurveyDBManager15 manager = new SurveyDBManager15();
			AccountDBManager accountManager = new AccountDBManager();
			
			ObjectNode account = accountManager.getAccount("1");
			
			JsonNode result = manager.getSurveys(account);
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(4, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			manager.close();
			accountManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testGetUniversalSurveys17() {
		try {
			SurveyDBManager17 manager = new SurveyDBManager17();
			
			JsonNode result = manager.getUniversalSurveys();
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(5, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			int parentSurveyCount = 0;
			for(JsonNode survey : result) {
				if(survey.get("name").asText().equals("parent survey")) {
					parentSurveyCount++;
					Assert.assertTrue(survey.has("childSurveys"));
					
					Assert.assertEquals("child survey", ((ArrayNode)survey.get("childSurveys")).get(0).get("name").asText());
				}
			}
			
			Assert.assertEquals(1, parentSurveyCount);

			manager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSurveysByAccountObject17() {
		try {
			SurveyDBManager17 manager = new SurveyDBManager17();
			AccountDBManager accountManager = new AccountDBManager();
			
			ObjectNode account = accountManager.getAccount("1");
			
			JsonNode result = manager.getSurveys(account);
			
			Assert.assertTrue(result.isArray());
			Assert.assertEquals(5, result.size());
			Assert.assertTrue(isAlphabeticalOrder(result));
			
			int parentSurveyCount = 0;
			for(JsonNode survey : result) {
				if(survey.get("name").asText().equals("parent survey")) {
					parentSurveyCount++;
					Assert.assertTrue(survey.has("childSurveys"));
					
					Assert.assertEquals("child survey", ((ArrayNode)survey.get("childSurveys")).get(0).get("name").asText());
				}
			}
			
			Assert.assertEquals(1, parentSurveyCount);

			manager.close();
			accountManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	// TODO: finish testing other survey manager methods
}