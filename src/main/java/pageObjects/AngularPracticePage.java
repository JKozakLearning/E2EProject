//////  not Ready!!! TODO


package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AngularPracticePage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(AngularPracticePage.class.getName());

    public AngularPracticePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/angularpractice/");
    }
}
