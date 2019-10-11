package baseClasses;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import PageClasses.LandingPage;
import utilities.DateUtil;
import utilities.ExtentReportManager;
import utilities.TestDataProvider;

public class PageBaseClass extends BaseTestClass {

	public WebDriver driver;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	
	public PageBaseClass(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	} 
	


	/**********************Open application**************************************/
	
	public LandingPage openApplication() {
		logger.log(Status.INFO, "Opening the webstore");
		driver.get(prop.getProperty("url"));
		//driver.get("http://192.168.3.91:9092/");
		
		logger.log(Status.INFO, "Adding Cookies as workaround for \"permission denied\" ");
		
		Set <Cookie> cookies = driver.manage().getCookies();			
		for(Cookie c : cookies) {
			

			  
			if(c!=null) {
				if(c.getDomain().contains("minicircuits")) {
					driver.manage().addCookie(new Cookie(c.getName(),c.getValue(), c.getDomain(), c.getPath(), c.getExpiry()));
					
				}
			}
			
		}
		
		driver.manage().addCookie(new Cookie("sidtr","E20151DC486F474DB042C3FB0A6D50ED"));
		
		logger.log(Status.PASS, "Cookies added successfully");
		logger.log(Status.INFO, "Open app from PageBase class " +driver.getCurrentUrl());
		logger.log(Status.PASS, "Successfully Opened newdev");
		
		LandingPage landingpage = new LandingPage(driver, logger);
		PageFactory.initElements(driver, landingpage);
		return landingpage;
	}
	

	
	/************************get page title*********************************/
	public void getTitle(String expectedTitle) {
		
		try {
			Assert.assertEquals(driver.getTitle(),expectedTitle);
			reportPass("Actual Title : " + driver.getTitle() + "equal to " + expectedTitle);
			
		}catch(Exception e){
			reportFail(e.getMessage());
		}
		
	}
	
	/****************** Reporting Functions ***********************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}
	
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}
	
	/****************** Capture Screen Shot ***********************/
	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "/ScreenShots/" + DateUtil.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/ScreenShots/" + DateUtil.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@DataProvider
	public Object [][] getWebStoreData(){
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "open webstore");
	}

	
	
}//
