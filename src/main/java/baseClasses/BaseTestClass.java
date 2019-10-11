package baseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.ExtentReportManager;

public class BaseTestClass {

	public static WebDriver driver;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	public static Properties prop;

	/****************** Invoke Browser ***********************/
	public void invokeBrowser(String browserName) {
		
		
		File src = new File(System.getProperty("user.dir")+"//src//main//java//configFiles//config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		
		try {

			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();

			} else {
				driver = new SafariDriver();
			}

		} catch (Exception e) {
			// reportFail(e.getMessage());
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterMethod // will execute after each test case
	public void flushReport() {
		report.flush();
		driver.manage().deleteAllCookies();
		driver.close();
	}
	

	/***************** Wait Functions in Framework *****************/
	public static void waitForPageLoad() {
		js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}

	}

	public static void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***************** Select by value *****************/
	public static void selectFromDropdownByValue(WebDriver driver, WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/***************** Select by text *****************/
	public static void selectFromDropdownByText(WebDriver driver, WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);

		// Credit Card Ending 5326

	}

	/***************** scroll into element *****************/
	public static void scrollIntoViewJS(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);

	}

	public static void waitTillElementFound(WebElement ElementTobeFound) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(ElementTobeFound));
	}

	public static void waitForElementToClick(WebElement element) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static boolean retryingFindClick(String xpath) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(By.xpath(xpath)).click();
				result = true;
				break;
			} catch (Exception e) {
			}
			attempts++;
		}
		return result;
	}

	public static boolean isElementPresentById(WebElement element) {

		boolean flag = true;
		try {
			element.isDisplayed();
			// webDrv.findElement(By.id(targetId));

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean checkAlert_Accept() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			a.accept();
			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}
	
	/****************switchToNewTab**********************/	
	public void switchTonewTab() {
		String parent=driver.getWindowHandle();
		Set<String>s1=driver.getWindowHandles();
		Iterator<String> I1= s1.iterator();
		while(I1.hasNext())
		{
		  String child_window=I1.next();
		  if(!parent.equals(child_window))
		  {
		    driver.switchTo().window(child_window);
		    System.out.println(driver.switchTo().window(child_window).getTitle());
		    driver.close();
		  }
		}
		driver.switchTo().window(parent);
		System.out.println(driver.switchTo().window(parent).getTitle());
	}
}//
