package PageClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.PageBaseClass;

public class OrderHistoryPage extends PageBaseClass{

	public OrderHistoryPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);

	}

	@FindBy(xpath = "//tbody[@id='history_table']/tr/td[1]/a") public WebElement orderNumber_list;
	
	
	public void isInOrderHistory(String orderNum) {
		try {
			boolean isOnList = false;
			List<WebElement> elements = driver.findElements(By.xpath("//tbody[@id='history_table']/tr/td[1]/a"));
			
			for(WebElement element : elements) {
				
				System.out.println(element.getText());
					if(element.getText().equals(orderNum)) {
						isOnList =  true;
						break;
					}else {
						isOnList =  false;
					}
				}
			
			Assert.assertTrue(isOnList);
			reportPass("Order Number : "+orderNum +"is in History table");
		} catch (AssertionError e) {
			reportFail(e.getMessage() +"Order Number not in History table");
		}
		
	}
	
}//
