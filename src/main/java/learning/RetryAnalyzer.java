package learning;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int counter = 0;
    int retrylimit = 2;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (counter < retrylimit) {
            counter++;
            return true;
        }
        return false;
    }
}
