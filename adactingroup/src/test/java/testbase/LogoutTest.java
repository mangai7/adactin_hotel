package testbase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import adactingroup.LoginPage;
import adactingroup.LogoutPage;
import utilities.ScreenshotListener;

@Listeners(ScreenshotListener.class)   // This connects your ScreenshotListener
public class LogoutTest extends BaseClass{
	public WebDriver driver;
	LoginPage LP;
	LogoutPage logout;
	@BeforeClass
	public void setUpTest() throws Exception {
        LP = new LoginPage(BaseClass.driver);
        logout=new LogoutPage(BaseClass.driver);
        
	    LP.username("mangaipres");
		LP.password("mangai@123");
		Thread.sleep(500);
		LP.login();
		

        
    }
	@Test
	void TC_22_testlogout() throws Exception {
		logout.logout();
		Thread.sleep(2000);
		String actualMsg = logout.msg().getText();
	    System.out.println("Message: " + actualMsg);
	    //Assert.assertTrue(actualMsg.equalsIgnoreCase("You have successfully logged out."), "message mismatch");
	    Assert.assertTrue(actualMsg.toLowerCase().contains("you have successfully logged out.".toLowerCase()), "message mismatch");
		
	}
	
	

}
