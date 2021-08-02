package learning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.AcademyMainPage;
import pageObjects.HomePage;
import pageObjects.PracticeLoginPage;
import pageObjects.PracticePage;

import java.io.IOException;

public class LoginToPracticeTest extends Base{
    public WebDriver driver;
    private HomePage homePage;
    private AcademyMainPage academyMainPage;
    private PracticeLoginPage practiceLoginPage;
    private PracticePage practicePage;
    private WebDriverWait explicitWait;


    private static Logger logs = LogManager.getLogger(LoginToPracticeTest.class.getName());

    @BeforeTest
    public void initDriver() throws IOException {
        driver = initializeDriver();
        explicitWait = new WebDriverWait(driver,10);
    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }

    @Test
    public void loginToPracticeSite_navigateToLoginPracticeSite_loginCorrectly() {
        driver.manage().deleteAllCookies();
        driver.get(properties.getProperty("url"));
        logs.info("page is loaded correctly");
        homePage = new pageObjects.HomePage(driver);
        homePage.closePopup();
        academyMainPage = homePage.clickInterviewButton();
        practiceLoginPage = academyMainPage.clickPracticeButton();
        practiceLoginPage.enterName(properties.getProperty("practiceSiteLoginName"));
        practiceLoginPage.enterEmail(properties.getProperty("practiceSiteLoginMail"));
        practicePage = practiceLoginPage.clickSubmitButton();
        // Had to change Files > Settings > Build,Execution, Deployment > Compiler > Java Compiler. change the Target Bytecode version to 1.8.
        explicitWait.until(ExpectedConditions.visibilityOf(practicePage.getPractice1()));
        Assert.assertEquals(practicePage.getTitleText(), "OUR PROJECTS");
    }


}
