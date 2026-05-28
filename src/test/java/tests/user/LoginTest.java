package tests.user;

import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import tests.BaseTest;
import utils.ConfigReader;
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void setUp(){
        loginPage = new LoginPage(page);
        loginPage.navigateTo(ConfigReader.getLoginPageUrl());
    }

//    @Test
//    public void login_successfully_with_valid_credentials() {
//        loginPage.login(ConfigReader.getAdminUser(), ConfigReader.getAdminPassword());
//
//        assertThat(loginPage.isDashboardDisplayed())
//                .as("Dashboard should be visible after login")
//                .isTrue();
//    }

//    @Test
//    public void login_fail_with_wrong_password() {
//        loginPage.login("Admin", "wrongpass");
//        assertThat(loginPage.isInvalidCredentialErrorDisplayed()).isTrue();
//        assertThat(loginPage.getInvalidCredentialMessage()).contains("Invalid credentials");
//    }
//
//    @Test
//    public void login_fail_when_fields_are_empty() {
//        loginPage.clickLogin();
//        assertThat(loginPage.isRequiredErrorDisplayed()).isTrue();
//    }
}
