package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class HomePage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(HomePage.class.getName());

    @FindBy(css="a[href*='sign_in']")
    private WebElement signInButton;

    @FindBy(css="div[class*='umome-react-wysiwyg-close']")
    private WebElement closePopupButton;

    @FindBy(css="div[class*='sumome-react-wysiwyg-is-resizing']")
    private List<WebElement> findPopupElements;

    @FindBy(css=".nav.navbar-nav.navbar-right>li>a")
    private WebElement homeInNavigationBar;

    @FindBy(xpath="//h2[contains(text(),'Featured Courses')]")
    private WebElement title;

    @FindBy(css = "ul[class*='navbar-right'] > li:nth-child(4) > a")
    private WebElement interviewButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        try {
            Assert.assertEquals(driver.getTitle(), "QA Click Academy | Selenium,Jmeter,SoapUI,Appium,Database testing,QA Training Academy");
        } catch (Throwable throwable) {
            logs.error("bad page loaded \n" + throwable);
            Assert.fail();
        }
    }

    public LogingPage clickSignInButton() {
        signInButton.click();
        return new LogingPage(driver);

    }

    // Popup usually shows after 6-7 second, but adding this for handle when popup shows immediately
    public void closePopup() {
        if (findPopupElements.size() > 0) {
            closePopupButton.click();
        }
    }

    public WebElement getHomeInNavigationBar() {
        return homeInNavigationBar;
    }

    public String getTitleText() {
        return title.getText();
    }

    public AcademyMainPage clickInterviewButton() {
        interviewButton.click();
        return new AcademyMainPage(driver);
    }
}
