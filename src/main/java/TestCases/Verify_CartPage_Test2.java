package TestCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import PageClasses.CartPage;
import PageClasses.DashBoardPage;
import PageClasses.HomePage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;
import utilities.TestDataProvider;

public class Verify_CartPage_Test2 extends BaseTestClass {
	
	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	CartPage cart;


	@Test (dataProvider = "getWebStoreData", priority = 1)
	public void verify_quickBuy_added_correctly(Hashtable<String, String> testData) {
		logger = report.createTest("Verify Quick Buy added correctly"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		waitForPageLoad();
		//cart.addItemQuickBuy("BP4P", "2"); 
		cart.addItemQuickBuy(testData.get("quickBuyItem"), testData.get("quickBuyItemQty")); 
		cart.isItemInCart("BP4P", "2");
		
	}
	
	
	@Test (dataProvider = "getWebStoreData", enabled = false)
	public void verify_quickBuy_autosuggest_list(Hashtable<String, String> testData) {
		logger = report.createTest("Verify Quick Buy Auto-Suggest list"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		waitForPageLoad();
		cart.selectFromSuggestionList("GALI", "GALI-33+");
		
		//throw new SkipException("Investigating the flakiness/inconsistentness of this report");
	}
	
	

	@Test (dataProvider = "getWebStoreData")
	public void verify_ship_outside_US(Hashtable<String, String> testData) {
		logger = report.createTest("Verify Shipping outside US"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		cart.clickShipOutsideUS("Aruba");
	}
	
	@Test (dataProvider = "getWebStoreData", priority = 2)
	public void verify_removeItems_from_cart(Hashtable<String, String> testData) {
		
		logger = report.createTest("Verify remove items from Cart"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		cart.removeItem(testData.get("model"));
		cart.isemptyCartMessageDisplayed();
		//this is too long
		topmenu = cart.getTopMenu();
		topmenu.clickSignOut();
	}
	@Test (dataProvider = "getWebStoreData", priority = 3)
	public void verify_quickBuy_exception_message(Hashtable<String, String> testData) {
		logger = report.createTest("Verify Quick Buy exception message "); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		cart.removeItem(testData.get("model"));
		
		cart.clickQuickBuyAddToCart();
		cart.isErrorMessageDisplayed();
		
		
		
	}
	@Test (dataProvider = "getWebStoreData", priority = 4)
	public void verify_reel_options_popUp(Hashtable<String, String> testData) {
		logger = report.createTest("Verify reel option pop up and selection"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		cart.addItemQuickBuy("ADE-11X+", "21");
		cart.isReelOptionsDisplayed();
		cart.selectFromReelOptions("1");
	}

	// new tab of order warranty page
	@Test(dataProvider = "getWebStoreData")
	public void verify_orderInfoInNewTab(Hashtable<String, String> testData) {
		logger = report.createTest("Verify reel option pop up and selection"); 
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));
		cart.clickOrderWarrantyInfoLink();
		
	}
	
	@DataProvider
	public Object [][] getWebStoreData(){
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "CartPage_Test");
	}
}//**
