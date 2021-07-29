package learning;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {
    public WebDriver driver;
    public Properties properties;

    public WebDriver initializeDriver() throws IOException {
        properties = new Properties();
        FileInputStream propertiesFile = new FileInputStream("src/main/resources/data.properties");
        properties.load(propertiesFile);
        //mvn test -Dbrowser=chrome
//        String browserName = System.getProperty("browser");
        String browserName = properties.getProperty("browser");

        if (browserName.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            if (browserName.contains("headless")) {
                chromeOptions.addArguments("headless");
            }
            driver = new ChromeDriver(chromeOptions);


        } else if (browserName.equals("firefox")){
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver();
        }

        driver.manage().window().setSize(new Dimension(1680,1050));
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        driver.manage().deleteAllCookies();

        return driver;
    }

    public String[] getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {
        Date oDate = new Date();
        SimpleDateFormat oSDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String sDate = oSDF.format(oDate);
        TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String testCaseNameWithDate = testCaseName + "_" + sDate + ".png";
        String destinationFile = "./reports/" + testCaseNameWithDate;
        FileUtils.copyFile(source,new File(destinationFile));

        String[] screenshotProperties = {destinationFile, testCaseNameWithDate};
        return screenshotProperties;

    }

//    public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {
//
//        TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
//        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
//        String destinationFile = "./reports/" + testCaseName + ".png";
//
//        FileUtils.copyFile(source,new File(destinationFile));
//
//        return destinationFile;
//
//    }
}
