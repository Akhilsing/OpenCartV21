package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);

	}
	
@FindBy(xpath= "//input[@name='email']")
WebElement EmailInputField;

@FindBy(xpath="//*[@value='Continue']")	
WebElement ContinueBtn;

@FindBy(xpath="//*[text()='Warning: You must agree to the Privacy Policy!']")
WebElement MissAnyFieldM;

	public void ContinueButton() {
		wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn)).click();
	}
		

	public void EnterEmail(String email) {
	WebElement EmailInput=wait.until(ExpectedConditions.visibilityOf(EmailInputField));
	EmailInput.sendKeys(email);
		
	}
	

	public String MissAnyMandatoryField() {
		
		WebElement MissFiled=wait.until(ExpectedConditions.visibilityOf(MissAnyFieldM));
		return MissFiled.getText();
	}




	
	

}
