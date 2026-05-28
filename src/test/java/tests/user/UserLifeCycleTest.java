package tests.user;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIM_AddEmployeePage;
import pages.PIM_EmployeeListPage;
import tests.BaseTest;
import tests.data.EmployeeData;
import utils.ConfigReader;
import utils.ScreenshotHelper;
import utils.TableUtils;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLifeCycleTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(UserLifeCycleTest.class);

    @Test(description = "")
    public void userLifeCycle() {
        LoginPage loginPage = new LoginPage(page);
        PIM_AddEmployeePage addEmployeePage = new PIM_AddEmployeePage(page);
        PIM_EmployeeListPage employeeListPage = new PIM_EmployeeListPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);

        log.info("===============NAVIGATE TO PIM PAGE===============");
        dashboardPage.navigateToModule("PIM");

        log.info("===============NAVIGATE TO ADD EMPLOYEE PAGE===============");
        dashboardPage.navigateToModule("Add Employee");

        log.info("===============ADD EMPLOYEE===============");
        String employeeId = addEmployeePage.addEmployee(EmployeeData.basicInfo, EmployeeData.loginDetails);

        log.info("===============VERIFY ALL FIELDS ARE SAVED CORRECTLY===============");
        assertThat(employeeListPage.verifyBasicFieldsAreSavedCorrectly(EmployeeData.basicInfo, employeeId)).isTrue();

        log.info("===============UPLOAD AVATAR===============");
        employeeListPage.uploadAvatar(EmployeeData.IMG_AVATAR);

        log.info("===============LOGOUT===============");
        dashboardPage.logout();

        log.info("===============LOGIN BY EMPLOYEE===============");
        loginPage.login(EmployeeData.loginDetails.get("Username"), EmployeeData.loginDetails.get("Password"));

        log.info("===============NAVIGATE TO MY INFO PAGE===============");
        dashboardPage.navigateToModule("My Info");

        log.info("===============GO TO PERSONAL DETAILS PAGE===============");
        employeeListPage.goToModule("Personal Details");

        log.info("===============FILL PERSONAL DETAILS===============");
        employeeListPage.fillPersonalDetails(EmployeeData.inputPersonalDetails, EmployeeData.ddlPersonalDetails, EmployeeData.gender);

        log.info("===============GO TO CONTACT DETAILS PAGE===============");
        employeeListPage.goToModule("Contact Details");

        log.info("===============FILL CONTACT DETAILS===============");
        employeeListPage.fillContactDetails(EmployeeData.inputContactDetails, EmployeeData.ddlContactDetails);

        log.info("===============LOGOUT===============");
        dashboardPage.logout();

        log.info("===============LOGIN BY ADMIN===============");
        loginPage.login(ConfigReader.getAdminUser(), ConfigReader.getAdminPassword());

        log.info("===============NAVIGATE TO PIM PAGE===============");
        dashboardPage.navigateToModule("PIM");

        log.info("===============SEARCH===============");
        employeeListPage.searchByEmployeeId(employeeId);
        TableUtils table = new TableUtils(page, "div.oxd-table");

        log.info("===============VERIFY USERNAME IS FILTERED CORRECTLY===============");
        List<String> values = table.getAllValuesInColumn("Id");
        log.info("Values: {}", values);
        assertThat(employeeListPage.verifyUsernameFilteredCorrectly(values, employeeId)).isTrue();

        log.info("===============CLICK ON EDIT===============");
        table.clickActionButtonByRowValues(Map.of("Id", employeeId), "Edit");

        log.info("===============GO TO PERSONAL DETAILS PAGE===============");
        employeeListPage.goToModule("Personal Details");

        log.info("===============VERIFY ALL PERSONAL DETAIL FIELDS ARE SAVED CORRECTLY===============");
        assertThat(employeeListPage.verifyPersonalDetailFieldsAreSavedCorrectly(EmployeeData.inputPersonalDetails, EmployeeData.ddlPersonalDetails, EmployeeData.gender)).isTrue();

        log.info("===============GO TO CONTACT DETAILS PAGE===============");
        employeeListPage.goToModule("Contact Details");

        log.info("===============VERIFY ALL CONTACT DETAIL FIELDS ARE SAVED CORRECTLY===============");
        assertThat(employeeListPage.verifyContactDetailFieldsAreSavedCorrectly(EmployeeData.inputContactDetails, EmployeeData.ddlContactDetails)).isTrue();

        log.info("===============NAVIGATE TO EMPLOYEE LIST PAGE===============");
        dashboardPage.navigateToModule("Employee List");

        log.info("===============SEARCH===============");
        employeeListPage.searchByEmployeeId(employeeId);

        log.info("===============DELETE===============");
        table.clickActionButtonByRowValues(Map.of("Id", employeeId), "Delete");

        log.info("===============CONFIRM DELETE===============");
        employeeListPage.deleteEmployee();

        log.info("===============VERIFY DELETE EMPLOYEE SUCCESSFULLY===============");
        employeeListPage.searchByEmployeeId(employeeId);
        assertThat(table.verifyRowsCountMatches(0)).isTrue();
        ScreenshotHelper.takeScreenshot(page);

        log.info("===============LOGOUT===============");
        dashboardPage.logout();

        log.info("===============LOGIN BY EMPLOYEE===============");
        loginPage.login(EmployeeData.loginDetails.get("Username"), EmployeeData.loginDetails.get("Password"));

        log.info("===============VERIFY LOGIN FAIL===============");
        assertThat(loginPage.isInvalidCredentialErrorDisplayed()).isTrue();

//        String emId = "EMP-0460";
//
//        log.info("===============NAVIGATE TO PIM PAGE===============");
//        dashboardPage.navigateToModule("PIM");
//
//        log.info("===============SEARCH===============");
//        employeeListPage.searchByEmployeeId(emId);
//        TableUtils table = new TableUtils(page, "div.oxd-table");
//
////        log.info("===============VERIFY USERNAME IS FILTERED CORRECTLY===============");
////        List<String> values = table.getAllValuesInColumn("Id");
////        log.info("Values: {}", values);
////        assertThat(employeeListPage.verifyUsernameFilteredCorrectly(values, "EMP-0373")).isTrue();
//
//        log.info("===============CLICK ON EDIT===============");
//        table.clickActionButtonByRowValues(Map.of("Id", emId), "Edit");
//
//        log.info("===============GO TO PERSONAL DETAILS PAGE===============");
//        employeeListPage.goToModule("Personal Details");
//
//        log.info("===============VERIFY ALL PERSONAL DETAIL FIELDS ARE SAVED CORRECTLY===============");
//        assertThat(employeeListPage.verifyPersonalDetailFieldsAreSavedCorrectly(EmployeeData.inputPersonalDetails, EmployeeData.ddlPersonalDetails, EmployeeData.gender)).isTrue();
//
//        log.info("===============GO TO CONTACT DETAILS PAGE===============");
//        employeeListPage.goToModule("Contact Details");
//
//        log.info("===============VERIFY ALL CONTACT DETAIL FIELDS ARE SAVED CORRECTLY===============");
//        assertThat(employeeListPage.verifyContactDetailFieldsAreSavedCorrectly(EmployeeData.inputContactDetails, EmployeeData.ddlContactDetails)).isTrue();
//
//        log.info("===============NAVIGATE TO EMPLOYEE LIST PAGE===============");
//        dashboardPage.navigateToModule("Employee List");
//
//        log.info("===============SEARCH===============");
//        employeeListPage.searchByEmployeeId(emId);
//
//        log.info("===============DELETE===============");
//        table.clickActionButtonByRowValues(Map.of("Id", emId), "Delete");
//
//        log.info("===============CONFIRM DELETE===============");
//        employeeListPage.deleteEmployee();
//
//        log.info("===============VERIFY DELETE EMPLOYEE SUCCESSFULLY===============");
//        employeeListPage.searchByEmployeeId(emId);
//        assertThat(table.verifyRowsCountMatches(0)).isTrue();
//        ScreenshotHelper.takeScreenshot(page);
//
//        log.info("===============LOGOUT===============");
//        dashboardPage.logout();
//
//        log.info("===============LOGIN BY EMPLOYEE===============");
//        loginPage.login(EmployeeData.loginDetails.get("Username"), EmployeeData.loginDetails.get("Password"));
//
//        log.info("===============VERIFY LOGIN FAIL===============");
//        assertThat(loginPage.isInvalidCredentialErrorDisplayed()).isTrue();
    }

}
