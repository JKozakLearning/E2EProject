package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumPracticePage {
    public WebDriver driver;
    private static Logger logs = LogManager.getLogger(SeleniumPracticePage.class.getName());


    @FindBy(css = "h4.product-name")
    private List<WebElement> productsList;

    @FindBy(xpath = "//div[@class='product-action']/button")
    private List<WebElement> addToCartButtonsList;

    @FindBy(css = ".products-wrapper p.product-price")
    private List<WebElement> productsPrice;

    @FindBy(css = ".products-wrapper .quantity")
    private List<WebElement> productsQuantityView;

    @FindBy(css = ".increment")
    private List<WebElement> incrementQuantityButton;

    @FindBy(css = ".decrement")
    private List<WebElement> decrementQuantityButton;

    @FindBy(css = ".cart-info > table > tbody > tr:nth-child(1) > td:nth-child(3)")
    private WebElement itemsAddedCount;

    @FindBy(css = ".cart-info > table > tbody > tr:nth-child(2) > td:nth-child(3)")
    private WebElement totalPriceForAllItems;

    @FindBy(css = "img[alt='Cart']")
    private WebElement cartButton;

    @FindBy(css = ".cart-preview.active .product-name")
    private List<WebElement> cartItemsName;

    @FindBy(css = ".cart-preview.active .quantity")
    private List<WebElement> cartItemsQuantity;

    @FindBy(css = ".cart-preview.active .product-price")
    private List<WebElement> cartItemsPrice;

    @FindBy(css = ".cart-preview.active .amount")
    private List<WebElement> cartItemsTotalPrice;

    @FindBy(css = ".cart-preview > div:nth-child(1) > div:nth-child(3) > div")
    private WebElement cartSlider;


    public SeleniumPracticePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/seleniumPractise/#/");
    }

    public WebElement getTotalPriceForAllItems() {
        return totalPriceForAllItems;
    }

    public WebElement getItemsAddedCount() {
        return itemsAddedCount;
    }

    public List<WebElement> getProductsList() {
        return productsList;
    }

    public List<WebElement> getAddToCartButtonsList() {
        return addToCartButtonsList;
    }

    public List<WebElement> getIncrementQuantityButton() {
        return incrementQuantityButton;
    }

    public void clickCartButton() {
        cartButton.click();
    }

    // assumes that the products from data : /can be added multiple times / product not present on page
    public Object[][] addItemsToCart(WebDriver driver, String[] itemsNeeded, int[] itemQuantity, int countOfItems) throws InterruptedException {
        WebDriverWait explicitWait = new WebDriverWait(driver, 5);
        Actions actions = new Actions(driver);
        List<String> itemsNeededList = Arrays.asList(itemsNeeded);
        List<String> itemsNotFoundList = new LinkedList<>(Arrays.asList(itemsNeeded));

        //create object with / product name / quantity / position / prices from products page
        Object[][] data = new Object[itemsNeeded.length][4];

        for (int i = 0; i < itemsNeeded.length; i++) {
            data[i][0] = itemsNeeded[i];
            data[i][1] = itemQuantity[i];

            // find position
            for (int j = 0; j < productsList.size(); j++) {
                String formattedName = productsList.get(j).getText().split("-")[0].trim();
                if (itemsNeeded[i].equals(formattedName)) {
                    //position
                    data[i][2] = j;
                    // price
                    data[i][3] = Integer.parseInt(productsPrice.get(j).getText());
                    itemsNotFoundList.remove(formattedName);
                    break;
                }
            }
        }

        // if some products not found - return
        if (itemsNotFoundList.size() > 0) {

            Object[][] productsNotFound = new Object[1][2];
            productsNotFound[0][0] = "Some Product/s not found: ";
            productsNotFound[0][1] = itemsNotFoundList;

            return productsNotFound;

        }

        //start adding items
        for (int i = 0; i < itemsNeeded.length; i++){
            int k = 1;
            // for avoid element not clickable
            actions.sendKeys(Keys.HOME).build().perform();
            Thread.sleep(200);

            // when product was found on page
            if (data[i][2] != null) {
                // if product was added recently - change quantity back to 1
                if (Integer.parseInt(productsQuantityView.get((Integer) data[i][2]).getAttribute("value")) > 1) {
                    productsQuantityView.get((Integer) data[i][2]).clear();
                    productsQuantityView.get((Integer) data[i][2]).sendKeys("1");
                }
                // change quantity
                while (k < (Integer) data[i][1]) {
                    incrementQuantityButton.get((Integer) data[i][2]).click();
                    k++;
                }
                //in case add to cart button would not refresh after previous click
                explicitWait.until(ExpectedConditions.visibilityOf(addToCartButtonsList.get((Integer) data[i][2])));
                // add item to cart
                addToCartButtonsList.get((Integer) data[i][2]).click();
                Thread.sleep(1000);
            }
        }

        // reformatting data if some products are present more than 1 time (for comparing with card data)
        List<String> formattedNeededList =itemsNeededList.stream().distinct().collect(Collectors.toList());
        if (itemsNeededList.size() > formattedNeededList.size()) {
            int sameProductsCount = itemsNeededList.size() - formattedNeededList.size();
            Object[][] newData = new Object[formattedNeededList.size()][4];
            for (int i = 0; i < formattedNeededList.size(); i++) {
                String nameFromFormattedList = formattedNeededList.get(i);
                // name of product
                newData[i][0] = nameFromFormattedList;
                // position
                newData[i][2] = data[itemsNeededList.indexOf(nameFromFormattedList)][2];
                // price
                newData[i][3] = data[itemsNeededList.indexOf(nameFromFormattedList)][3];

                //quantity check
                int totalQuantityForProduct = (int) data[itemsNeededList.indexOf(nameFromFormattedList)][1];

                for (int j = i + 1; j < itemsNeededList.size() && sameProductsCount > 0; j++) {
                    if (data[i][0].equals(data[j][0])) {
                        totalQuantityForProduct += (int) data[j][1];
                        sameProductsCount--;
                    }
                }
                //quantity
                newData[i][1] = totalQuantityForProduct;
            }
            return newData;

        } else {
            return data;
        }
    }

    // returns [ProductName, quantity, price, totalPrice] per row
    public Object[][] checkCartItems(WebDriver driver) throws InterruptedException {

        Actions actions = new Actions(driver);
        // some elements can not be visible if we don't move slider down
        if (cartSlider.isDisplayed()) {
            actions.moveToElement(cartSlider).clickAndHold().build().perform();
            actions.moveByOffset(0,400).build().perform();
            Thread.sleep(5000);
        }

        Object[][] dataFromCart = new Object[cartItemsName.size()][4];
        int productsAddedCount = 0;

        for (int i = 0; i < cartItemsName.size(); i++) {

            //name
            dataFromCart[i][0] = cartItemsName.get(i).getText().split("-")[0].trim();
            //quantity
            dataFromCart[i][1] = Integer.parseInt(cartItemsQuantity.get(i).getText().split(" ")[0]);
            //price per 1
            dataFromCart[i][2] = Integer.parseInt(cartItemsPrice.get(i).getText());
            //total price
            dataFromCart[i][3] = Integer.parseInt(cartItemsTotalPrice.get(i).getText());

        }

        return dataFromCart;
    }
}
