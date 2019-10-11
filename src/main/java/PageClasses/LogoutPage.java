package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class LogoutPage extends PageBaseClass{
	
	//public ExtentTest logger;
	public TopMenuClass topmenu;
	
	public LogoutPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu);
	}

	
	
}//
