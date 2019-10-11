package PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;

public class QuotePage extends PageBaseClass{

	public QuotePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//span[@class='load_saved slider_mobile']")public WebElement savedAddr_slider; 
	@FindBy(id = "iAcceptCheck")public WebElement accept_checkbox;
	@FindBy(xpath = "//a[text()='Produce Quote']")public WebElement produceQuote_button; 
	@FindBy(xpath = "//span[@class='inter_ship_title confirm_load' and contains(text(),'Confirm')]") public WebElement confirmAddress_button;
	
	
	
	
	public QuoteorderConfirmationPage clickProduceAQuote() {
		try {
			logger.log(Status.INFO, "Clicking I Accept checkbox and Produce A Quote");
			waitForPageLoad();
			accept_checkbox.click();
			produceQuote_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		QuoteorderConfirmationPage quoteconfirm = new QuoteorderConfirmationPage(driver, logger);
		PageFactory.initElements(driver, quoteconfirm);
		return quoteconfirm;
		
	}
	
	public void loadSavedAddress() {
		try {
			logger.log(Status.INFO, "Clicking Load Saved Shipping Address");
			scrollIntoViewJS(savedAddr_slider);
			savedAddr_slider.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void selectAddress(String state) {
		logger.log(Status.INFO, "Selecting from Saved address");
		try {
			waitForPageLoad();
			driver.findElement(By.xpath("//label[contains(text(),'"+state+"')]//parent::span//parent::div/span//input[@type='checkbox']")).click();
			confirmAddress_button.click();
		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}
		
		logger.log(Status.PASS, "State " +state+ " is selected");
	}
	
	
	
	
}//
