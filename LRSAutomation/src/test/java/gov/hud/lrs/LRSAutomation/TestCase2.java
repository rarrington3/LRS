package gov.hud.lrs.LRSAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestCase2 {
	//private WebDriver driver;
	
	@Test
	public void lrstest_firefox(){
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver-v0.9.0-win64\\geckodriver.exe");
		WebDriver driver = new MarionetteDriver();
		driver.get("http://www.cnn.com");
		//Boolean isFound = IsElementPresent(driver, By.cssSelector("input[value*='Google Search']"));
      //  Assert.assertTrue(isFound);
		driver.quit();

	}

    private Boolean IsElementPresent(WebDriver driver, By by) {
        try
        {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) { return false; }
    }
 
   
}
