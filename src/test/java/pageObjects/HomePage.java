package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Constructor, Locators, Actions

//Inheritance concept is using like extends
public class HomePage extends BasePage {

// this all the constructor we created	
	
	public HomePage(WebDriver driver) {
	/*
		Actually, here we need to write page.init elements but I 
		have not written that because I also created separated 
		constructor in separate class because all page objects 
		contain the constructor right so instead of writing the 
		constructor in every page object class I separated the 
		constructor in another class that is extended into this 
		constructor
		*/
		
		super(driver);
		
	}
	
@FindBy(xpath = "//a[@title='My Account' and @class='dropdown-toggle']")
WebElement lnkMyAccount;

@FindBy(xpath= "//ul[@class='dropdown-menu dropdown-menu-right']//a[text()='Register']")
WebElement lnkRegister;

@FindBy(linkText="Login")
WebElement lnkLogin;

public void clickMyAccount() {
wait.until(ExpectedConditions.elementToBeClickable(lnkMyAccount)).click();
}

public void clickRegisterlnk() {
	wait.until(ExpectedConditions.elementToBeClickable(lnkRegister)).click();
}

public void clickLoginlnk() {
	wait.until(ExpectedConditions.elementToBeClickable(lnkLogin)).click();
}
}
