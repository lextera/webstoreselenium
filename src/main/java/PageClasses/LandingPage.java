package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.PageBaseClass;
import baseClasses.TopMenuClass;



public class LandingPage extends PageBaseClass {
	
	public TopMenuClass topmenu;
	public ExtentTest logger;

	public LandingPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topmenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topmenu); 

	}

	

	
	//so we can directly use it inside test cases
	public TopMenuClass getTopMenu() {
		System.out.println("From topMenu " + driver.getCurrentUrl());
		return topmenu;
	}
	
}//

