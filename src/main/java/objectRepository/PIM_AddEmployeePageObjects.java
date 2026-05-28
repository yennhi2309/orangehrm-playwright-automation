package objectRepository;

public class PIM_AddEmployeePageObjects extends BaseObjects{
    public static final String DUPLICATE_ERROR_MESSAGE = "//*[contains(@class, 'error-message') and string()=normalize-space('Employee Id already exists')]";
    public static final String CHECKBOX_CREATE_DETAILS = "//input[@type='checkbox']";
    public static final String TOGGLE_CREATE_LOGIN_DETAILS = "//span[contains(@class, 'oxd-switch-input')]";

    public static final String DYNAMIC_TEXTBOX_BY_LABEL = "//label[text()=normalize-space('%s')]/parent::div/following-sibling::div/input[@class='oxd-input oxd-input--active']";
    public static final String DYNAMIC_STATUS = "//label[string()='%s']/input[@type='radio']";
    public static final String DYNAMIC_TITLE = "//*[contains(@class, 'orangehrm-main-title') and contains(., '%s')]";
    public static final String DYNAMIC_BUTTON_SAVE_BY_TITLE = DYNAMIC_TITLE+"/parent::div//button[@type='submit']";

}
