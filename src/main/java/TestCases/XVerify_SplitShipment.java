package TestCases;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import PageClasses.CartPage;
import PageClasses.CheckoutPage;
import PageClasses.DashBoardPage;
import PageClasses.HomePage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class XVerify_SplitShipment extends BaseTestClass{
	LandingPage landingPage;
	TopMenuClass topmenu;
	LoginPage login;
	HomePage homepage;
	DashBoardPage dashboard;
	CartPage cart;
	CheckoutPage checkout;
	
	String model = "ADT16-1T";
	String qty = "8";
	
	String splitModel = "CALSEN-4GHS";
	String splitQty = "20";
	
	
	@Test
	public void splitShipment_not_available_in_alaska() {
		logger = report.createTest("Verify US Quote and converting Quote to Order");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		topmenu = landingPage.getTopMenu();
		login = topmenu.clickSignIn();
		homepage = login.enterCredentials("lextera", "password1");
		dashboard = topmenu.enterModel(model);
		cart = dashboard.addToCart(qty);
		cart.addItemQuickBuy(splitModel, splitQty);
		checkout = cart.clickCheckout();
		System.out.println("US ADDRESS  ----" +checkout.isSplitShipmentAvailable());
		checkout.loadSavedAddress(); 
		checkout.selectAddress("AK");

		System.out.println("alaska ADDRESS  ----" +checkout.isSplitShipmentAvailable());

			
		
		
		
	}
	
}//
