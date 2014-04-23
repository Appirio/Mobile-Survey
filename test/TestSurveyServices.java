import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.appirio.diageo.db.DiageoServicesException;


public class TestSurveyServices {

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

}
