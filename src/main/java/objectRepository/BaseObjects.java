package objectRepository;

public class BaseObjects {
    // Common locators across all pages
    public static final String LOADING_SPINNER = "//div[contains(@class, 'loading-spinner-container')]";
    public static final String ERROR_MESSAGE = "//*[contains(@class, 'error-message')]";
    public static final String LABEL_OF_ERROR_MESSAGE = "//*[contains(@class, 'error-message')]/preceding-sibling::div/label";
    public static final String DYNAMIC_SUCCESS_MESSAGE = "//div[contains(@class,'oxd-toast-container')]/descendant::p[contains(@class,'oxd-toast-content-text') and contains(normalize-space(.),'%s')]";

    // Dynamic locator templates
    public static final String DYNAMIC_BUTTON_BY_TEXT = "//button[normalize-space()='%s']";
    public static final String DYNAMIC_LINK_BY_TEXT = "a:has-text('%s')";
    public static final String DYNAMIC_INPUT_BY_NAME = "input[name='%s']";

}
