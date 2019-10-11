package baseClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import PageClasses.DashBoardPage;
import PageClasses.LoginPage;
import PageClasses.LogoutPage;
import PageClasses.OrderHistoryPage;

public class TopMenuClass extends PageBaseClass {
	
	
	public TopMenuClass(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}
	
 	@FindBy(xpath = "//section[@class='account_sign_info']") public WebElement accountMenu_link;
	@FindBy(xpath = "//div[@id='login_block']//a[@href='/WebStore/login.html']") public WebElement signInSubMenu_link;
	@FindBy(xpath = "//div[@id='login_block']//a[@href='signout']") public WebElement signOffSubMenu_link;
	@FindBy(xpath = "//input[@name='model' and @placeholder='Model No. Search']") public WebElement modelsearch_textbox;
	//@FindBy(xpath = "//input[@name='model' and @value='Model No. Search']") public WebElement modelsearch_textbox;
	@FindBy(xpath = "//span[@class='search_icon']") public WebElement search_icon;
	@FindBy(xpath = "//a[contains(@href,'orderHistory')]") public WebElement orderHistory_submenu;
	
	
	public LoginPage clickSignIn() {
		logger.log(Status.INFO, "clicking signIn top menu");
		waitForPageLoad(); // !delete
		accountMenu_link.click();
		signInSubMenu_link.click();
		waitForPageLoad();
		logger.log(Status.PASS, "SignIn Clicked");
		
		LoginPage login = new LoginPage(driver, logger);
		PageFactory.initElements(driver, login);
		return login;
	}
	
	public LogoutPage clickSignOut() {
		logger.log(Status.INFO, "clicking signOut top menu");
		waitForPageLoad(); 
		accountMenu_link.click();
		signOffSubMenu_link.click();
		logger.log(Status.PASS, "SignOut clicked"); 
		
		LogoutPage logout = new LogoutPage(driver, logger);
		PageFactory.initElements(driver, logout);
		return logout;
	}
	
	public DashBoardPage enterModel(String model) {
		waitForPageLoad(); 
		logger.log(Status.INFO, "Entering item in Model textbox");
		//waitForPageLoad();
		modelsearch_textbox.sendKeys(model);
		search_icon.click();
		DashBoardPage dashboard = new DashBoardPage(driver, logger);
		PageFactory.initElements(driver, dashboard);
		return dashboard;
		
		
	}
	
	public OrderHistoryPage clickOrderHistory() {
		
		logger.log(Status.INFO, "Clicking Order History");
		waitForPageLoad();
		accountMenu_link.click();
		orderHistory_submenu.click();
		OrderHistoryPage orderhistory = new OrderHistoryPage(driver, logger);
		PageFactory.initElements(driver, orderhistory);
		return orderhistory;
	}
	
	

}//

/*
 * topmenu can include model search function and site search
 * 
 * 
 * 
 * 
 */