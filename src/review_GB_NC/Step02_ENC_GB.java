package review_GB_NC;

import java.io.IOException;

import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import lib.Excel;

public class Step02_ENC_GB {
	
private WebDriver driver;
public static String xlsFilePath = System.getProperty("user.dir") + "\\src\\testdata\\testdata.xls";
public String sheet="Login";

//Login to the ENC user 
@FindBy (xpath = ".//*[@id='content-main']/p[2]/a") private WebElement loginToContractor_Link;
@FindBy ( id="btn_signin") private WebElement Signin_Button ;
@FindBy ( xpath=".//*[@id='desktop']") private WebElement Username_Box ;
@FindBy ( xpath=".//*[@id='body']/div[1]/div[2]/div/div/form/input[4]") private WebElement Password_Box ;

//Serach the particular request
@FindBy (xpath = ".//*[@id='left-nav']//a[contains(text(),'Awaiting executive noncore approver action')]") private WebElement Awaiting_executive_noncore ;
@FindBy (xpath = ".//*[@id='left-nav']//a[contains(text(),'Candidates')]") private WebElement Candidates ;
@FindBy (id="FLD_REQUEST_NUMBER") private WebElement Req_Num_Search ;
@FindBy (name="btnGo") private WebElement GO_reqnum ;
@FindBy (xpath =" .//*[@id='content-main']/form/table[4]/tbody/tr[2]/td[5]/a" ) private WebElement skill_link ;

@FindBy ( xpath= "//span[@id='credentialSignin']") private WebElement cred_login;
//@FindBy ( xpath= "//input[@id='otp-input']") private WebElement otp_box;
//@FindBy(id = "submit_btn")    private WebElement otp_submit_btn;
@FindBy ( xpath= "//input[@name='username']") private WebElement login_username;
@FindBy ( xpath= "//input[@name='password']") private WebElement login_password;
@FindBy ( id="login-button") private WebElement login_Button ;
@FindBy(id = "otp-input")	private WebElement passcodeBox;
@FindBy(id = "submit_btn")	private WebElement SubmitPasscode;
@FindBy (xpath = "//label[@id='totp_label']") private WebElement Authenticator_App_option;
@FindBy (xpath = "//input[@id='newTotp-otp-input']") private WebElement EnterAccessCode_Box;
@FindBy (xpath = "//button[contains(text(),'Next: Verify')]") private WebElement Verify_Button;
@FindBy (xpath = ".//div[@id='totp_item']") private WebElement Totp_Link;
@FindBy (xpath = "//input[@id='otp-input']") private WebElement OTP_TextBox;
@FindBy (xpath = "//button[@id='submit_btn']") private WebElement OTP_Submit_Button;

//Submit the request to further authorization
//@FindBy (id ="FLD_CMTS_TO_REQSTR" ) private WebElement comment_box ;
@FindBy (xpath = "//*[@id='FLD_CMTS_TO_REQSTR']") private WebElement comment_box;
@FindBy (name ="btnSaveAndAuth" ) private WebElement Authorize_btn ;

	public Step02_ENC_GB(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}


	// Function to login to the application
	
	public void loginENC() throws IOException, InterruptedException{

//		WebDriverWait wait = new WebDriverWait(driver, 180);
//		wait.until(ExpectedConditions.visibilityOf(loginToContractor_Link));
	//
//		loginToContractor_Link.click();
		WebDriverWait wait00 = new WebDriverWait(driver, 180);
		wait00.until(ExpectedConditions.visibilityOf(cred_login));
		cred_login.click();
		
		//new login changes
		WebDriverWait wait01 = new WebDriverWait(driver, 180);
		wait01.until(ExpectedConditions.visibilityOf(login_Button));
		wait01.until(ExpectedConditions.elementToBeClickable(login_Button));
		login_username.clear();
		login_username.sendKeys(Excel.getCellValue(xlsFilePath, sheet, 30, 0));
		Thread.sleep(1000);
		login_password.clear();
		login_password.sendKeys(Excel.getCellValue(xlsFilePath, sheet, 30, 1));

		//Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE).save(System.getProperty("user.dir") + "\\src\\test\\resources\\Screens\\GB");
		login_Button.click();
		
		try {

			//Totp_Link.click();
			//String otpKeyStr = "47UORWOB2YBVMXQE"; // <- this 2FA secret key for csatestus2@c25a0161.toronto.ca.ibm.com
			String otpKeyStr = "EE544YJYKK3YNE3R"; // bennett12@c25a0161.toronto.ca.ibm.com 
			Totp totp = new Totp(otpKeyStr);
			String twoFactorCode = totp.now();
			
			//Authenticator_App_option.click();

			OTP_TextBox.sendKeys(twoFactorCode);
			System.out.println("value fetched from box= "+OTP_TextBox.getAttribute("value"));
			Thread.sleep(5000);
			OTP_Submit_Button.click();
			System.out.println("clicked on OTP submit button");
		}
		catch (Exception e) {
			System.out.println("no OTP screen");
			//e.printStackTrace();
		}	
	}

	public void ENC_open_Request ()
	{

		WebDriverWait wait01 = new WebDriverWait(driver, 180);
		wait01.until(ExpectedConditions.visibilityOf(Awaiting_executive_noncore));

		Awaiting_executive_noncore.click();

		WebDriverWait wait02 = new WebDriverWait(driver, 300);
		wait02.until(ExpectedConditions.visibilityOf(Req_Num_Search));

		Req_Num_Search.clear();
		Req_Num_Search.sendKeys(Excel.getCellValue(xlsFilePath, "Request_creation", 2, 15));
		GO_reqnum.click();

		WebDriverWait wait03 = new WebDriverWait(driver, 160);
		wait03.until(ExpectedConditions.visibilityOf(skill_link)); 
		
		/* code to capture screenshot */
		//Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save("C:\\Users\\SriSwathiAnushaNulu\\Documents\\Project csa\\Practice Automation\\NPrice\\NCore\\UK");

		skill_link.click();

	}
	public void submit_req() 
	{
		WebDriverWait wait04 = new WebDriverWait(driver, 600);
		wait04.until(ExpectedConditions.visibilityOf(comment_box));
		comment_box.sendKeys("comments entered");
		//Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save("C:\\Users\\SriSwathiAnushaNulu\\Documents\\Project csa\\Practice Automation\\NPrice\\NCore\\UK");
		
		WebDriverWait wait05 = new WebDriverWait(driver, 180);
		wait05.until(ExpectedConditions.visibilityOf(Authorize_btn));
		Authorize_btn.click();
	}
}
