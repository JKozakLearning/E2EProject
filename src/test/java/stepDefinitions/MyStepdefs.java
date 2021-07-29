package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import learning.Base;
import learning.LoginTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.HomePage;
import pageObjects.LogingPage;

import java.io.IOException;

public class MyStepdefs extends Base {
    HomePage homePage;
    LogingPage logingPage;
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(LoginTest.class.getName());
    String currentEmail;
    String currentPassword;

    @Given("^Initialize the browser with chrome$")
    public void initializeTheBrowserWithChrome() throws IOException {
        driver = initializeDriver();
        logs.info("driver is initialized");
    }

    @And("Navigate to {string} Site")
    public void navigateToSite(String arg0) {
        driver.get(arg0);
        logs.info("page is loaded correctly");
        homePage = new pageObjects.HomePage(driver);
        homePage.closePopup();

    }

    @And("^Click on Login link in home page to land on sign in page$")
    public void clickOnLoginLinkInHomePageToLandOnSignInPage() {
        logingPage = homePage.clickSignInButton();
    }

    @When("^User enters email (.+) and password (.+) and press logs in button$")
    public void userEntersEmailUsernameAndPasswordPasswordAndPressLogsInButton(String username, String password){
        currentEmail = username;
        currentPassword = password;
        logingPage.enterEmail(username);
        logingPage.enterPassword(password);
        logingPage.clickLoginButton();
    }


    @Then("^Verify that user is successfully login$")
    public void verifyThatUserIsSuccessfullyLogin() {
        Assert.assertEquals(logingPage.getUrlAfterEmailAndPasswordPassed(),
                "https://rahulshettyacademy.com/sign_in/?email="
                        + currentEmail.split("@")[0] + "%40" + currentEmail.split("@")[1]
                        + "&+password=" + currentPassword + "&commit=Log+In");
    }

    @Then("^Verify correct error msg$")
    public void verifyCorrectErrorMsg() {
        Assert.assertEquals(logingPage.getAlertText(), "Invalid email or password.");
    }

    @And("^Quit browser$")
    public void quitBrowser() {
        driver.quit();
    }

    //    @When("User enters email {String} and password {String} and press logs in button")
//    public void userEntersEmailAndPasswordAndPressLogsInButton(String arg0, String arg1) {
//        currentEmail = arg0;
//        currentPassword = arg1;
//        logingPage.enterEmail(arg0);
//        logingPage.enterPassword(arg1);
//        logingPage.clickLoginButton();
//
//
//    }




}
