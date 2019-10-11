package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;

public class HomePage extends PageBaseClass{
	
	public TopMenuClass topmenu;
	//public ExtentTest logger;
	
	public HomePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 
	}
	
	public TopMenuClass getTopMenu() {
		return topmenu;
	}
	


}//

/**
 * 
 * 
 * 
 * 
 * */
