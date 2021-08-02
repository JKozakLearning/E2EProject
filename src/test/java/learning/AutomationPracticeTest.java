//////  Tests not Ready!!! TODO


package learning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pageObjects.*;

import java.io.IOException;

public class AutomationPracticeTest extends Base {
    public WebDriver driver;
    private HomePage homePage;
    private AcademyMainPage academyMainPage;
    private PracticeLoginPage practiceLoginPage;
    private PracticePage practicePage;
    private AutomationPracticePage automationPracticePage;
    private WebDriverWait explicitWait;


    private static Logger logs = LogManager.getLogger(AutomationPracticeTest.class.getName());

    @BeforeTest
    public void initDriverAndNavigateToPracticePage() throws IOException {
        driver = initializeDriver();
        explicitWait = new WebDriverWait(driver, 10);
        logs.info("driver is initialized");
        driver.manage().deleteAllCookies();
        driver.get(properties.getProperty("url"));
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
        automationPracticePage = practicePage.clickAutomationPractice2Button();
    }

    @BeforeMethod
    public void refreshSite() {
        driver.navigate().refresh();

    }
}
