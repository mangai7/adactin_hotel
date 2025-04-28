package testbase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class BaseClass {
public static WebDriver driver;
	
	
	@BeforeClass
	public void setup() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://adactinhotelapp.com");
		
		
		
	}
	

	@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
	
}
	public void takeScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            
            // Check if "screenshots" folder exists, if not create it
            File screenshotsFolder = new File("screenshots");
            if (!screenshotsFolder.exists()) {
                screenshotsFolder.mkdir();
            }
            
            File destFile = new File(screenshotsFolder, fileName + ".png");
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot taken: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
	
	

}
