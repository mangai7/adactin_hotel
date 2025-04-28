package testbase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import adactingroup.ExcelReader;
import adactingroup.LoginPage;
import adactingroup.SearchHotelPage;
import utilities.ScreenshotListener;

@Listeners(ScreenshotListener.class)   // This connects your ScreenshotListener

public class SearchHotelTest extends BaseClass {
	public WebDriver driver;
	LoginPage LP;
	SearchHotelPage SHP;
    
	@BeforeClass
    public void setUpTest() throws Exception {
        LP = new LoginPage(BaseClass.driver);
        SHP = new SearchHotelPage(BaseClass.driver);
        //login
        String username = ExcelReader.getCellValue("LoginData", 1, 0); // 2nd row, 1st column
	    String password = ExcelReader.getCellValue("LoginData", 1, 1); // 2nd row, 2nd column
	    LP.username(username);
		LP.password(password);
		Thread.sleep(500);
		LP.login();
	}

    // Data Provider for Search Hotel Test
    @DataProvider(name = "searchHotelData")
    public Object[][] searchHotelData() {
        return new Object[][] {
            // Location, Hotel, Room Type, Room Number, Check-in Date, Check-out Date, Adult Number, Child Number
            {"Sydney", "Hotel Creek", "Double", "2 - Two", "20/09/2025", "21/09/2025", "2 - Two", "2 - Two"},
            {"Sydney", "Hotel Creek", "Single", "1 - One", "22/09/2025", "23/09/2025", "1 - One", "1 - One"}
        };
    }

    // Test 1: Search Hotel with valid data
    @Test(priority = 1, dataProvider = "searchHotelData")
    void TC_07_testSearchHotel(String location, String hotel, String roomType, String roomNumber, String inDate, String outDate, String adult, String child) throws Exception {
        SHP.selectLocation(location);
        Thread.sleep(500);
        SHP.selectHotel(hotel);
        Thread.sleep(500);
        SHP.selectRoomType(roomType);
        Thread.sleep(500);
        SHP.selectNumberOfRoom(roomNumber);
        Thread.sleep(500);
        SHP.selectInDate(inDate);
        Thread.sleep(500);
        SHP.selectOutDate(outDate);
        Thread.sleep(500);
        SHP.selectNumberOfAdult(adult);
        Thread.sleep(500);
        SHP.selectNumberOfChild(child);
        Thread.sleep(500);
        SHP.SelectSearch();
        String actualTitle = BaseClass.driver.getTitle();
        System.out.println("Page Title after login: " + actualTitle);
        Assert.assertEquals(actualTitle, "Adactin.com - Select Hotel", "Expected failed.");
    }

    // Test 2: Missing Check-in Date
    @Test(priority = 2)
    void TC_08_testMissingInDate() throws Exception {
        SHP.SelectSearchIcon();
        SHP.selectLocation("Sydney");
        Thread.sleep(500);
        SHP.selectHotel("Hotel Creek");
        Thread.sleep(500);
        SHP.selectRoomType("Double");
        Thread.sleep(500);
        SHP.selectNumberOfRoom("2 - Two");
        SHP.indateclear();
        Thread.sleep(500);
        SHP.selectOutDate("21/09/2025");
        Thread.sleep(500);
        SHP.selectNumberOfAdult("2 - Two");
        Thread.sleep(500);
        SHP.selectNumberOfChild("1 - One");
        Thread.sleep(500);
        SHP.SelectSearch();
        // Validate error message
        String actualMsg = SHP.checkinspan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase("Please Select Check-In Date"), "Error message mismatch");
    }

    // Test 3: Missing Check-out Date
    @Test(priority = 3)
    void TC_09_testMissingOutDate() throws Exception {
        SHP.SelectSearchIcon();
        SHP.selectLocation("Sydney");
        Thread.sleep(500);
        SHP.selectHotel("Hotel Creek");
        Thread.sleep(500);
        SHP.selectRoomType("Double");
        Thread.sleep(500);
        SHP.selectNumberOfRoom("2 - Two");
        SHP.indateclear();
        Thread.sleep(500);
        SHP.selectInDate("20/09/2025");
        Thread.sleep(500);
        SHP.outdateclear();
        Thread.sleep(500);
        SHP.selectNumberOfAdult("2 - Two");
        Thread.sleep(500);
        SHP.selectNumberOfChild("1 - One");
        Thread.sleep(500);
        SHP.SelectSearch();
        // Validate error message
        String actualMsg = SHP.checkoutspan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase("Please Select Check-Out Date"), "Error message mismatch");
    }

    // Test 4: Check-out Date before Check-in Date
    @Test(priority = 4)
    void TC_10_testDateBeforeInDate() throws Exception {
        SHP.SelectSearchIcon();
        SHP.selectLocation("Sydney");
        Thread.sleep(500);
        SHP.selectHotel("Hotel Creek");
        Thread.sleep(500);
        SHP.selectRoomType("Double");
        Thread.sleep(500);
        SHP.selectNumberOfRoom("2 - Two");
        Thread.sleep(500);
        SHP.selectInDate("20/09/2025");
        Thread.sleep(500);
        SHP.selectOutDate("19/09/2025"); // given before in-date
        Thread.sleep(500);
        SHP.selectNumberOfAdult("2 - Two");
        Thread.sleep(500);
        SHP.selectNumberOfChild("2 - Two");
        Thread.sleep(500);
        SHP.SelectSearch();
        String actualMsg = SHP.checkoutspan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase("Check-Out Date shall be after than Check-In Date"), "Error message mismatch");
    }

    // Test 5: Missing Location
    @Test(priority = 5)
    void TC_11_testWithoutLocation() throws Exception {
        SHP.SelectSearchIcon();
        // Do not select location
        SHP.selectHotel("Hotel Creek");
        Thread.sleep(500);
        SHP.selectRoomType("Double");
        Thread.sleep(500);
        SHP.selectNumberOfRoom("2 - Two");
        Thread.sleep(500);
        SHP.selectInDate("20/09/2025");
        Thread.sleep(500);
        SHP.selectOutDate("19/09/2025");
        Thread.sleep(500);
        SHP.selectNumberOfAdult("2 - Two");
        Thread.sleep(500);
        SHP.selectNumberOfChild("2 - Two");
        Thread.sleep(500);
        SHP.SelectSearch();
        String actualMsg = SHP.locationspan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase("Please Select a Location"), "Error message mismatch");
    }

    // Test 6: Missing Room Type
    @Test(priority = 6)
    void TC_12_testWithoutRoomType() throws Exception {
        SHP.SelectSearchIcon();
        SHP.selectLocation("Sydney");
        Thread.sleep(500);
        SHP.selectHotel("Hotel Creek");
        Thread.sleep(500);
        // Do not select room type
        SHP.selectNumberOfRoom("2 - Two");
        Thread.sleep(500);
        SHP.selectInDate("20/09/2025");
        Thread.sleep(500);
        SHP.selectOutDate("19/09/2025");
        Thread.sleep(500);
        SHP.selectNumberOfAdult("2 - Two");
        Thread.sleep(500);
        SHP.selectNumberOfChild("2 - Two");
        Thread.sleep(500);
        SHP.SelectSearch();
        String actualTitle = BaseClass.driver.getTitle();
        System.out.println("Page Title after login: " + actualTitle);
        Assert.assertTrue(actualTitle.equalsIgnoreCase("Adactin.com - Search Hotel"), "message mismatch");
    }
}
