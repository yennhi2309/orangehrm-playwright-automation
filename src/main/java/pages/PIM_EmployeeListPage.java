package pages;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotHelper;


import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static objectRepository.PIM_EmployeeListPageObjects.*;

public class PIM_EmployeeListPage extends BasePage{
    private static final Logger log = LoggerFactory.getLogger(PIM_EmployeeListPage.class);

    public PIM_EmployeeListPage(Page page) {
        super(page);
    }

    private boolean verifyBasicInputFields(Map<String, String> basicInfo) {
        return basicInfo.entrySet().stream()
                .allMatch(entry -> {
//                    String actual = getElement(getDynamicLocator(DYNAMIC_INPUT_BY_NAME, entry.getKey())).inputValue();
                    String selector = getDynamicLocator(DYNAMIC_INPUT_BY_NAME, entry.getKey());
                    Locator input = getElement(selector);
                    page.waitForCondition(() ->
                            !getElement(selector).inputValue().isEmpty()
                    );
                    boolean isMatch = input.inputValue().equals(entry.getValue());
                    if(!isMatch) {
                        log.error("Mismatch at label [{}]: Expected [{}], Actual [{}]",
                                entry.getKey(), entry.getValue(), input.inputValue());
                    }
                    return isMatch;
                });
    }

    private boolean verifyEmployeeId(String expectedEmployeeId) {
        String selector = getDynamicLocator(DYNAMIC_INPUT_FIELD_BY_LABEL, "Employee Id");
        page.waitForCondition(() ->
                !getElement(selector).inputValue().isEmpty()
        );
        String actual = getElement(selector).inputValue();
        log.info("Actual: {}", actual);
        return actual.equals(expectedEmployeeId);
    }

    @Step("VERIFY ALL FIELDS ARE SAVED CORRECTLY")
    public boolean verifyBasicFieldsAreSavedCorrectly(Map<String, String> basicInfo, String employeeId){
        boolean trueBasicInfo = verifyBasicInputFields(basicInfo);
        boolean trueEmployeeId = verifyEmployeeId(employeeId);
        return trueBasicInfo && trueEmployeeId;
    }

    @Step("UPLOAD AVATAR")
    public void uploadAvatar(String filePath){
        click(IMG_EDIT_EMPLOYEE);
        ScreenshotHelper.takeScreenshot(page);
        String beforeSrc = getElement(IMG_EMPLOYEE_PICTURE).getAttribute("src");
        FileChooser fileChooser = page.waitForFileChooser(() -> click(BUTTON_ADD_IMG));
        fileChooser.setFiles(Paths.get(filePath));
        ScreenshotHelper.takeScreenshot(page);
//        String afterSrc = getElement(IMG_EMPLOYEE_PICTURE).getAttribute("src");
//        if(!beforeSrc.equals(afterSrc)){
            click(getDynamicLocator(DYNAMIC_BUTTON_SAVE_BY_TITLE, "Change Profile Picture"));
            waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
            waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully Updated"), new Page.WaitForSelectorOptions().setTimeout(500));
            ScreenshotHelper.takeScreenshot(page);
            waitForLoadingComplete(LOADING_SPINNER);
//        } else {
//            log.info("Image not change");
//        }
    }

    @Step("GO TO MODULE {moduleName}")
    public void goToModule(String moduleName){
        click(getDynamicLocator(DYNAMIC_LINK_BY_TEXT, moduleName));
        ScreenshotHelper.takeScreenshot(page);
    }

    private void fillInputByLabel(String label, String value){
        waitForSelector(getDynamicLocator(DYNAMIC_INPUT_FIELD_BY_LABEL, label), new Page.WaitForSelectorOptions().setTimeout(500));
        fill(getDynamicLocator(DYNAMIC_INPUT_FIELD_BY_LABEL, label), value);
    }

    private void selectDropdown(String label, String value){
        click(getDynamicLocator(DYNAMIC_DROPDOWN_BY_LABEL, label));
        Locator opt = getElement(getDynamicLocator(DYNAMIC_OPT_BY_TEXT, value));
        opt.waitFor();
        opt.scrollIntoViewIfNeeded();
        opt.click();
    }

    private void selectGender(String gender){
        if(!gender.equals("Male") && !gender.equals("Female")){
            log.error("No gender name: " + gender);
        }
        click(getDynamicLocator(DYNAMIC_GENDER, gender));
    }

    @Step("FILL PERSONAL DETAILS")
    public void fillPersonalDetails(Map<String, String> inputByLabel, Map<String, String> dropdown, String gender){
        waitForSelector(getDynamicLocator(DYNAMIC_TITLE, "Personal Details"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        inputByLabel.forEach((label, value) -> {
            if (value != null && !value.isEmpty()) {
                fillInputByLabel(label, value);
            }
        });
        dropdown.forEach((label, value) -> {
            if (value != null && !value.isEmpty()) {
                selectDropdown(label, value);
            }
        });
        selectGender(gender);
        click(getDynamicLocator(DYNAMIC_BUTTON_SAVE_BY_TITLE, "Personal Details"));
        waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
        waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        waitForLoadingComplete(LOADING_SPINNER);
//        click(getDynamicLocator(DYNAMIC_BUTTON_SAVE_BY_TITLE, "Custom Fields"));
//        waitForSelector(LOADING_SPINNER);
//        waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully"));
//        waitForLoadingComplete(LOADING_SPINNER);
    }

    @Step("FILL CONTACT DETAILS")
    public void fillContactDetails(Map<String, String> inputByLabel, Map<String, String> dropdown){
        waitForSelector(getDynamicLocator(DYNAMIC_TITLE, "Contact Details"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        inputByLabel.forEach((label, value) -> {
            if (value != null && !value.isEmpty()) {
                fillInputByLabel(label, value);
            }
        });
        dropdown.forEach((label, value) -> {
            if (value != null && !value.isEmpty()) {
                selectDropdown(label, value);
            }
        });
        click(getDynamicLocator(DYNAMIC_BUTTON_SAVE_BY_TITLE, "Contact Details"));
        waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
        waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        waitForLoadingComplete(LOADING_SPINNER);
    }

    private boolean verifyInputFieldsAreSavedCorrectly(Map<String, String> inputByLabel) {
        boolean allMatched = true;
        for (Map.Entry<String, String> entry : inputByLabel.entrySet()) {
            String label = entry.getKey();
            String expected = entry.getValue();
            Locator field = getElement(getDynamicLocator(DYNAMIC_INPUT_FIELD_BY_LABEL, label));
            try {
                page.waitForCondition(() -> !field.inputValue().isEmpty(),
                        new Page.WaitForConditionOptions().setTimeout(5000));
            } catch (Exception e) {
                log.warn("Field [{}] is still empty after 5 seconds", label);
            }
            String actual = field.inputValue();
            if (!actual.equals(expected)) {
                log.error("[{}]: Expected [{}] | Actual [{}]", label, expected, actual);
                allMatched = false;
            }
        }
        return allMatched;
    }

    private boolean verifyDropdownAreSavedCorrectly(Map<String, String> dropdownByLabel){
        boolean allMatched = true;
        for (Map.Entry<String, String> entry : dropdownByLabel.entrySet()) {
            String label = entry.getKey();
            String expected = entry.getValue();
            Locator dropdownEl = getElement(getDynamicLocator(DYNAMIC_DROPDOWN_BY_LABEL, label)).locator(".oxd-select-text-input");
            try {
                page.waitForCondition(() ->
                    !dropdownEl.innerText().trim().equals("-- Select --"), new Page.WaitForConditionOptions().setTimeout(5000)
                );
            } catch (Exception e) {
                log.warn("Dropdown [{}] is still empty after 5 seconds", label);
            }
            String actual = dropdownEl.innerText().trim();
            if (!actual.equals(expected)) {
                log.error("[{}]: Expected [{}] | Actual [{}]", label, expected, actual);
                allMatched = false;
            }
        }

        return allMatched;
    }

    private boolean verifyGenderAreSavedCorrectly(String gender){
        return getElement(getDynamicLocator(DYNAMIC_GENDER, gender)).isChecked();
    }

    @Step("VERIFY PERSONAL DETAILS ARE SAVED CORRECTLY")
    public boolean verifyPersonalDetailFieldsAreSavedCorrectly(Map<String, String> inputByLabel, Map<String, String> dropdown, String gender){
        boolean trueInputFields = verifyInputFieldsAreSavedCorrectly(inputByLabel);
        boolean trueDropdownOpt = verifyDropdownAreSavedCorrectly(dropdown);
        boolean trueGender = verifyGenderAreSavedCorrectly(gender);
        return trueInputFields && trueDropdownOpt && trueGender;
    }

    @Step("VERIFY CONTACT DETAILS ARE SAVED CORRECTLY")
    public boolean verifyContactDetailFieldsAreSavedCorrectly(Map<String, String> inputByLabel, Map<String, String> dropdown){
        boolean trueInputFields = verifyInputFieldsAreSavedCorrectly(inputByLabel);
        boolean trueDropdownOpt = verifyDropdownAreSavedCorrectly(dropdown);
        return trueInputFields && trueDropdownOpt;
    }

    @Step("SEARCH EMPLOYEE BY ID")
    public void searchByEmployeeId(String employeeId){
        fill(getDynamicLocator(DYNAMIC_INPUT_FIELD_BY_LABEL, "Employee Id"), employeeId);
        click(getDynamicLocator(DYNAMIC_BUTTON_BY_TEXT, "Search"));
//        try {
//            waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
//        } catch (Exception e) {
//        }
//        waitForLoadingComplete(LOADING_SPINNER);
    }

    @Step("VERIFY USERNAME FILTERED CORRECTLY")
    public boolean verifyUsernameFilteredCorrectly(List<String> allColumnValues, String employeeId){
        ScreenshotHelper.takeScreenshot(page);
        for(String value:allColumnValues){
            if(!value.equals(employeeId)) return false;
        }
        return true;
    }
    @Step("CONFIRM DELETE EMPLOYEE")
    public void deleteEmployee(){
        ScreenshotHelper.takeScreenshot(page);
        click(getDynamicLocator(DYNAMIC_BUTTON_BY_TEXT, "Yes, Delete"));
        waitForSelector(LOADING_SPINNER, new Page.WaitForSelectorOptions().setTimeout(500));
        waitForSelector(getDynamicLocator(DYNAMIC_SUCCESS_MESSAGE, "Successfully"), new Page.WaitForSelectorOptions().setTimeout(500));
        ScreenshotHelper.takeScreenshot(page);
        waitForLoadingComplete(LOADING_SPINNER);
    }
}
