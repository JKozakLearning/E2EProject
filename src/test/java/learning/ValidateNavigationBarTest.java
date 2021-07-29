package learning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.HomePage;

import java.io.IOException;

public class ValidateNavigationBarTest extends Base {
    HomePage homePage;
    public WebDriver driver;
    private static Logger logs= LogManager.getLogger(ValidateNavigationBarTest.class.getName());
    @BeforeTest
    public void initDriver() throws IOException {
        driver = initializeDriver();
    }

    @Test(priority = 1)
    public void validateNavBar_checkItsPresent_itsPresent() {
        driver.get(properties.getProperty("url"));
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.getHomeInNavigationBar().isDisplayed());
        // for check errors
        //Assert.assertEquals(homePage.getTitleText(), "FEATURED123 COURSES");

    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }
}
