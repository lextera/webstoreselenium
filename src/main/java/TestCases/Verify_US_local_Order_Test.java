package TestCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageClasses.CartPage;
import PageClasses.CheckoutPage;
import PageClasses.DashBoardPage;
import PageClasses.HomePage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.LogoutPage;
import PageClasses.OrderConfirmationPage;
import PageClasses.OrderHistoryPage;
import PageClasses.OrderSummaryPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;
import utilities.TestDataProvider;

public class Verify_US_local_Order_Test extends BaseTestClass {

	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	CartPage cart;
	CheckoutPage checkout;
	OrderSummaryPage ordersummary;
	LogoutPage logout;
	OrderConfirmationPage orderconfirm;
	OrderHistoryPage orderhistory;
	
	String orderIDGenerated = "";

	@Test(dataProvider = "getWebStoreData")
	public void order_US(Hashtable<String, String> testData) {

		logger = report.createTest("Verify US order using existing credit card");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		landingPage.getTitle(testData.get("pageTitle"));
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn(); 
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model")); //error here
		cart = new CartPage(driver, logger);	
		cart = dashboard.addToCart(testData.get("qty"));
		checkout = cart.clickCheckout();
		checkout.loadSavedAddress(); 
		checkout.selectAddress(testData.get("countryCode"));
		checkout.selectShipOptionsAndContinue("7");
		checkout.clickBillingInfoSame();
		ordersummary = checkout.reviewOrder();
		orderconfirm = ordersummary.selectSavedCreditCard(testData.get("selectCreditCard"));
		orderIDGenerated = orderconfirm.getOrderNumber();
		topmenu = orderconfirm.getTopMenu();
		orderhistory = topmenu.clickOrderHistory();
	
		orderhistory.isInOrderHistory(orderIDGenerated);

	}
	
	@DataProvider
	public Object [][] getWebStoreData(){
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "US_local_Order_Test");
	}

}//
