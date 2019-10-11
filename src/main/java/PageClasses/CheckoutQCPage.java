package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;

public class CheckoutQCPage extends PageBaseClass{
	
	String instruction_notes = "This is an quote automated test transaction from Software Team \n Pls disregard";
	String ponumber = "Tes PO# 123-456";
	public CheckoutQCPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "shipping_method")public WebElement shipOptions_dropdown;
	@FindBy(id = "title_bill") public WebElement ponumber_textbox;
	@FindBy(id = "phone_ship")public WebElement deliveryphone_textbox;
	@FindBy(id = "special_instructions_ship") public WebElement specinstructions_textbox;
	@FindBy(xpath = "//section[@class='freight_collect']//section[@class='slider_mobile']") public WebElement freightCollect_slider;
	@FindBy(xpath = "//span[@class='continue_to_next checkout_first' and @id= 'submit_shipping']") public WebElement continue1_button;
	@FindBy(xpath = "//form[@id='billing_info']//span[@class='slider round']") public WebElement billSameInfo_slider;
	@FindBy(xpath = "//span[@id='submit_billing' and text()='Continue']") public WebElement continue2_button;
	@FindBy(id = "iAcceptCheck") public WebElement iAccept_checkbox;
	@FindBy(id = "final_continuing") public WebElement reviewOrder_button;
	
	/******************export end user form**********************/
	@FindBy(xpath = "//input[@class='order_input export_customer_name inputText']") public WebElement exportCustomer_textbox;
	@FindBy(xpath = "//input[@class='order_input export_end_user_name inputText']") public WebElement exportEndUser_textbox;
	@FindBy(xpath = "//input[@class='order_input export_address inputText']") public WebElement exportAddress_textbox;
	@FindBy(xpath = "//input[@class='order_input export_city inputText']") public WebElement exportCity_textbox;
	@FindBy(id = "mcl_country_end_user") public WebElement exportCountry_dropdown;
	
	@FindBy(xpath = "//input[@class='export_sign inputText']") public WebElement signature_textbox;
	@FindBy(xpath = "//span[contains(text(),'A:')]//following-sibling::span//label//span") public WebElement declarationA_radioButton;
	@FindBy(id = "iAcceptCheck_end") public WebElement iAgree_checkbox;
	@FindBy(id = "submit_end_user") public WebElement continueEndUser_button;
	
	
	public void selectShipOptionsAndContinue(String value) {
		try {
			deliveryphone_textbox.clear(); // just added
			deliveryphone_textbox.sendKeys("TEST-PHONE#");
			 waitForPageLoad();
			selectFromDropdownByValue(driver, shipOptions_dropdown, value);
			specinstructions_textbox.sendKeys(instruction_notes);
			continue1_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
	}
	
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
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			waitForElementToClick(continue2_button);
			logger.log(Status.INFO, "Clicking 2nd Continue ");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.log(Status.INFO, "Entering PO Number");
			ponumber_textbox.sendKeys(ponumber);
			logger.log(Status.INFO, "Clicking Continue");
			continue2_button.click(); //x
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public OrderSummaryQCPage reviewOrder() {
		//waitForPageLoad();
		try {
			logger.log(Status.INFO, "Accepting Terms and Condition");
			scrollIntoViewJS(iAccept_checkbox);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//waitTillElementFound(iAccept_checkbox); //x
			iAccept_checkbox.click();
			
			waitTillElementFound(reviewOrder_button);
			logger.log(Status.INFO, "Clicking Review Order");
			reviewOrder_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		OrderSummaryQCPage ordersummaryqc = new OrderSummaryQCPage(driver, logger);
		PageFactory.initElements(driver, ordersummaryqc);
		return ordersummaryqc;
		
	}
	
	public void fillUpExportUserForm() {
		logger.log(Status.INFO, "Filling up Export End user form");
		exportCustomer_textbox.sendKeys("Test customer");
		exportEndUser_textbox.sendKeys("Test End User name");
		exportAddress_textbox.sendKeys("Test Address");
		exportCity_textbox.sendKeys("Test City");
		//waitForPageLoad();
		waitTillElementFound(exportCountry_dropdown);
		selectFromDropdownByValue(driver, exportCountry_dropdown, "GB");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//waitForPageLoad();
		scrollIntoViewJS(declarationA_radioButton);
		//waitForElementToClick(declarationA_radioButton); //
		declarationA_radioButton.click();
		//waitTillElementFound(signature_textbox);
		//scrollIntoViewJS(signature_textbox);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scrollIntoViewJS(signature_textbox); //
		signature_textbox.sendKeys("Test Signature");
		//logger.log(Status.INFO, "Clicking I Agree button");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iAgree_checkbox.click();
		//waitForPageLoad();
		continueEndUser_button.click();
		
	}
	
	
	
}//
