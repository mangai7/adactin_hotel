package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import testbase.BaseClass;

public class ScreenshotListener extends BaseClass implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Add a null and session check before taking screenshot
        if (driver != null) {
            try {
                // Only try to take screenshot if driver session is active
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotName = "screenshots/" + result.getName() + "_" + java.time.LocalDateTime.now().toString().replace(":", "_") + ".png";
                FileUtils.copyFile(src, new File(screenshotName));
                System.out.println("Screenshot taken: " + screenshotName);
            } catch (Exception e) {
                System.out.println("Could not capture screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Driver is null, cannot capture screenshot.");
        }
    }
}
