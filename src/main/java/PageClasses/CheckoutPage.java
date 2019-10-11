package PageClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class CheckoutPage extends PageBaseClass {

	public TopMenuClass topmenu;
	String instruction_notes = "This is an automated test transaction from Software Team \n Pls disregard";

	public CheckoutPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu);
	}

	@FindBy(id = "shipping_method") public WebElement shipOptions_dropdown;
	@FindBy(id = "title_bill") public WebElement ponumber_textbox;
	@FindBy(id = "special_instructions_ship") public WebElement specinstructions_textbox;
	@FindBy(xpath = "//span[@class='continue_to_next checkout_first' and @id= 'submit_shipping']") public WebElement continue1_button;
	@FindBy(xpath = "//div[@id='select_address_section']//span[@class='slider round']") public WebElement savedaddress_slider;
	@FindBy(xpath = "//span[@class='inter_ship_title confirm_load']") public WebElement confirmAddress_button;
	@FindBy(xpath = "//div[@class='checkout_item']//span[@class='int_shipp slider_mobile']") public WebElement shipOutside_slider;
	@FindBy(xpath = "//section[@class='freight_collect']//section[@class='slider_mobile']") public WebElement freightCollect_slider;
	@FindBy(xpath = "//form[@id='billing_info']//span[@class='slider round']") public WebElement billSameInfo_slider;
	@FindBy(xpath = "//span[@id='submit_billing' and text()='Continue']") public WebElement continue2_button;
	@FindBy(id = "iAcceptCheck") public WebElement iAccept_checkbox;
	@FindBy(id = "final_continuing") public WebElement reviewOrder_button;

	@FindBy(xpath = "//div[@class='splitship_header']") public WebElement splitshipment_block;
	

	@FindBy(id = "email_address_bill") public WebElement billing_email_text;
	
	//for guest
	@FindBy(id = "first_name_ship") public WebElement guest_fname;
	@FindBy(id = "last_name_ship") public WebElement guest_lname;
	@FindBy(id = "street_ad_ship") public WebElement guest_streetaddr;
	@FindBy(id = "city_bill") public WebElement guest_city;
	@FindBy(id = "zip_ship") public WebElement guest_zip;
	@FindBy(id = "phone_ship") public WebElement guest_phone;
	@FindBy(id = "state_ship") public WebElement guest_state;
	@FindBy(id = "shipping_method") public WebElement guest_ship;
	


	
	public void loadSavedAddress() {
		try {
			savedaddress_slider.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void selectAddress(String state) {
		try {
			waitForPageLoad();
			driver.findElement(By.xpath("//label[contains(text(),'"+state+"')]//parent::span//preceding-sibling::span//input[@type='checkbox']")).click();
			confirmAddress_button.click();
		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}
		
	
		
	}

	public void selectShipOptionsAndContinue(String value) {
		try {
			selectFromDropdownByValue(driver, shipOptions_dropdown, value);
			specinstructions_textbox.sendKeys(instruction_notes);
			continue1_button.click();
		}catch(Exception e) {
 			reportFail(e.getMessage());
 		}

		
	}
	
	/****************billing info*************************/
	public void clickBillingInfoSame() {
		try {
			logger.log(Status.INFO, "Clicking BillingSameInfo button slider");		
			waitTillElementFound(billSameInfo_slider);

			try {
				Thread.sleep(1000);
				billSameInfo_slider.click();
			} catch (InterruptedException e) {
				retryingFindClick("//form[@id='billing_info']//span[@class='slider round']");
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			waitForElementToClick(continue2_button);
			logger.log(Status.INFO, "Clicking 2nd Continue ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*******add condition here 09/23 ************/
			if(billing_email_text.getAttribute("value").isEmpty()) {
				billing_email_text.sendKeys("lex_acosta@yahoo.com");
			}
			continue2_button.click(); //x
		} catch(Exception e) {
 			reportFail(e.getMessage());
 		}
	}
	
	public OrderSummaryPage reviewOrder() {
		try {
			logger.log(Status.INFO, "Accepting Terms and Condition");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitTillElementFound(iAccept_checkbox); //x
			iAccept_checkbox.click();
			
			waitTillElementFound(reviewOrder_button);
			logger.log(Status.INFO, "Clicking Review Order");
			reviewOrder_button.click();
		} catch(Exception e) {
 			reportFail(e.getMessage());
 		}
		
		OrderSummaryPage ordersummary = new OrderSummaryPage(driver, logger);
		PageFactory.initElements(driver, ordersummary);
		return ordersummary;
	}
	public boolean isSplitShipmentAvailable() {
		
		try {
			Thread.sleep(1000);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isElementPresentById(splitshipment_block);
	}
	
	/****************guest user*************************/
	public void enterGuestDetails(String first, String last, String address, String city, String state, String zip, String phone, String shipMethod ) {
		try {
			logger.log(Status.INFO, "Entering Guest user info");		
			guest_fname.sendKeys(first);
			guest_lname.sendKeys(last);
			guest_streetaddr.sendKeys(address);
			guest_city.sendKeys(city);
			//dropdown
			waitForPageLoad();
			selectFromDropdownByValue(driver, guest_state, state);
			guest_zip.sendKeys(zip);
			guest_phone.sendKeys(phone);
			waitForPageLoad();
			selectFromDropdownByValue(driver, guest_ship, shipMethod);
			specinstructions_textbox.sendKeys(instruction_notes);
			continue1_button.click();
			
			
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
	}
	
	
	
	
}//
