package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


import io.github.bonigarcia.wdm.WebDriverManager;

/*
it contains reusable methods. what were is required for all
the test cases those methods we will keep inside base class
*/

public class BaseClass {
	
public Logger logger;
public  WebDriver driver;
public WebDriverManager driver1;
public Properties pro;
	
	@BeforeClass
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws InterruptedException, IOException
	{
		//loading the config.properties file
		//FileReader fr=new FileReader(".//src//test//resources//config.properties");
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties");
		pro=new Properties();
		pro.load(fr);
		
		logger=LogManager.getLogger(this.getClass());
		
		switch(br.toLowerCase()) 
		{
		case "chrome": WebDriverManager.chromedriver().setup(); 
					   driver=new ChromeDriver();
					   break;		
		case "edge": WebDriverManager.edgedriver().setup();
					 driver=new EdgeDriver();
					 break;
		case "firefox": WebDriverManager.firefoxdriver().setup();
						driver=new FirefoxDriver(); 
						break;
		default: System.out.println("Invalid brower name");
				 return;
		}
		System.out.println("Browser: " + br);
    	
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(pro.getProperty("OpencartUrl")); // getting the value from properties
		System.out.println("Opening URL: " + pro.getProperty("OpencartUrl"));
		driver.manage().window().maximize();

	}
	
	@AfterClass
	public void tearDown()
	{
		    if (driver != null) {
		        driver.quit();
		    }
	}
	
	public String randomString() {
		
		String generatedstring= RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomNumber() {
		
		String generatednumber= RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
	
	public String randomAlphanumbeic() {
		
		String generatedstring= RandomStringUtils.randomAlphabetic(3);
		String generatednumber= RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);
	}
	
	
	public String captureScreenshot(String testName, WebDriver driver) {

	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
	            .format(new Date());

	    TakesScreenshot ts = (TakesScreenshot) driver;

	    File src = ts.getScreenshotAs(OutputType.FILE);

	    String path = System.getProperty("user.dir")
	            + "/screenshots/"
	            + testName + "_" + timeStamp + ".png";

	    try {
	        FileUtils.copyFile(src, new File(path));
	        System.out.println("Successfully captured a screenshot: " + path);
	    } catch (IOException e) {
	        System.out.println("Screenshot failed: " + e.getMessage());
	    }

	    return path;
	}
    

}
