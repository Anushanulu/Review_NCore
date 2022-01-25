package review_US_NC_main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;

import lib.Excel;
import review_IN_NC.Step03_SBAuth_IN;
import review_US_NC.Step02_SBAuth;


public class SB_Auth
{
	// TestNG logger

	public static Logger log = Logger.getLogger("TnM");

	public static String xlsFilePath = System.getProperty("user.dir") + "\\src\\testdata\\testdata.xls";
	public String sheet="Login"; 
	public String url;
	public String id;
	public String paswd;
	public String id_green;
	public String paswd_green;
	public String url_green;
	public String url2;

	public WebDriver driver;


	@BeforeTest
	public void setup()
	{
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\SriSwathiAnushaNulu\\Documents\\Softwares\\geckodriver-v0.29.0-win64\\geckodriver.exe");

		driver = new FirefoxDriver();

		//System.setProperty("webdriver.chrome.driver","C:\\Chrome driver\\chromedriver_win32\\chromedriver.exe");
		//driver = new ChromeDriver();

		//id = Excel.getCellValue(xlsFilePath, sheet, 5, 0);
		//paswd = Excel.getCellValue(xlsFilePath, sheet, 5, 1);
		url = Excel.getCellValue(xlsFilePath, sheet, 5, 2);

		//String url1 = "https://" +  id + ":" + paswd + "@" + url;

		driver.get(url);  
		driver.manage().window().maximize();
	}	


	//Test for Skill Request page
	@Test(priority=0)
	public void SB_login() throws InterruptedException 
	{

		Step02_SBAuth sb= new Step02_SBAuth (driver);
		sb.loginSB();	
	}

	@Test(priority=1)
	public void SB_Details() 
	{

		Step02_SBAuth sbdetails= new Step02_SBAuth (driver);
		sbdetails.SB_open_Request();
	}
	@Test(priority=2)
	public void SB_auth() 
	{

		Step02_SBAuth sbAuth= new Step02_SBAuth (driver);
		sbAuth.SB_Auth_req();
	}


}
