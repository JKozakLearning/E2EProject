package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PracticePage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(PracticePage.class.getName());

    @FindBy(css = ".container > h5")
    private WebElement title;



    @FindBy(css = "a[href*='seleniumPractise']")
    private WebElement automationPractice1Button;

    @FindBy(css = "a[href*='AutomationPractice']")
    private WebElement automationPractice2Button;

    @FindBy(css = "a[href*='angularpractice']")
    private WebElement automationPractice3Button;

    public PracticePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rahulshettyacademy.com/#/practice-project");
    }

    public String getTitleText() {
        return title.getText();
    }

    public WebElement getPractice1() {
        return automationPractice1Button;
    }

    public WebElement getPractice2() {
        return automationPractice2Button;
    }

    public WebElement getPractice3() {
        return automationPractice3Button;
    }

    public SeleniumPracticePage clickAutomationPractice1Button() {
        automationPractice1Button.click();
        return new SeleniumPracticePage(driver);
    }

    public AutomationPracticePage clickAutomationPractice2Button() {
        automationPractice2Button.click();
        return new AutomationPracticePage(driver);
    }

    public AngularPracticePage clickAutomationPractice3Button() {
        automationPractice2Button.click();
        return new AngularPracticePage(driver);
    }
}
