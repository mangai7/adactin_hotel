package testbase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import adactingroup.BookHotelPage;
import adactingroup.ExcelReader;
import adactingroup.LoginPage;
import adactingroup.SearchHotelPage;
import utilities.ScreenshotListener;

@Listeners(ScreenshotListener.class)   //  This connects your ScreenshotListener
public class BookHotelTest extends BaseClass{

	public WebDriver driver;
	LoginPage LP;
	SearchHotelPage SHP;
	BookHotelPage BHP;
	
	@BeforeClass
    public void setUpTest() throws Exception {
        LP = new LoginPage(BaseClass.driver);
        SHP = new SearchHotelPage(BaseClass.driver);
        BHP = new BookHotelPage(BaseClass.driver);
        //login
        String username = ExcelReader.getCellValue("LoginData", 1, 0); // 2nd row, 1st column
	    String password = ExcelReader.getCellValue("LoginData", 1, 1); // 2nd row, 2nd column
	    LP.username(username);
		LP.password(password);
		Thread.sleep(500);
		LP.login();
		
		SHP.selectLocation("Sydney");
		Thread.sleep(500);
		SHP.selectHotel("Hotel Creek");
		Thread.sleep(500);
		SHP.selectRoomType("Double");
		Thread.sleep(500);
		SHP.selectNumberOfRoom("2 - Two");
		Thread.sleep(500);
		SHP.selectInDate("20/09/2025"); //"dd/mm/yyyy"
		Thread.sleep(500);
		SHP.selectOutDate("21/09/2025");//"dd/mm/yyyy"
		Thread.sleep(500);
		SHP.selectNumberOfAdult("2 - Two");
		Thread.sleep(500);
		SHP.selectNumberOfChild("2 - Two");
		Thread.sleep(500);
		SHP.SelectSearch();
		
        
    }
	
	
	@DataProvider(name = "invalidData")
    public Object[][] invalidData() {
        return new Object[][] {
            { "", "k", "no.24,Vela street,Chennai.", "1234567812345678", "VISA", "June", "2026", "223", "Please Enter your First Name" },
            { "Sree", "", "no.24,Vela street,Chennai.", "1234567812345678", "VISA", "June", "2026", "223", "Please Enter your Last Name" },
            { "Sree", "k", "", "1234567812345678", "VISA", "June", "2026", "223", "Please Enter your Address" },
            { "Sree", "k", "no.24,Vela street,Chennai.", "", "VISA", "June", "2026", "223", "Please Enter your 16 Digit Credit Card Number" }
        };
    }

    @DataProvider(name = "validData")
    public Object[][] validData() {
        return new Object[][] {
            { "Sree", "k", "no.24,Vela street,Chennai.", "1234567812345678", "VISA", "June", "2026", "223", "Booking Confirmation" }
        };
    }

    @Test(priority = 1, dataProvider = "invalidData")
    void TC_13_testWithOutClickingRadioButton(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) {
        BHP.clickContinueButton();
        String actualMsg = BHP.radiospan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 2, dataProvider = "validData")
    void TC_14_testGoToBookingPage(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) {
        BHP.clickRadioButton();
        BHP.clickContinueButton();
        String actualMsg = BHP.booktitle().getText();
        System.out.println("Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 3, dataProvider = "invalidData")
    void TC_15_testWithoutFirstname(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) throws Exception {
        BHP.lastname(lastname);
        BHP.address(address);
        BHP.cardnum(cardnum);
        BHP.cardtype(cardtype);
        BHP.expmonth(expmonth);
        BHP.expyear(expyear);
        BHP.cvvnum(cvvnum);
        Thread.sleep(500);
        BHP.booknow();
        Thread.sleep(4000);

        String actualMsg = BHP.firstnamespan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 4, dataProvider = "invalidData")
    void TC_16_testWithoutLastname(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) throws Exception {
        BHP.firstname(firstname);
        BHP.clearlastname();
        BHP.address(address);
        BHP.cardnum(cardnum);
        BHP.cardtype(cardtype);
        BHP.expmonth(expmonth);
        BHP.expyear(expyear);
        BHP.cvvnum(cvvnum);
        Thread.sleep(500);
        BHP.booknow();
        Thread.sleep(4000);

        String actualMsg = BHP.lastnamespan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 5, dataProvider = "invalidData")
    void TC_17_testWithoutBillingAddress(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) throws Exception {
        BHP.firstname(firstname);
        BHP.lastname(lastname);
        BHP.clearaddr();
        BHP.cardnum(cardnum);
        BHP.cardtype(cardtype);
        BHP.expmonth(expmonth);
        BHP.expyear(expyear);
        BHP.cvvnum(cvvnum);
        BHP.booknow();
        Thread.sleep(4000);

        String actualMsg = BHP.addressspan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 6, dataProvider = "invalidData")
    void TC_18_testWithoutCardNumber(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) throws Exception {
        BHP.firstname(firstname);
        BHP.lastname(lastname);
        BHP.address(address);
        BHP.clearcardnum();
        BHP.cardtype(cardtype);
        BHP.expmonth(expmonth);
        BHP.expyear(expyear);
        BHP.cvvnum(cvvnum);
        Thread.sleep(500);
        BHP.booknow();
        Thread.sleep(4000);

        String actualMsg = BHP.cardpan().getText();
        System.out.println("Error Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 7, dataProvider = "validData")
    void TC_19_testBookWithValidData(String firstname, String lastname, String address, String cardnum, String cardtype, String expmonth, String expyear, String cvvnum, String expectedMessage) throws Exception {
        BHP.firstname(firstname);
        BHP.lastname(lastname);
        BHP.address(address);
        BHP.cardnum(cardnum);
        BHP.cardtype(cardtype);
        BHP.expmonth(expmonth);
        BHP.expyear(expyear);
        BHP.cvvnum(cvvnum);
        Thread.sleep(500);
        BHP.booknow();
        Thread.sleep(5000);

        String actualMsg = BHP.titleconfirm().getText();
        System.out.println("Message: " + actualMsg);
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMessage), "message mismatch");
    }

    @Test(priority = 8)
    void TC_20_testItinerary() throws Exception {
        BHP.itinerary();
        BHP.selectall();
        BHP.selectdelete();

        if (driver != null) {
            try {
                Alert alert = driver.switchTo().alert();

                // Get alert text (optional)
                String alertText = alert.getText();
                System.out.println("Alert says: " + alertText);

                // Accept the alert (click OK)
                alert.accept();
                Thread.sleep(4000);

            } catch (NoAlertPresentException e) {
                System.out.println("No alert present to accept.");
            }
        } else {
            System.out.println("Driver is null. Cannot switch to alert.");
        }
    }
}

