package TestCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageClasses.CartPage;
import PageClasses.CheckoutQCPage;
import PageClasses.DashBoardPage;
import PageClasses.HomePage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.OrderConfirmationPage;
import PageClasses.OrderHistoryPage;
import PageClasses.OrderSummaryQCPage;
import PageClasses.QuotePage;
import PageClasses.QuoteorderConfirmationPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;
import utilities.TestDataProvider;

public class Verify_US_Quote_Convert_to_Order_Test extends BaseTestClass{

	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	CartPage cart;
	QuotePage quote;
	QuoteorderConfirmationPage quoteconfirm;
	CheckoutQCPage checkoutqc;
	OrderSummaryQCPage ordersummaryqc;
	OrderConfirmationPage orderconfirm;
	OrderHistoryPage orderhistory;

	String orderIDGenerated = "";
	
	@Test (dataProvider = "getWebStoreData")
	public void verify_convertToQuote_US(Hashtable<String, String> testData) {
		logger = report.createTest("Verify US Quote and converting Quote to Order");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));

		cart = dashboard.addToCart(testData.get("qty"));
		quote = cart.clickConvertToQuote();

		quoteconfirm = quote.clickProduceAQuote();
		checkoutqc = quoteconfirm.clickConvertToOrder();
		checkoutqc.selectShipOptionsAndContinue("7");
		checkoutqc.clickBillingInfoSame();
		ordersummaryqc = checkoutqc.reviewOrder();
		orderconfirm = ordersummaryqc.selectSavedCreditCard(testData.get("selectCreditCard"));
		orderIDGenerated = orderconfirm.getOrderNumber();
		System.out.println("***Order ID Generated is  " + orderIDGenerated);
		topmenu = orderconfirm.getTopMenu();
		orderhistory = topmenu.clickOrderHistory();
		
		orderhistory.isInOrderHistory(orderIDGenerated);
		
	}
	
	
	@DataProvider
	public Object[][] getWebStoreData() {
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "US_Quote_Convert_to_Order_Test");
	}

	 
}//

