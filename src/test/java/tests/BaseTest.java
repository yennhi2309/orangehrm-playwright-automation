package tests;

import com.microsoft.playwright.*;
import utils.ConfigReader;
import utils.ScreenshotHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseTest {
    protected static Page page;
    private static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    private ITestResult testResult;

    @BeforeMethod
    public void setUp(ITestResult result){
        playwright = Playwright.create();
        boolean headed = Boolean.parseBoolean(ConfigReader.getProperty("headed")); //Default headed = "false"
        int slowMo = Integer.parseInt(ConfigReader.getProperty("slowMo"));
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(!headed) //false = Display Browser, true = Don't Display (Default)
                        .setArgs(List.of("--start-maximized"))
                        .setSlowMo(slowMo) //Speed(ms)
        );

        Browser.NewContextOptions options = new Browser.NewContextOptions().setViewportSize(null);
        context = browser.newContext(options);
        page = context.newPage();
        attachPlaywrightEventHooks(page);

        this.testResult = result;
        ScreenshotHelper.reset();
        login();
    }

    public static void login(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo(ConfigReader.getLoginPageUrl());
        loginPage.login(ConfigReader.getAdminUser(), ConfigReader.getAdminPassword());

        assertThat(loginPage.isDashboardDisplayed())
                .as("Dashboard should be visible after login")
                .isTrue();
    }

    private static void attachPlaywrightEventHooks(Page page) {

        // Log whenever Page navigate
        page.onFrameNavigated(frame -> {
            log.info("Navigated to URL: {}", frame.url());
        });

        // Log whenever request send
        page.onRequest(request -> {
            log.debug("Request: {} {}", request.method(), request.url());
        });

        // Log whenever response return
        page.onResponse(response -> {
            log.debug("Response: {} {}", response.status(), response.url());
        });

        // Log whenever dialog alert/confirm/prompt appear
        page.onDialog(dialog -> {
            log.warn("Dialog appeared: {}", dialog.message());
            dialog.dismiss();
        });
    }

    @AfterMethod
    public void closeBrowser(){
        context.close();
        browser.close();
        playwright.close();
    }

    public Page getPage() {
        return page;
    }
}
