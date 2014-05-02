import java.io.IOException;
import java.util.UUID;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
					
					Assert.assertEquals("child survey 1", ((ArrayNode)survey.get("childSurveys")).get(0).get("name").asText());
					Assert.assertEquals("child survey 2", ((ArrayNode)survey.get("childSurveys")).get(1).get("name").asText());
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
					
					Assert.assertEquals("child survey 1", ((ArrayNode)survey.get("childSurveys")).get(0).get("name").asText());
					Assert.assertEquals("child survey 2", ((ArrayNode)survey.get("childSurveys")).get(1).get("name").asText());
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
	
	@Test
	public void testCreateSurvey17() {
		try {
			String externalId = UUID.randomUUID().toString();
			JsonNode body = new ObjectMapper().readTree("[{\"isparent__c\":\"f\",\"parent_survey__c\":null,\"enable_edit_on_review_screen__c\":\"t\",\"name\":\"Holiday Cold Box Survey\",\"sfid\":\"a2iA0000000L7FSIA0\",\"survey_type__c\":\"Product\",\"first_question__c\":\"a2gA0000000LARDIA4\",\"grading_scale__c\":null,\"total_possible_score__c\":\"0\",\"questions\":[{\"include_none_of_the_above__c\":\"f\",\"conditional_answer__c\":null,\"next_question__c\":null,\"label_for_add_l_comments__c\":null,\"sfid\":\"a2gA0000000LARDIA4\",\"question_text__c\":\"How many Facings?\",\"parent_question__c\":null,\"name\":\"Q-0005\",\"question_type__c\":\"Count\",\"dms_survey__c\":\"a2iA0000000L7FSIA0\",\"answer_text__c\":null,\"answer_value__c\":\"6\"}],\"count\":1,\"display\":\"t\",\"location\":{\"tdlinx_outlet_city__c\":\"Scottsdale\",\"tdlinx_outlet_zip_code__c\":\"852546404\",\"tdlinx_outlet_state__c\":\"AZ\",\"tdlinx_outlet_addr__c\":\"6245 E Bell Rd Ste 101\",\"billingstate\":\"AZ\",\"billingcountry\":null,\"billingpostalcode\":\"852546404\",\"billingcity\":\"Scottsdale\",\"billingstreet\":\"6245 E Bell Rd Ste 101\",\"sfid\":\"001A00000124Kg8IAE\",\"tdlinx_outlet_desc__c\":\"Arena Sports Grill\",\"name\":\"Arena Sports Grill\",\"distance\":0.3936360794123505},\"contact__c\":\"003A000001GYixGIAT\",\"account__c\":\"001A00000124Kg8IAE\",\"survey_id\":0,\"upc__c\":\"070195599269\",\"manually_entered_upc_description__c\":\"redman\",\"manually_entered_base_size__c\":\"0.05L\"},{\"isparent__c\":\"f\",\"parent_survey__c\":null,\"enable_edit_on_review_screen__c\":\"t\",\"name\":\"Holiday Cold Box Survey\",\"sfid\":\"a2iA0000000L7FSIA0\",\"survey_type__c\":\"Product\",\"first_question__c\":\"a2gA0000000LARDIA4\",\"grading_scale__c\":null,\"total_possible_score__c\":\"0\",\"questions\":[{\"include_none_of_the_above__c\":\"f\",\"conditional_answer__c\":null,\"next_question__c\":null,\"label_for_add_l_comments__c\":null,\"sfid\":\"a2gA0000000LARDIA4\",\"question_text__c\":\"How many Facings?\",\"parent_question__c\":null,\"name\":\"Q-0005\",\"question_type__c\":\"Count\",\"dms_survey__c\":\"a2iA0000000L7FSIA0\",\"answer_text__c\":null,\"answer_value__c\":\"7\"}],\"count\":1,\"display\":\"t\",\"location\":{\"tdlinx_outlet_city__c\":\"Scottsdale\",\"tdlinx_outlet_zip_code__c\":\"852546404\",\"tdlinx_outlet_state__c\":\"AZ\",\"tdlinx_outlet_addr__c\":\"6245 E Bell Rd Ste 101\",\"billingstate\":\"AZ\",\"billingcountry\":null,\"billingpostalcode\":\"852546404\",\"billingcity\":\"Scottsdale\",\"billingstreet\":\"6245 E Bell Rd Ste 101\",\"sfid\":\"001A00000124Kg8IAE\",\"tdlinx_outlet_desc__c\":\"Arena Sports Grill\",\"name\":\"Arena Sports Grill\",\"distance\":0.3936360794123505},\"contact__c\":\"003A000001GYixGIAT\",\"account__c\":\"001A00000124Kg8IAE\",\"survey_id\":1,\"upc__c\":\"01212901\",\"manually_entered_upc_description__c\":\"pepsi\",\"manually_entered_base_size__c\":\"0.05L\"}] ");
			
			SurveyDBManager manager = new SurveyDBManager();
			manager.createSurvey15(body, externalId);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}
}