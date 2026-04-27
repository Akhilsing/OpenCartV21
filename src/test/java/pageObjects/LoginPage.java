package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		
		super(driver);
	}

@FindBy(xpath="//input[@placeholder='E-Mail Address']")
WebElement EmailInput;

@FindBy(xpath="//input[@placeholder='Password']")
WebElement PasswordInput;

@FindBy(xpath="//*[@id='content']/div/div[2]/div/form/input")
WebElement Loginbtn;


public void EnterMailId(String email) {
	WebElement EnterEmail=wait.until(ExpectedConditions.visibilityOf(EmailInput));
	EnterEmail.sendKeys(email);
}

public void EnterPassword(String pwd) {
	WebElement EnterPwd=wait.until(ExpectedConditions.visibilityOf(PasswordInput));
	EnterPwd.sendKeys(pwd);
}

public void clickLoginBtn() {
	wait.until(ExpectedConditions.elementToBeClickable(Loginbtn)).click();
}




}
