package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import io.qameta.allure.Step;
import utils.ScreenshotHelper;

import static objectRepository.LoginPageObjects.*;

public class LoginPage extends BasePage{
    public LoginPage(Page page) {
        super(page);
    }

    @Step("NAVIGATE TO LOGIN PAGE")
    public void navigateTo(String url) {
        page.navigate(url);
        page.locator(BUTTON_LOGIN).waitFor();
    }

    // Basic actions
    public void enterUsername(String username) {
        fill(getDynamicLocator(DYNAMIC_INPUT_BY_NAME, "username"), username);
    }

    public void enterPassword(String password) {
        fill(getDynamicLocator(DYNAMIC_INPUT_BY_NAME, "password"), password);
    }

    public void clickLogin() {
        click(BUTTON_LOGIN);
    }

    public void clickForgotPassword() {
        click(LINK_FORGOT_PASSWORD);
    }

    @Step("LOGIN WITH USERNAME: {0} | PASSWORD: {1}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        ScreenshotHelper.takeScreenshot(page);
        clickLogin();
        System.out.println("LOGIN WITH USERNAME: " + username + "| PASSWORD: " + password);
    }

    @Step("LOGIN SUCCESS")
    public boolean isDashboardDisplayed() {
        page.waitForSelector(DASHBOARD_HEADER);
        ScreenshotHelper.takeScreenshot(page);
        return isVisible(DASHBOARD_HEADER);
    }

    @Step("INVALID CREDENTIALS")
    public boolean isInvalidCredentialErrorDisplayed() {
        page.waitForSelector(INVALID_CREDENTIALS);
        ScreenshotHelper.takeScreenshot(page);
        return isVisible(INVALID_CREDENTIALS);
    }

    @Step("REQUIRED ERROR")
    public boolean isRequiredErrorDisplayed() {
        page.waitForSelector(ERROR_MESSAGE);
        ScreenshotHelper.takeScreenshot(page);
        return getElement(ERROR_MESSAGE).count() != 0;
    }

    public String getInvalidCredentialMessage() {
        return getText(INVALID_CREDENTIALS);
    }
}
