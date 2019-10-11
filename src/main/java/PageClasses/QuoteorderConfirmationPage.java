package PageClasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;

public class QuoteorderConfirmationPage extends PageBaseClass{

	public QuoteorderConfirmationPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		
	}
	@FindBy(xpath = "//a[text()='Convert to Order']")public WebElement convertQuote_button; 
	@FindBy(xpath = "//a[text()='Return to Shopping ›']")public WebElement returnShopping_button; 
	@FindBy(xpath = "//div[@class='summary_title']/p[2]/span")public WebElement quoteNumber_text; 
	@FindBy(xpath = "//span[@class='cart_model']/a")public WebElement itemModel_text; 
	@FindBy(xpath = "//span[@class='qty_main']//input")public WebElement qtyValue_text; 
	@FindBy(id = "total_quantity")public WebElement subTotal_text; 
	@FindBy(id = "total_quantity")public WebElement currency_text; 	
	@FindBy(xpath = "//fieldset//div[2]/span[2]/a")public WebElement quoteItems; 
	
		
	public CheckoutQCPage clickConvertToOrder() {
		try {
			logger.log(Status.INFO, "Clicking Convert To Order button");
			convertQuote_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage() +"Subtotal is incorrect");
		}
		
		CheckoutQCPage checkoutqc = new CheckoutQCPage(driver, logger);
		PageFactory.initElements(driver, checkoutqc);
		return checkoutqc;
		
	}
	
	//
	public void iSQuoteDetailsCorrect(String item, String qty) {
		logger.log(Status.INFO, "verifying items and subtotal");
		try {
			List<String> items = new ArrayList<String>();
			System.out.println("Quote # generated is :  " + quoteNumber_text.getText());

			List <WebElement>  models = driver.findElements(By.xpath("//fieldset//div[2]/span[2]/a"));
			System.out.println("size is --->" +models.size());
			
			for(WebElement model : models) {
				System.out.println("<---Item Model is ------->" + model.getText());
				//adding to items list
				items.add(model.getText());
			}
			
			String actualSize = Integer.toString(models.size());
			System.out.println("printing the items from List"+items);

			Assert.assertTrue(subTotal_text.getText().equals(actualSize));
			reportPass(" Items and subtotal are correct, Quote Number generated is : " + quoteNumber_text.getText());
		} catch (AssertionError e) {
			reportFail(e.getMessage() +"Subtotal is incorrect");
		}
		

		
		
	}
	
	
	
	

}//
