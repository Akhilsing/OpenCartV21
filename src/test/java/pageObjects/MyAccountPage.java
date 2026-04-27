package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyAccountPage extends BasePage {
	
	public MyAccountPage(WebDriver driver) {
		
		super(driver);
	}
	
	@FindBy(xpath="//h2[text()='My Account']")
	WebElement MyAccountHeading;
	
	@FindBy(xpath="//*[@id='column-right']/div/a[13]")
	WebElement Logout;
	
	public boolean MYAccountHeadingExists() {
		
		try
		{
			WebElement MyAccountHeader=wait.until(ExpectedConditions.visibilityOf(MyAccountHeading));
			System.out.println(MyAccountHeader.getText());
			return (MyAccountHeader.isDisplayed());
		}
		catch(Exception e) 
		{
			return false;
		}
	
		
	}
	
	public void ClickLogout() {
	wait.until(ExpectedConditions.elementToBeClickable(Logout)).click();
	
	}

}
