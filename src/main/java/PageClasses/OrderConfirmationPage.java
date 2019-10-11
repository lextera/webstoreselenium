package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class OrderConfirmationPage extends PageBaseClass{
	
	public TopMenuClass topmenu;

	public OrderConfirmationPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu);
	}
	
	@FindBy(xpath = "//div[@class='summary_title order_confirm_text']/p[2]/span") public WebElement orderNumber_string;
	@FindBy(xpath = "//a[contains(@href,'dashboard.html')]") public WebElement modelNumber_string;
	@FindBy(xpath = "//div[@class='qty_input_selector']/span/input") public WebElement orderQuantity_string;
	
	
	public TopMenuClass getTopMenu() {
		System.out.println("From topMenu " + driver.getCurrentUrl());
		return topmenu;
	}

	public boolean isModelCorrect(String model1){
		
		System.out.println("Actual Model is : " + modelNumber_string.getText().trim());
		logger.log(Status.PASS, "Item Model verified");
		return modelNumber_string.getText().trim().equals(model1);
	}
	
	public boolean isQTYEquals(String qty){
		
		
		System.out.println("Actual Quantity is : " +  orderQuantity_string.getText().trim());
		logger.log(Status.PASS, "Quantity Verified ");
		return  orderQuantity_string.getText().trim().equals(qty);
		
		 
	}
	
	public String getOrderNumber() {
		logger.log(Status.PASS, "Order number generated");
		return orderNumber_string.getText();
		
	}

}
