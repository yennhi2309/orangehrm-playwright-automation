package listeners;

import tests.BaseTest;
import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotHelper;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();

        if (testClass instanceof BaseTest) {
            Page page = ((BaseTest) testClass).getPage();
            ScreenshotHelper.takeScreenshot(page, result);
        }
    }
}
