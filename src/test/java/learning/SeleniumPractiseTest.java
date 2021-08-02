package learning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumPractiseTest extends Base{
    public WebDriver driver;
    private HomePage homePage;
    private AcademyMainPage academyMainPage;
    private PracticeLoginPage practiceLoginPage;
    private PracticePage practicePage;
    private SeleniumPracticePage seleniumPracticePage;
    private WebDriverWait explicitWait;

    private static Logger logs = LogManager.getLogger(SeleniumPractiseTest.class.getName());
    @BeforeTest
    public void initDriverAndNavigateToPracticePage() throws IOException {
        driver = initializeDriver();
        explicitWait = new WebDriverWait(driver,10);
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
        seleniumPracticePage = practicePage.clickAutomationPractice1Button();

    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }

    @BeforeMethod
    public void refreshSite() throws InterruptedException {
        driver.navigate().refresh();
        Thread.sleep(1000);
    }

    @Test(dataProvider = "getProductsAndQuantity", priority = 1)
    public void AddItemsToCart(String[] productNameList, int[] productQuantityList) throws InterruptedException {
        int finalPrice = 0;
        List<String> uniqueProductNameList = Arrays.stream(productNameList).distinct().collect(Collectors.toList());

        // Add items - dataFromProductsPage: / name of product / quantity / position / price /
        Object[][] dataFromProductsPage = seleniumPracticePage.addItemsToCart(driver, productNameList, productQuantityList, productNameList.length);

        // check all products are found
        Assert.assertNotSame("Some Product/s not found: ", dataFromProductsPage[0][0], "Can't find Product/s : " + dataFromProductsPage[0][1].toString() + ".");

        // let price on site finish adding
        Thread.sleep(3000);

        // Check items added number
        try {
            Assert.assertEquals(seleniumPracticePage.getItemsAddedCount().getText(), String.valueOf(uniqueProductNameList.size()));
        } catch (Throwable throwable) {
            logs.error("Items count in top-right corner not match" + "\n " + throwable);
            Assert.fail();
        }

        // Check final price for all items
        for (int i = 0; i < uniqueProductNameList.size(); i++) {
            finalPrice = finalPrice + ((int) dataFromProductsPage[i][1] * (int) dataFromProductsPage[i][3]);
        }
        try {
            Assert.assertEquals(seleniumPracticePage.getTotalPriceForAllItems().getText(), String.valueOf(finalPrice));
        } catch (Throwable throwable) {
            logs.error("Items Total Price not match" + "\n " + throwable);
            Assert.fail();
        }

        seleniumPracticePage.clickCartButton();
        Object[][] dataFromCart = seleniumPracticePage.checkCartItems(driver);
        Thread.sleep(1000);

        // compare count of products that should be added to count of products in cart
        try {
            Assert.assertEquals(dataFromCart.length, uniqueProductNameList.size());
        } catch (Throwable throwable) {
            logs.error("Products count in Cart not match the count of products that should be added");
            Assert.fail();
        }

        //check: /name of products /quantities / price per 1 / totalPrice in Cart
        for (int i = 0; i < uniqueProductNameList.size(); i++) {
            // name of products
            try {
                Assert.assertEquals(dataFromCart[i][0], dataFromProductsPage[i][0]);
            } catch (Throwable throwable) {
                logs.error("Wrong Product name in Cart" + "\n " + throwable);
                Assert.fail();
            }
            // quantities
            try {
                Assert.assertEquals(dataFromCart[i][1], dataFromProductsPage[i][1]);
            } catch (Throwable throwable) {
                logs.error("Wrong quantity in Cart for Product: " + dataFromCart[i][0] + "\n " + throwable);
                Assert.fail();
            }

            //price per 1
            try {
                Assert.assertEquals(dataFromCart[i][2], dataFromProductsPage[i][3]);
            } catch (Throwable throwable) {
                logs.error("Wrong price in Cart for Product: " + dataFromCart[i][0] + "\n " + throwable);
                Assert.fail();
            }

            // total price per product
            try {
                Assert.assertEquals((int) dataFromProductsPage[i][1] * (int) dataFromProductsPage[i][3], dataFromCart[i][3]);
            } catch (Throwable throwable) {
                logs.error("Wrong total price in Cart for Product: " + dataFromCart[i][0] + "\n " + throwable);
                Assert.fail();
            }
        }


    }

    @DataProvider
    public Object[][] getProductsAndQuantity() {

        Object[][] data = new Object[4][2];
        data[0][0] = new String[] {"Brocolli", "Beans", "Brocolli"};
        data[0][1] = new int[] {2, 3, 6};

        data[1][0] = new String[] {"Brocolli", "Cucumber", "Beans", "Tomato", "Pumpkin"};
        data[1][1] = new int[] {2, 3, 6, 2, 1};

        data[2][0] = new String[] {"Brocolli", "Cucumber", "Beans", "Tomato", "Pumpkin", "Mushroom", "Onion"};
        data[2][1] = new int[] {2, 3, 6, 1, 4, 3, 7};

        data[3][0] = new String[] {"Brocolli", "Cucumber", "Brocolli", "Cucumber", "Brocolli", "Mushroom", "Potato", "Banana", "Apple"};
        data[3][1] = new int[] {5, 2, 5, 2, 5, 3, 7, 8, 2};

        return data;
    }
}
