package utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {
    private static int screenshotIndex = 0;
    private static final String MODULE = "PIM";
    public static void reset() {
        screenshotIndex = 0;
    }

    private static String getScreenshotDir() {
        return "target/screenshots/" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
    public static void takeScreenshot(Page page, ITestResult result) { //ITestResult: Chứa thông tin test (tên test, description, status...).
        if (result == null) return;
        screenshotIndex++;
        String testSuite = result.getTestClass().getName();
        String testDesc = result.getMethod().getDescription();

        String tcIndex = "TC00";
        if (testDesc != null && testDesc.contains("TC")) {
            tcIndex = testDesc.split(" ")[0]; // TC01
        }

        String fileName = String.format("%s_%s_%s_SS%02d.png", MODULE, testSuite, tcIndex, screenshotIndex).replaceAll("\\s+", "_");

        boolean saveToDisk = Boolean.parseBoolean(ConfigReader.getProperty("saveToDisk"));
        Page.ScreenshotOptions options = new Page.ScreenshotOptions().setFullPage(true);
        Path filePath = null;
        if (saveToDisk) {
            Path dir = Paths.get(getScreenshotDir());
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            filePath = dir.resolve(fileName);
            options.setPath(filePath);
            System.out.println("[ScreenshotHelper] Saved to disk: " + filePath.toAbsolutePath());
        }

        byte[] image = page.screenshot(options);
        attachToAllure(image, fileName);
    }
    @Attachment(value = "{fileName}", type = "image/png")
    private static byte[] attachToAllure(byte[] image, String fileName) {
        return image;
    }

    public static void takeScreenshot(Page page) {
        // Lấy ITestResult của test đang thực thi từ TestNG Reporter
        ITestResult result = org.testng.Reporter.getCurrentTestResult();
        takeScreenshot(page, result);
    }
}
