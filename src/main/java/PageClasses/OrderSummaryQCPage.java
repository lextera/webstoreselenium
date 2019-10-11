package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class OrderSummaryQCPage extends PageBaseClass{
	public TopMenuClass topmenu;
	
	public OrderSummaryQCPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 
		
	}
	
	@FindBy(xpath = "//form[@id='payment']//label[@class='onoff_switch']") public WebElement creditCard_slider;
	@FindBy(id = "existing_card_mcl") public WebElement savedCard_dropdown; //diff in mce
	@FindBy(xpath = "//a[@class='submit_mcl_order' and contains(text(),'Process')]") public WebElement processPayment_button; //diff
	//a[@class='submit_mce_order' and contains(text(),'Process')]
	@FindBy(id = "sumbit_mcl_order_confirmed") public WebElement placeOrder_button; //sumbit_mce_order_confirmed
	
	
	@FindBy(id = "existing_card_mce") public WebElement mce_savedCard_dropdown;
	@FindBy(xpath = "//a[@class='submit_mce_order' and contains(text(),'Process')]") public WebElement mce_processPayment_button;
	@FindBy(id = "sumbit_mce_order_confirmed") public WebElement mce_placeOrder_button; //sumbit_mce_order_confirmed
	
	
	public TopMenuClass getTopMenu() {
		System.out.println("From topMenu " + driver.getCurrentUrl());
		return topmenu;
	}
	
 	public OrderConfirmationPage selectSavedCreditCard(String text) {

 		try {
			logger.log(Status.INFO, "Selecting Saved Card");
			scrollIntoViewJS(creditCard_slider);
			creditCard_slider.click();
			waitForPageLoad();
			selectFromDropdownByText(driver, savedCard_dropdown, text); 
			processPayment_button.click();
			waitForPageLoad();
			logger.log(Status.INFO, "Clicking Proceed button of Pop up window");
			placeOrder_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
 		
 		
 		
 		OrderConfirmationPage orderConfirmation = new OrderConfirmationPage(driver, logger);
 		PageFactory.initElements(driver, orderConfirmation);
 		return orderConfirmation;
 		
 	}
 	
 	public OrderConfirmationPage selectSavedCreditCardForMCE(String text) {

 		try {
			logger.log(Status.INFO, "Selecting Saved Card");
			scrollIntoViewJS(creditCard_slider);
			creditCard_slider.click();
			waitForPageLoad();
			selectFromDropdownByText(driver, mce_savedCard_dropdown, text); 
			mce_processPayment_button.click();
			waitForPageLoad();
			logger.log(Status.INFO, "Clicking Proceed button of Pop up window");
			mce_placeOrder_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
 		
 		OrderConfirmationPage orderConfirmation = new OrderConfirmationPage(driver, logger);
 		PageFactory.initElements(driver, orderConfirmation);
 		return orderConfirmation;
 		
 	}
	
}//
