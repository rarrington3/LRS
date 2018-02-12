package gov.hud.lrs.LRSAutomation;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import gov.hud.lrs.LRSAutomation.base.*;

public class DemoClass extends Page {
	 private static Logger Log = Logger.getLogger(Log.class.getName());
	 
	public  void doLogin(){
		Reporter.log("Launch the browser");
		Log.info("Launch the browser");
		openBrowser();
		Reporter.log("The browser is launched");
		Log.info("The browser is launched");
		Reporter.log("Launch the test URL");
		Log.info("Once the browser is launched, get to the test URL");
		driver.get(CONFIG.getProperty("testURL"));
		Reporter.log("The test url is launched");
		Log.info("The test site is up and running");
		Reporter.log("Click on the workload link");
		Log.info("Click on the workload link");
		delay();
        click("workload");
        Reporter.log("Click on the review link");
        Log.info("Click on the review link");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        click("review1");
        Reporter.log("Click on the defect area link");
        Log.info("Click on the defect area link");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        click("defectarea1");
        Reporter.log("Close the browser");
        Log.info("Close the browser");
        driver.quit();
	
	}
	
}





