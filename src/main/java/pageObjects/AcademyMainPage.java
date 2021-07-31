package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AcademyMainPage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(HomePage.class.getName());

    @FindBy(css = "a[href*='practice-project']")
    private WebElement practiceButton;


    public AcademyMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rahulshettyacademy.com/#/index");
    }

    public PracticeLoginPage clickPracticeButton() {
        practiceButton.click();
        return new PracticeLoginPage(driver);
    }
}
