package gov.hud.lrs.LRSAutomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import gov.hud.lrs.LRSAutomation.util.*;



public class Page {
  // public static Logger Log=null;
   public static WebDriver driver = null;
   public static Properties CONFIG	 = null;
   public static Properties OR	 = null;
   public static boolean isLoggedIn=false;
   public static boolean isAddDBA=false;
   public static boolean isInitialized=false;
   public static boolean isBrowserOpened=false;
   
   public void initialize() throws Exception{
		// logs
		if(!isInitialized){
		// config
		Log.debug("Loading Property files");
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\gov\\hud\\lrs\\LRSAutomation\\config\\config.properties");
		CONFIG.load(fs);
	    OR = new Properties();		   
		fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\gov\\hud\\lrs\\LRSAutomation\\config\\OR.properties");
		OR.load(fs);  
		Log.debug("Loaded Property files successfully");
		Log.debug("Loading XLS Files");   
        isInitialized=true;
				
		}		 
   }
   
// open a browser if its not opened

   public void openBrowser(){
	   try {
		initialize();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(!isBrowserOpened){
		   if(CONFIG.getProperty("browser").equals("Mozilla"))
		   { 
			    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver-v0.9.0-win64\\geckodriver.exe");
				driver = new MarionetteDriver();
		   }   
		   else if(CONFIG.getProperty("browser").equals("IE"))
		        driver=new InternetExplorerDriver();
		   else if(CONFIG.getProperty("browser").equals("Chrome")){
			    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver\\chromedriver.exe");
				ChromeOptions o = new ChromeOptions();
				o.addArguments("disable-extensions");
				driver = new ChromeDriver(o);
			   }
			
		isBrowserOpened=true;
	

	}

	}
	
	
	// close browser
		public void closeBrowser(){
	     	driver.quit();
			isBrowserOpened=false;
		}
	
				
   //Click--for a special case
   
   public void clickByID(String xpath){
	   
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpath))));
	       driver.findElement(By.id(xpath)).click();
	   }catch(Exception e){
	    	// report error
			ErrorUtil.addVerificationFailure(e);
			Log.debug("Cannot find the object with Key---" + xpath);
			}
   }
   // Click
   public void click(String xpathKey){
	   try{
		
			 new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		     driver.findElement(By.xpath(OR.getProperty(xpathKey))).click();
		  }catch (Exception e){
			  // report error
			  ErrorUtil.addVerificationFailure(e);
			  Log.debug("Cannot find the object with Key---" + xpathKey);
			  e.printStackTrace();
	      }
	 }

   // Input
   
   public void input(String xpathKey,String inputText){
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		   driver.findElement(By.xpath(OR.getProperty(xpathKey))).sendKeys(inputText);
	   }catch(Exception e){
		   // report error
		   ErrorUtil.addVerificationFailure(e);
		   Log.debug("Cannot find the object with Key---" + xpathKey);
		   e.printStackTrace();
	   }
   }
   
// Keyboard Input-TAB
   
   public void tab(String xpathKey){
	   try{
		   driver.findElement(By.xpath(OR.getProperty(xpathKey))).sendKeys(Keys.TAB);
	   }catch(Exception e){
		   // report error
		   e.printStackTrace();
	   }
   }
   
   
   // Clear
   public void clear(String xpathKey){
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		    driver.findElement(By.xpath(OR.getProperty(xpathKey))).clear();
		  }catch(Exception e){
		    	// report error
				ErrorUtil.addVerificationFailure(e);
				Log.debug("Cannot find the object with Key---" + xpathKey);
				}
	   }
   
   // Switch to frame
   public void switchFrame(String xpathKey){
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		   driver.switchTo().frame(driver.findElement(By.xpath(OR.getProperty(xpathKey))));
		   }catch(Exception e){
		    	// report error
				ErrorUtil.addVerificationFailure(e);
				Log.debug("Cannot find the object with Key---" + xpathKey);
				}
   }
   
   
   
   // Select a value from a dropdown

        public void Select(String xpathKey, String value){
        	try{
        		new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
    		    new Select(Page.driver.findElement(By.xpath(OR.getProperty(xpathKey)))).selectByVisibleText(value);
        		
        	}catch(Exception e){
    	    	// report error
    			ErrorUtil.addVerificationFailure(e);
    			Log.debug("Cannot find the object with Key---" + xpathKey);
        	}
        	
        	
        }
   
   
   // Select a Row from the table
   
   public void selectRow(String xpathKey){
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		    driver.findElement(By.xpath(OR.getProperty(xpathKey))).click();
	   } catch(Exception e){
	    	// report error
			ErrorUtil.addVerificationFailure(e);
			Log.debug("Cannot find the object with Key---" + xpathKey);
			}
   }
   
   // Verification
   
   public boolean isElementPresent(String xpathKey){
	   try{
		   new WebDriverWait(driver,100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		   driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		  }catch(Exception e){
		    	// report error
				ErrorUtil.addVerificationFailure(e);
				Log.debug("Cannot find the object with Key---" + xpathKey);
				return false;
				}
	   return true;
   }
   
   public String getValue(String xpathKey){
	   try{
		   driver.findElement(By.xpath(OR.getProperty(xpathKey))).getAttribute("value");
		   }catch (Exception e){
			  // report error
			  e.printStackTrace();
	      }
	   return null;
   }

   // Wait
  public void delay(){
	 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
  }
  // Wait for the Element to Load
  
  public static void WaitForElementLoad(String xpathKey, int timeoutInSeconds){
	  if(timeoutInSeconds>0){
		  WebDriverWait wait = new WebDriverWait(driver,timeoutInSeconds);
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
	  }
	  
	  
  }
// Get text from the table
   public void getTableData(String xpathKey){
	   String requestOpenData=driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText();
	   String requestType=driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText();
	   String requestSubType=driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText();
	   String status=driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText();
	   
	   System.out.println(requestOpenData);
	   System.out.println(requestType);
	   System.out.println(requestSubType);
	   System.out.println(status);
	   
   }
   
      
   // Take screenshot
   public static void takeScreenshot(String fileName){
	   File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   try{
		   FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"screenshots\\"+fileName));
	   } catch(IOException e){
		   e.printStackTrace();
	   }
   }
   
   }
