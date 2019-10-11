package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class LoginPage extends PageBaseClass{
	
	public TopMenuClass topmenu;
	//public ExtentTest logger;
	
	public LoginPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 
	}
	

 	@FindBy(id = "login_name")
	public WebElement login_text;
 	
 	@FindBy(id = "account_password")
	public WebElement password_text;

	@FindBy(id = "login")
	public WebElement signIn_button;
	
	@FindBy(id = "account_email")
	public WebElement guest_email_text;
	
	@FindBy(id = "longin_as_guest_button")
	public WebElement continue_guest_button;
	
	
	@FindBy(xpath = "//span[@class='errorMessage']")
	public WebElement errorMessage1;
	
	@FindBy(xpath = "//span[@class='errorMessage']")
	public WebElement errorMessage2;
	
	//The email address you entered is not valid. 

	public HomePage enterCredentials(String username, String password) {
	
		try {
			logger.log(Status.INFO, "Entering user credentials");
			login_text.sendKeys(username);
			password_text.sendKeys(password);
			signIn_button.click();
			logger.log(Status.PASS, "Successfully login");

		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}


		HomePage homepage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homepage);
		return homepage;
		 
	}
	
	public CheckoutPage loginAsGuest(String email) {
		
		try {
			logger.log(Status.INFO, "Entering Guest user credentials");
			guest_email_text.sendKeys(email);
			continue_guest_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		CheckoutPage checkout = new CheckoutPage(driver, logger);
		PageFactory.initElements(driver, checkout);
		return checkout;
	}
	
	public void verifyErrorMessage(String error) {
		try {
			String err1 = errorMessage1.getText();
			System.out.println(err1);
			
			Assert.assertTrue(err1.equals(error));
		} catch (Exception e) {
	
			reportFail(e.getMessage());
		}

	}
	

}//
