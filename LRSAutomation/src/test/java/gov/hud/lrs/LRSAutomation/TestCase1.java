package gov.hud.lrs.LRSAutomation;

import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.Test;
import gov.hud.lrs.LRSAutomation.util.*;

public class TestCase1 {
	
	@Test
	public void defectareaTest(){
		Logger Log = Logger.getLogger(Log.class.getName());
		Log.info("Start of the Testcase");
		Reporter.log("Start of the Testcase");
		DemoClass dc = new DemoClass();
		dc.doLogin();
		Log.info("End of the test case");
		Reporter.log("End of the test case");
	}

}
