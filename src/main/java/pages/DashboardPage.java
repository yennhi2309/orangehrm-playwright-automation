package pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import static objectRepository.BaseObjects.DYNAMIC_LINK_BY_TEXT;
import static objectRepository.DashboardPageObjects.*;
import static objectRepository.LoginPageObjects.BUTTON_LOGIN;
import utils.ScreenshotHelper;

public class DashboardPage extends BasePage{
    public DashboardPage(Page page) {
        super(page);
    }

    @Step("NAVIGATE TO MODULE {moduleName}")
    public void navigateToModule(String moduleName){
        getElement(getDynamicLocator(DYNAMIC_LINK_MODULE, moduleName)).waitFor();
        click(getDynamicLocator(DYNAMIC_LINK_MODULE, moduleName));
        ScreenshotHelper.takeScreenshot(page);
    }

    public void getPage(String pageName){
        click(getDynamicLocator(DYNAMIC_LINK_MODULE, pageName));
    }

    @Step("LOG OUT")
    public void logout(){
        click(DDL_USER);
        click(getDynamicLocator(DYNAMIC_LINK_BY_TEXT, "Logout"));
        waitForSelector(BUTTON_LOGIN, new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
    }
}
