package PageClasses;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class CartPage extends PageBaseClass {

	public TopMenuClass topmenu;

	public CartPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu);

	}

	@FindBy(id = "checkout_button")
	public WebElement checkout_button;

	@FindBy(xpath = "//a[contains(text(),'Convert to Quote')]")
	public WebElement convertToQuote_button;

	@FindBy(xpath = "//div[@class='internation_shipping']//label[@class='onoff_switch']")
	public WebElement shipping_slider;

	@FindBy(id = "tCountry")
	public WebElement shipTo_dropdown;

	@FindBy(xpath = "//span[text()='Confirm' and @id='save']")
	public WebElement confirmShipping_button;

	@FindBy(xpath = "//form[@id='freight']/div/span//label")
	public WebElement international_country_text;

	@FindBy(id = "model_to_add")
	public WebElement quickbuyitem_textbox;
	@FindBy(id = "qty_to_add")
	public WebElement quickbuyQty_textbox;
	@FindBy(id = "add_to_cart")
	public WebElement quickbuyAdd_button;

	@FindBy(xpath = "//section[@class='cart_content']")
	public WebElement itemList;

	@CacheLookup
	@FindBy(xpath = "//section[@class='your_cart_empty']//span")
	public WebElement emptyCart_textBlock;

	@FindBy(xpath = "//span[@id='modelError']//span[@class='errorMessage']")
	public WebElement missingModel_text;

	@FindBy(xpath = "//span[@id='qtyError']//span[@class='errorMessage']")
	public WebElement missingQty_text;

	@FindBy(xpath = "//span[contains(text(),'Ordering Delivery and Warranty Information ›')]")
	public WebElement orderWarrantlyInfo_link;

	public TopMenuClass getTopMenu() {
		return topmenu;
	}

	public CheckoutPage clickCheckout() {
		try {
			logger.log(Status.INFO, "Clicking Checkout button");
			checkout_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		CheckoutPage checkout = new CheckoutPage(driver, logger);
		PageFactory.initElements(driver, checkout);
		return checkout;
	}
	
	public LoginPage clickCheckoutGuest() {
		try {
			logger.log(Status.INFO, "Clicking Checkout button");
			checkout_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		LoginPage login = new LoginPage(driver, logger);
		PageFactory.initElements(driver, login);
		return login;
	}

	public QuotePage clickConvertToQuote() {
		try {
			logger.log(Status.INFO, "Clicking Convert To Quote button");
			convertToQuote_button.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		QuotePage quote = new QuotePage(driver, logger);
		PageFactory.initElements(driver, quote);
		return quote;
	}

	/********************
	 * adding item via quick buy
	 ********************************/
	public void addItemQuickBuy(String model, String qty) {
		logger.log(Status.INFO, "Adding item via quick buy");
		waitForPageLoad();
		try {
			quickbuyitem_textbox.sendKeys(model);
			waitForPageLoad();
			quickbuyQty_textbox.sendKeys(qty);
			waitForPageLoad();
			quickbuyAdd_button.click();
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// checkAlert_Accept();
			Alert alert = driver.switchTo().alert();
			String alertMessage = driver.switchTo().alert().getText();
			alert.accept();

			quickbuyAdd_button.click();

		}

		waitForPageLoad();

	}

	public void selectFromSuggestionList(String part, String full) {
		try {
			logger.log(Status.INFO, "Entering part of item name");
			quickbuyitem_textbox.sendKeys(part);

			List<WebElement> elements = driver
					.findElements(By.xpath("//span[@id='quickbuy_suggested']//span[@class='item']"));

			boolean isListAvailable = elements.size() > 0;
			logger.log(Status.INFO, "Selecting the model from the suggestion list");
			waitForPageLoad();
			for (WebElement ele : elements) {
				if (ele.getText().equals(full)) {
					System.out.println(ele.getText());
					ele.click();
					break;
				}
			}

			logger.log(Status.PASS, "Successfully selected " + full + "from the auto suggestion list");
		} catch (AssertionError e) {
			reportFail(e.getMessage());
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}//

	public void isItemSubTotalCorrect(int subtotal) {
		try {
			int actual = driver.findElements(By.xpath("//section[@class='cart_content']")).size();
			Assert.assertTrue(actual == subtotal);

			logger.log(Status.PASS, "Item subtotal count is correct :" + actual);
		} catch (AssertionError e) {
			reportFail(e.getMessage() + "Incorrect Item SubTotal count");
		}

	}

	public void displayItemNames() {

		List<WebElement> items = driver.findElements(By.xpath("//section[@class='cart_content']//a"));

		for (WebElement item : items) {
			System.out.println(item.getText().trim());
		}

	}

	public void isItemInCart(String model, String expectedQty) {
		try {
			logger.log(Status.INFO, "Verifying if item added in cart");
			boolean flag = false;
			List<WebElement> items = driver.findElements(By.xpath("//section[@class='cart_content']//a"));

			for (WebElement item : items) {
				String quickBuyItem = item.getText();
				if (quickBuyItem.equalsIgnoreCase(model)) {
					flag = true;
					break;
				}
			}

			WebElement quantity = driver.findElement(By.xpath("//a[contains(@href,'" + model
					+ "')]//parent::span//parent::div//following-sibling::div/div/span//input"));
			String actualQty = quantity.getAttribute("value");
			System.out.println("******captured quanitity*******" + actualQty);

			boolean isCorrectQty = actualQty.equalsIgnoreCase(expectedQty);

			Assert.assertTrue(flag && isCorrectQty);
			reportPass("Item " + model + " with quantity of " + actualQty + " is displayed in the Cart");
		} catch (AssertionError e) {
			reportFail(e.getMessage() + " Quick Buy Cart error in Item and Quantity ");

		}

	}

	/************************ remove by model **********************************/
	public void removeItem(String model) {
		logger.log(Status.INFO, "Clicking Remove button text");
		// waitForPageLoad();
		driver.findElement(By.xpath("//a[text()='" + model
				+ "']//parent::span//parent::div//following-sibling::div//span[@class='qty_remove delete']")).click();

	}

	public void isemptyCartMessageDisplayed() {

		try {
			waitForPageLoad();
			int size = driver.findElements(By.xpath("//section[@class='cart_content']")).size();
			Assert.assertTrue(size == 0 && emptyCart_textBlock.isDisplayed());
			reportPass("The empty Cart message is displayed : " + emptyCart_textBlock.getText());

		} catch (AssertionError e) {
			reportFail(e.getMessage() + "Empty Cart text not displayed");
		}

	}

	public void clickQuickBuyAddToCart() {
		waitForPageLoad();
		quickbuyAdd_button.click();
	}

	public void isErrorMessageDisplayed() {

		try {
			waitForPageLoad();
			WebElement missingModel = driver
					.findElement(By.xpath("//span[@id='modelError']//span[@class='errorMessage']"));
			WebElement missingQty = driver.findElement(By.xpath("//span[@id='qtyError']//span[@class='errorMessage']"));
			Assert.assertTrue(missingModel.isDisplayed() && missingQty.isDisplayed());
			reportPass("Errors are displayed : " + missingModel.getText() + " and " + missingQty.getText());
		} catch (org.openqa.selenium.NoSuchElementException j) {
			System.out.println(j.getMessage());
		} catch (AssertionError e) {
			reportFail(e.getMessage() + " Error message not displayed ");

		}

	}

	public void isReelOptionsDisplayed() {

		try {
			WebElement reelOptions = driver.findElement(By.xpath("//div[@id='myModal']//div[@class='modal-content']"));
			Assert.assertTrue(reelOptions.isDisplayed());
			reportPass("Reel pop up options are displayed");
		} catch (AssertionError e) {
			reportFail(e.getMessage() + "Reel custom pop up not displayed");
		}
		// System.out.println(" Result is " + isDisplayed);

	}

	public void selectFromReelOptions(String option) {
		logger.log(Status.INFO, "Selection from Option radio button");
		WebElement ele = driver
				.findElement(By.xpath("//div[@id='myModal']//div[@class='modal-content']//div[@class='opt_block']["
						+ option + "]//input[@type='radio']"));
		waitForPageLoad();
		ele.click();
		logger.log(Status.INFO, "Selecting option number #" + option);
		driver.findElement(By.xpath("//div[@id='myModal']//div[@class='modal-content']//span[@id='ok']")).click();
		logger.log(Status.PASS, "Successfully selected #" + option + "from the radio button");
	}

	public void clickShipOutsideUS(String country) {
		try {
			logger.log(Status.INFO, "Clicking Shipping outside the US");
			shipping_slider.click();
			waitForPageLoad();
			logger.log(Status.INFO, "Selecting " + country);
			selectFromDropdownByText(driver, shipTo_dropdown, country);
			confirmShipping_button.click();
			Assert.assertTrue(international_country_text.getText().equalsIgnoreCase(country),
					"Mismatch / Incorrect displayed");
			reportPass(country + " Country is selected successfully");
		} catch (AssertionError e) {
			reportFail(e.getMessage() + "Country not available from the dropdown list");
		}

	}

	public void clickOrderWarrantyInfoLink() {
		logger.log(Status.INFO, "Clicking Order Warranty Info link");
		orderWarrantlyInfo_link.click();
		switchTonewTab();
	}

}//
