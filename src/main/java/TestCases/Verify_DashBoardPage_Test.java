package TestCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageClasses.DashBoardPage;
import PageClasses.HomePage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;
import utilities.TestDataProvider;

public class Verify_DashBoardPage_Test extends BaseTestClass{
	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	

	
	@Test(dataProvider = "getWebStoreData")
	public void exception_minQuantity_popUp(Hashtable<String, String> testData) {
		
		logger = report.createTest("Verify error message minimum quantity for + models ");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		dashboard.enterInvalidQty(testData.get("qty"));
	}
		
	@Test(dataProvider = "getWebStoreData")
	public void reel_options_popUp(Hashtable<String, String> testData) {
		
		logger = report.createTest("Verify reel options for custom quantity");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		dashboard.enterCustomReelQty(testData.get("customQty"));
		
	}
	
	@DataProvider
	public Object [][] getWebStoreData(){
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "DashBoardPage_Test");
	}
}
