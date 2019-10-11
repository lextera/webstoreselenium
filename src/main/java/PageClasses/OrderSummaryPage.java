package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class OrderSummaryPage extends PageBaseClass {

	public TopMenuClass topmenu;
	
	public OrderSummaryPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 
		
	}
		
 	@FindBy(xpath = "//form[@id='payment']//label[@class='onoff_switch']") public WebElement creditCard_slider;
 	@FindBy(id = "existing_card_mcl") public WebElement savedCard_dropdown;
 	@FindBy(xpath = "//a[@class='submit_mcl_order' and contains(text(),'Process')]") public WebElement processPayment_button;
 	@FindBy(id = "sumbit_mcl_order_confirmed") public WebElement proceed_button;
	@FindBy(xpath = "//a[@class='submit_mcl_order' and contains(text(),'Continue')]") public WebElement continuePayment_button;


 	
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
			proceed_button.click();
		} catch (Exception e) {
	 		reportFail(e.getMessage());
		}

 		
 		OrderConfirmationPage orderConfirmation = new OrderConfirmationPage(driver, logger);
 		PageFactory.initElements(driver, orderConfirmation);
 		return orderConfirmation;
 		
 	}
 	
 	public void useNewCard() {
 		
 	}
 	
	public PayTracePage guestPayment() {
		logger.log(Status.INFO, "Clicking Continue Payment");
 		continuePayment_button.click();
 		waitForPageLoad();
		logger.log(Status.INFO, "Clicking Proceed button of Pop up window");
		proceed_button.click();
 		
		//paytrace page to order confirmation page
		PayTracePage paytrace = new PayTracePage(driver, logger);
 		PageFactory.initElements(driver, paytrace);
 		return paytrace;
 	}
 	
 	
	public TopMenuClass getTopMenu() {
		//System.out.println("From topMenu " + driver.getCurrentUrl());
		return topmenu;
	}

	
}//
