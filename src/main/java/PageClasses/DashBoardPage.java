package PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class DashBoardPage extends PageBaseClass {
	
	public TopMenuClass topmenu;
	
	
	public DashBoardPage(WebDriver driver , ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 
	}
	
	
 	@FindBy(id = "qty_number")
	public WebElement quantity_textbox;
 	
 	@FindBy(id = "add_to_cart")
	public WebElement addToCart_button;
 	
 	@FindBy(id = "add_to_cart2")
	public WebElement addToCartTestBoard_button;
 	
 	@FindBy(id = "qty_list")
	public WebElement quantityTestBoard_textbox;
 	
 	@FindBy(id = "order_ez_sample_model")
	public WebElement ezsample_button;
 	
 	
 	public CartPage addToCart(String quantity) {
 		try {
 	 		logger.log(Status.INFO, "adding items to cart");
 	 		//waitForPageLoad();
 	 		quantity_textbox.sendKeys(quantity);
 	 		waitForPageLoad();
 	 		addToCart_button.click();
 	 		logger.log(Status.INFO, "Added items to cart");
 		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}

 		
 		CartPage cart = new CartPage(driver, logger);
 		PageFactory.initElements(driver, cart);
 		return cart;
 		
 	}
 	
 	public void enterInvalidQty(String qty) {
 
 		try {
 		logger.log(Status.INFO, "Entering invalid quantity of 1");
 		quantity_textbox.sendKeys(qty);
 		addToCart_button.click();
 		waitForPageLoad();
 	
 	 		WebElement popUp = driver.findElement(By.xpath("//div[@class='modal-content']//p"));
 	 		Assert.assertTrue(popUp.isDisplayed()); 		
 	 		logger.log(Status.PASS, "Error message is displayed : " +popUp.getText());
 	 		logger.log(Status.INFO, "Clicking 'OK' pop up botton");
 	 		driver.findElement(By.xpath("//span[@class='modal_bottom']//span[@id='ok']")).click();
 		} catch(Exception e) {
 			reportFail(e.getMessage());
 		}

 	}
 	
 	public void addTestBoard(String TBquantity) {
 		logger.log(Status.INFO, "adding Test Board to cart");
 		try {
 	 		quantityTestBoard_textbox.sendKeys(TBquantity);
 	 		waitForPageLoad();
 	 		logger.log(Status.PASS, "Added Test Board items to cart");
 		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}
 		
 	}
 	
 	public void enterCustomReelQty(String customQty) {

 		try {
 		logger.log(Status.INFO, "Entering custom quantity of 21");
 		quantity_textbox.sendKeys(customQty);
 		addToCart_button.click();
 		
 	 		WebElement popUp = driver.findElement(By.xpath("//div[@id='myModal']//div[@class='modal-content']"));
 	 		Assert.assertTrue(popUp.isDisplayed());
 	 		logger.log(Status.PASS, "Reel pop up options displayed");
 	 		logger.log(Status.INFO, "Clicking Cancel button");
 	 		driver.findElement(By.xpath("//div[@id='myModal']//div[@class='modal-content']//span[@id='cancel']")).click();
 	 		
 		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}

 	}
 	
  	

}//
