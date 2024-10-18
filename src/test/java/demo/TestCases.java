package demo;

import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

public class TestCases {

    ChromeDriver driver;
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test Case 01");
        double starRating = 4.0;
        driver.get("https://www.flipkart.com/");
        Wrappers.enterTextWrapper(driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "Washing Machine");
        Thread.sleep(3000);
        Wrappers.clickOnElementWapper(driver, By.xpath("//div[contains(text(),'Popularity')]"));
        Boolean status = Wrappers.seachStarRatingAndPrintCount(driver, By.xpath("//span [contains(@id, 'productRating')]/div"), starRating);
        Assert.assertTrue(status);
        System.out.println("End Test Case 01");

    }

   @Test
    public void testCase02() throws InterruptedException {
        @SuppressWarnings("unused")
        int discount =17 ;
        System.out.println("Start Test Case 02");
        driver.get("https://www.flipkart.com/");
        Wrappers.enterTextWrapper(driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "iPhone");
        

        Thread.sleep(2000);
        Boolean status = Wrappers.printTitleAndDiscountIphone(driver, By.xpath("//div[contains(@class,'yKfJKb')]"), 17);
        Assert.assertTrue(status);     
        System.out.println("End Test Case 02");
    }


    @Test
    public void testCase03() throws InterruptedException{
    System.out.println("Start Test Case 03");
    driver.get("https://www.flipkart.com/");
    Wrappers.enterTextWrapper (driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "Coffee Mug"); 
    Thread.sleep(3000);
    Wrappers.clickOnElementWapper (driver, By.xpath("//div[contains(text(), '4★ & above')]"));
    Thread.sleep(3000);
    Boolean status = Wrappers.printTitleAndImageUrlOfCoffeeMug(driver, By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']")); 
    Assert.assertTrue(status);
    System.out.println("End Test Case 03");
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
        System.out.println("END OF THE ALL TEST CASES");

    }
}
