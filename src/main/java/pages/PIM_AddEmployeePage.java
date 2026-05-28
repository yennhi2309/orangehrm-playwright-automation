package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotHelper;

import java.util.List;
import java.util.Map;

import static objectRepository.PIM_AddEmployeePageObjects.*;

public class PIM_AddEmployeePage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(PIM_AddEmployeePage.class);

    public PIM_AddEmployeePage(Page page) {
        super(page);
    }

    @Step("CLICK TO CREATE DETAILS")
    public void clickToCreateDetails(){
        isVisible(TOGGLE_CREATE_LOGIN_DETAILS);
        click(TOGGLE_CREATE_LOGIN_DETAILS);
//        click(CHECKBOX_CREATE_DETAILS); //intercepted
        waitForSelector(getDynamicLocator(DYNAMIC_TEXTBOX_BY_LABEL, "Username"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
    }
    private void fillFields(Map<String, String> fieldMap, String locatorTemplate) {
        fieldMap.forEach((label, value) -> {
            fill(getDynamicLocator(locatorTemplate, label), value);
        });
    }

    public void fillBasicInfo(Map<String, String> basicInfo) {
        fillFields(basicInfo, DYNAMIC_INPUT_BY_NAME);
    }

    public void fillLoginDetails(Map<String, String> loginDetails) {
        fillFields(loginDetails, DYNAMIC_TEXTBOX_BY_LABEL);
    }

    @Step("CHECK ENABLE STATUS")
    public void checkEnableStatus(){
        Locator enabledCheckbox = getElement(getDynamicLocator(DYNAMIC_STATUS, "Enabled"));
        if(!enabledCheckbox.isChecked()){
            enabledCheckbox.check();
        }
    }
    public List<String> getLabelError(){
        return getElement(LABEL_OF_ERROR_MESSAGE).allTextContents();
    }
    private String setEmployeeId() {
        Locator inputEmployeeId = getElement(getDynamicLocator(DYNAMIC_TEXTBOX_BY_LABEL, "Employee Id"));
        String currentValue = inputEmployeeId.inputValue();
        if (currentValue == null || currentValue.isEmpty()) {
            log.error("Employee Id field is empty, using timestamp as fallback");
        }
        String employeeId = "EMP-" + inputEmployeeId.inputValue();
        inputEmployeeId.fill(employeeId);
        log.info("Generated Employee Id: " + employeeId);
        return employeeId;
    }

    @Step("ADD EMPLOYEE")
    public String addEmployee(Map<String, String> basicInfo, Map<String, String> loginDetails){
        fillBasicInfo(basicInfo);
        String employeeId = setEmployeeId();
        log.info("Employee Id: " + employeeId);
        clickToCreateDetails();
        fillLoginDetails(loginDetails);
        checkEnableStatus();
        if(isRequiredErrorDisplayed()) {
            log.error("Can't add employee");
        }
        click(getDynamicLocator(DYNAMIC_BUTTON_SAVE_BY_TITLE, "Add Employee"));
        waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
        waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        waitForLoadingComplete(LOADING_SPINNER);
        return employeeId;
    }
    public boolean isRequiredErrorDisplayed() {
        return isVisible(ERROR_MESSAGE);
    }

}
