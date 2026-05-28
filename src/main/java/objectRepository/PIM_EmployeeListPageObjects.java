package objectRepository;

public class PIM_EmployeeListPageObjects extends BaseObjects {
    public static final String IMG_EDIT_EMPLOYEE = ".orangehrm-edit-employee-image>.employee-image";
    public static final String IMG_EMPLOYEE_PICTURE= ".orangehrm-employee-picture .employee-image";
    public static final String BUTTON_ADD_IMG = ".employee-image-action";
    public static final String INPUT_IMAGE = "input[type='file']";
    public static final String BUTTON_SUBMIT = "button[type='submit']";

    public static final String DYNAMIC_INPUT_FIELD_BY_LABEL = "//label[normalize-space()='%s']/ancestor::div[contains(@class,'oxd-input-group')]//input[not(@disabled)]";
//    public static final String DYNAMIC_INPUT_FIELD_BY_LABEL = "//div[contains(@class, 'input-field') and normalize-space()=\"%s\"]//input";
    public static final String DYNAMIC_OPT_BY_TEXT = "//div[@role='listbox']/div[@role='option' and normalize-space(.)='%s']";
    public static final String DYNAMIC_DROPDOWN_BY_LABEL = "//div[contains(@class, 'input-field') and contains(.,'%s')]//div[@class='oxd-select-wrapper']";
    public static final String DYNAMIC_GENDER = "//span[contains(@class,'oxd-radio-input') and ancestor::label[normalize-space()='%s']]";
    public static final String DYNAMIC_TITLE = "//*[contains(@class, 'orangehrm-main-title') and contains(., '%s')]";
    public static final String DYNAMIC_BUTTON_SAVE_BY_TITLE = DYNAMIC_TITLE+"/parent::div//button[@type='submit']";
}
