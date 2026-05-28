package objectRepository;

public class LoginPageObjects extends BaseObjects{
    // Static locators
    public static final String BUTTON_LOGIN = "button[type='submit']";
    public static final String LINK_FORGOT_PASSWORD = "div.orangehrm-login-forgot > p";

    // Dynamic locators
    public static final String DYNAMIC_INPUT_BY_NAME = "input[name='%s']";

    // Validation messages
    public static final String INVALID_CREDENTIALS = "div[role='alert']";
    public static final String DASHBOARD_HEADER = "h6:has-text('Dashboard')";
}
