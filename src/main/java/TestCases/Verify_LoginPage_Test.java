package TestCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import PageClasses.CartPage;
import PageClasses.CheckoutPage;
import PageClasses.DashBoardPage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.OrderConfirmationPage;
import PageClasses.OrderSummaryPage;
import PageClasses.PayTracePage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class Verify_LoginPage_Test  extends BaseTestClass{
	LandingPage landingPage;
	TopMenuClass topmenu;
	DashBoardPage dashboard;
	CartPage cart;
	CheckoutPage checkout;
	LoginPage login;

	
	
	@Test
	public void exceptions_guest_user_existingEmail() {
		logger = report.createTest("Verify US order for Guest User");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		
		topmenu = landingPage.getTopMenu();
		dashboard = topmenu.enterModel("GAT-4+"); 
		cart = new CartPage(driver, logger);	
		cart = dashboard.addToCart("20");
		login = cart.clickCheckoutGuest();
		login.loginAsGuest("lextera@minicircuits.com");
		login.verifyErrorMessage("This email has been registered by an existing user. Please use a different email address.");
		

	}
	
	@Test
	public void exception_guest_user_invalidEmail() { 
		logger = report.createTest("Verify US order for Guest User");
		invokeBrowser("chrome");
		PageBaseClass pageBase = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		landingPage = pageBase.openApplication();
		
		topmenu = landingPage.getTopMenu();
		dashboard = topmenu.enterModel("GAT-4+"); 
		cart = new CartPage(driver, logger);	
		cart = dashboard.addToCart("20");
		login = cart.clickCheckoutGuest();
		login.loginAsGuest("invalid");
		login.verifyErrorMessage("The email address you entered is not valid.");

	}
}
