package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LogingPage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(LogingPage.class.getName());

    @FindBy(id="user_email")
    private WebElement email;

    @FindBy(id="user_password")
    private WebElement password;

    @FindBy(css="input[name='commit']")
    private WebElement loginButton;

    @FindBy(css = "div[class*='alert-danger']")
    private WebElement invalidEmailOrPasswordAlert;

    public LogingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        try {
            Assert.assertEquals(driver.getTitle(), "WebServices Testing using SoapUI");
        } catch (Throwable throwable) {
            logs.error("bad page loaded \n" + throwable);
            Assert.fail();
        }
    }

   public void enterEmail(String emailTyped) {
        email.sendKeys(emailTyped);
   }

   public void enterPassword(String passwordTyped) {
        password.sendKeys(passwordTyped);
   }

   public void clickLoginButton() {
        loginButton.click();
   }

   public String getAlertText() {
        return invalidEmailOrPasswordAlert.getText();
   }

   public String getUrlAfterEmailAndPasswordPassed() {
        return driver.getCurrentUrl();
   }
}
