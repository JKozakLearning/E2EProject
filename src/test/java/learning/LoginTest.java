package learning;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.HomePage;
import pageObjects.LogingPage;

import java.io.IOException;


public class LoginTest extends Base {
    HomePage homePage;
    LogingPage logingPage;
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(LoginTest.class.getName());
    @BeforeTest
    public void initDriver() throws IOException {
        driver = initializeDriver();
        logs.info("driver is initialized");
    }

    // go to login site
    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get(properties.getProperty("url"));
        logs.info("page is loaded correctly");
        homePage = new pageObjects.HomePage(driver);
        homePage.closePopup();
        logingPage = homePage.clickSignInButton();
    }

    @Test(dataProvider = "getDataGoodEmails", priority = 1)
    public void LoginTest_enterGoodEmails_changeURL(String email, String password) {
        logingPage.enterEmail(email);
        logingPage.enterPassword(password);
        logingPage.clickLoginButton();
        Assert.assertEquals(logingPage.getUrlAfterEmailAndPasswordPassed(),
                "https://rahulshettyacademy.com/sign_in/?email="
                        + email.split("@")[0] + "%40" + email.split("@")[1]
                        + "&+password=" + password + "&commit=Log+In");

    }

    @Test(dataProvider = "getDataWrongEmails", priority = 2)
    public void LoginTest_enterWrongEmails_LoginErrorMsg(String email, String password) {
        logingPage.enterEmail(email);
        logingPage.enterPassword(password);
        logingPage.clickLoginButton();
        Assert.assertEquals(logingPage.getAlertText(), "Invalid email or password.");

    }

    @DataProvider
    public Object[][] getDataGoodEmails() {

        Object[][] data = new Object[2][2];
        data[0][0] = "nonrestricteduser@mail.com";
        data[0][1] = "qwerty1234";

        data[1][0] = "restricteduser2@mail.com";
        data[1][1] = "qwerty1234";


        return data;
    }

    @DataProvider
    public Object[][] getDataWrongEmails() {

        Object[][] data = new Object[2][2];
        data[0][0] = "nonrestricteduser_mail.com";
        data[0][1] = "qwerty1234";


        data[1][0] = "restricteduser2@mail.com";
        data[1][1] = "qwerty1234";


        return data;
    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }
}
