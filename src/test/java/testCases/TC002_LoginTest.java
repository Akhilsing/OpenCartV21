package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	
	@Test
	public void Verify_login() {
		
		logger.info("**** Started TC002_loginTest ****");
		try
		{
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickLoginlnk();
		
		LoginPage lp=new LoginPage(driver);
		lp.EnterMailId(pro.getProperty("email"));
		lp.EnterPassword(pro.getProperty("password"));
		lp.clickLoginBtn();
		
		
		MyAccountPage myp=new MyAccountPage(driver);
		
		//Assert.assertEquals(myp.MYAccountHeadingExists(), true, "Login Failed");
		Assert.assertTrue(myp.MYAccountHeadingExists());
		myp.ClickLogout();
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("**** Finished TC002_loginTest ****");
	}

}
