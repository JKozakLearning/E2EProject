package cucumberOptions;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features/Login.feature",
    glue = "stepDefinitions")
public class TestRunner extends AbstractTestNGCucumberTests {

}
