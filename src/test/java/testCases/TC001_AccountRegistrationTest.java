package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

// one single class is one test case
public class TC001_AccountRegistrationTest extends BaseClass {
	
/*	
  this are all required for other test cases so we are 
   creating Base class inside that we will add these.
   
   public WebDriver driver;
   
	@BeforeClass
	public void setup() throws InterruptedException
	{
    	driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		
		driver.get("https://tutorialsninja.com/demo/");
		driver.manage().window().maximize();

	}
*/	
	

/*	
  this are all required for other test cases so we are 
   creating Base class inside that we will add these.
 
	@AfterClass
	public void tearDown()
	{	
		driver.close();

	}
*/
	
	@Test
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		try
		{
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		logger.info("*** Clicked on the MyAccount link ***");
		Thread.sleep(1000);
		hm.clickRegisterlnk();
		logger.info("*** Clicked on Register link ***");
		
		logger.info("*** Filling the Registration from ***");
		AccountRegistrationPage arp=new AccountRegistrationPage(driver);
		arp.EnterEmail(randomString()+"@gmail.com");
		arp.ContinueButton();
		Thread.sleep(3000);
		String MissMantaboryField= arp.MissAnyMandatoryField();
		if(MissMantaboryField.equals("Warning: You must agree to the Privacy Policy!"))
		{
			Assert.assertTrue(true);
		}
		else {
			logger.error("Test case failed");
			logger.debug("debug logs");
			Assert.assertTrue(false);
		}
	
		}
		catch(Exception e)
		{
			
			Assert.fail();
		}
		
		logger.info("*** Test execution is finished ***");
	}

}
