package tests.data;

import java.util.Map;

public class EmployeeData {
    private EmployeeData(){}

    private static final Map<String, String> DOMAIN_TO_TABLE_HEADER = Map.of(
            "firstName", "First (& Middle) Name",
            "middleName", "First (& Middle) Name",
            "lastName", "Last Name",
            "employeeId", "Id"
    );

    public static final Map<String, String> basicInfo = Map.of(
            "firstName", "haha",
            "middleName", "huhu",
            "lastName", "hihi"
    );

    public static final Map<String, String> loginDetails = Map.of(
            "Username", "nguyenvana",
            "Password", "Admin@123!",
            "Confirm Password", "Admin@123!"
    );
//    public static final String IMG_AVATAR = "C:\\Users\\intern.ntynhi1\\Pictures\\Screenshots\\TestImageRobot.png";
    public static final String IMG_AVATAR = "C:\\Users\\PC\\Pictures\\Screenshots\\Screenshot 2025-12-29 010343.png";
    public static final Map<String, String> inputPersonalDetails = Map.of(
//            "Other Id", "123450",
            "License Expiry Date", "2000-12-12"
//            "Test_Field", "gender"
    );
    public static final Map<String, String> ddlPersonalDetails = Map.of(
            "Nationality", "Indian",
            "Marital Status", "Single"
//            "Blood Type", "A+"
    );

    public static final Map<String, String> inputContactDetails = Map.of(
            "Street 1", "haha",
            "Street 2", "haha",
            "City", "haha",
            "State/Province", "haha",
            "Zip/Postal Code", "haha",
            "Home", "12345",
            "Mobile", "12345",
            "Work", "12345",
            "Work Email", "haha1@gmail.com",
            "Other Email", "haha2@gmail.com"
    );
    public static final Map<String, String> ddlContactDetails = Map.of(
            "Country", "India"
    );
    public static final String gender = "Male";
}
