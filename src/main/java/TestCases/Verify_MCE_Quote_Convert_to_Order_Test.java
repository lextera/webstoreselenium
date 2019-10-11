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

public class Verify_MCE_Quote_Convert_to_Order_Test extends BaseTestClass {
	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	CartPage cart;
	QuotePage quote;
	QuoteorderConfirmationPage quoteConfirm;
	CheckoutQCPage checkoutqc;
	OrderSummaryQCPage ordersummaryqc;
	OrderConfirmationPage orderconfirm;
	OrderHistoryPage orderhistory;
	
	String orderIDGenerated = "";
	
	//uk to uk pound sterling
	@Test(dataProvider = "getWebStoreData")
	public void verify_quote_MCE(Hashtable<String, String> testData) {
		logger = report.createTest("Verify MCE Quote and converting Quote to Order");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
		dashboard = topmenu.enterModel(testData.get("model"));
		cart = dashboard.addToCart(testData.get("qty"));//
		//cart.addItemQuickBuy("ADE-12", "12");
		//cart.clickShipOutsideUS("United Kingdom");
		cart.clickShipOutsideUS(testData.get("outsideUS"));
		// ship outside us select UK
		quote = cart.clickConvertToQuote();
		quote.loadSavedAddress();
		//quote.selectAddress("Surrey"); //
		quote.selectAddress(testData.get("selectAddress")); //
		quoteConfirm = quote.clickProduceAQuote();
	
		quoteConfirm.iSQuoteDetailsCorrect(testData.get("model"), testData.get("qty"));
		// click convert to quote
		checkoutqc = quoteConfirm.clickConvertToOrder();
		waitForPageLoad(); //
		//checkoutqc.selectShipOptionsAndContinue(testData.get("shipOptions")); //xx
		checkoutqc.selectShipOptionsAndContinue("501"); //xx
		checkoutqc.clickBillingInfoSame();
		checkoutqc.fillUpExportUserForm();

		ordersummaryqc = checkoutqc.reviewOrder();
		orderconfirm = ordersummaryqc.selectSavedCreditCardForMCE(testData.get("selectCreditCard"));
		orderIDGenerated = orderconfirm.getOrderNumber();
		
		System.out.println("***Order ID Generated is  " + orderIDGenerated);
		topmenu = orderconfirm.getTopMenu();
		orderhistory = topmenu.clickOrderHistory();

		orderhistory.isInOrderHistory(orderIDGenerated);
		
	}
	
	@DataProvider
	public Object [][] getWebStoreData(){
		return TestDataProvider.getTestData("webstore_testdata.xlsx", "login", "verify_quote_MCE");
	}
}//
