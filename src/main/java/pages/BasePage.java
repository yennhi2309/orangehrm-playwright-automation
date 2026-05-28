package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected void click(String locator) {
        page.locator(locator).click();
    }

    protected void fill(String locator, String text) {
        page.locator(locator).fill(text);
    }

    protected void type(String locator, String text) {
        page.locator(locator).pressSequentially(text);
    }

    protected String getText(String locator) {
        return page.locator(locator).textContent();
    }

    protected boolean isVisible(String locator) {
        return page.locator(locator).isVisible();
    }

    protected boolean isEnabled(String locator) {
        return page.locator(locator).isEnabled();
    }

    protected void waitForSelector(String locator, Page.WaitForSelectorOptions waitForSelectorOptions) {
        page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    //Dynamic locator helper
    protected String getDynamicLocator(String dynamicXpath, String... dynamicValues) {
        return String.format(dynamicXpath, (Object[]) dynamicValues);
    }

    // Multiple elements
    protected int getElementCount(String locator) {
        return page.locator(locator).count();
    }

    protected Locator getElement(String locator) {
        return page.locator(locator);
    }

    protected void waitForLoadingComplete(String loadingLocator) {
        page.locator(loadingLocator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }
}
