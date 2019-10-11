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

public class Verify_GuestUser_Order_US_Test extends BaseTestClass {

	LandingPage landingPage;
	TopMenuClass topmenu;
	DashBoardPage dashboard;
	CartPage cart;
	CheckoutPage checkout;
	LoginPage login;
	OrderSummaryPage ordersummary;
	PayTracePage paytrace;
	OrderConfirmationPage orderconfirm;
	
	
	@Test
	public void order_US_guest_user() {
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
		checkout = login.loginAsGuest("mcl@test.com");
		checkout.enterGuestDetails("MiniCircuits", "AutomatedTest", "13 neptune ave.", "brooklyn", "NY", "11235", "718-934-4500", "7");
		checkout.clickBillingInfoSame();
	
		ordersummary = checkout.reviewOrder();
		paytrace = ordersummary.guestPayment();
		orderconfirm = paytrace.enterCardDetails(); 
		System.out.println("Order number generated is : " +orderconfirm.getOrderNumber());
		
	}

}//
