package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PracticeLoginPage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(PracticeLoginPage.class.getName());

    @FindBy(css = "#name")
    private WebElement name;

    @FindBy(css = "#email")
    private WebElement email;

    @FindBy(css = "#form-submit")
    private  WebElement submitButton;

    public PracticeLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rahulshettyacademy.com/#/practice-project");
    }

    public void enterName(String nameTyped) {
        name.sendKeys(nameTyped);
    }

    public void enterEmail(String emailTyped) {
        email.sendKeys(emailTyped);
    }

    public PracticePage clickSubmitButton() {
        submitButton.click();
        return new PracticePage(driver);
    }


}
