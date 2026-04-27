package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

import utilities.Data_Provides;

public class TC003_LoginTestDDTpractice extends BaseClass {
	
	@Test(dataProvider="excelData", dataProviderClass=Data_Provides.class)
	public void Verify_loginDDT(String email, String pwd, String res) {
		
		logger.info("**** Started TC003_LoginTestDDT ****");
		try
		{
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickLoginlnk();
		
		LoginPage lp=new LoginPage(driver);
		lp.EnterMailId(email);
		lp.EnterPassword(pwd);
		lp.clickLoginBtn();
		
		
		MyAccountPage myp=new MyAccountPage(driver);
		if(res.equalsIgnoreCase("valid")) {
			if(myp.MYAccountHeadingExists()==true) {
				logger.info("Logged with valid credentials test pass");
				myp.ClickLogout();
				Assert.assertTrue(true);
				
			}
			else 
			{
				logger.info("Logged with invalid credentials test fail");
				Assert.assertTrue(false);
			}
		}
			
		if(res.equalsIgnoreCase("Invalid")) {
			logger.info("Logged with invalid credentials test fail");
			myp.ClickLogout();
			Assert.assertTrue(false);
		}
		else 
		{
			logger.info("Logged with valid credentials test pass");
			Assert.assertTrue(true);
		}
	
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("**** Finished TC003_LoginTestDDT ****");
	}

}
