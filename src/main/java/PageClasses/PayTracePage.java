package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.PageBaseClass;


public class PayTracePage extends PageBaseClass{

	public PayTracePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	
	}
	
	@FindBy(xpath= "//input[@name='CC']") public WebElement ccNum;
	@FindBy(id= "EXPMNTH") public WebElement exMonth;
	@FindBy(id= "EXPYR") public WebElement exYear;
	@FindBy(xpath= "//input[@name='CSC']") public WebElement csc;
	@FindBy(xpath= "//input[@value = 'Process Transaction' and @name = 'cmdBILL']") public WebElement processTxn;

	
	public OrderConfirmationPage enterCardDetails(){
		//waitForPageLoad();
		
		try {
			ccNum.sendKeys(prop.getProperty("ccnum"));		
			selectFromDropdownByValue(driver, exMonth, prop.getProperty("exmonth"));
			selectFromDropdownByValue(driver, exYear, prop.getProperty("exyear"));
			
			csc.sendKeys("945");
			processTxn.click();
			checkAlert_Accept();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		OrderConfirmationPage orderconfirm = new OrderConfirmationPage(driver, logger);
 		PageFactory.initElements(driver, orderconfirm);
 		return orderconfirm;
		
 		
		
	}
}
