package appium.listeners;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

/**
 * Custom listener to override test completion logic
 */

@Listeners(AllureListener.class)
public class AllureListener implements ITestListener {
    /**
     * Method for adding a screenshot to the allure report via annotation
     * @param screenShot screenshot bytes
     * @return
     */

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    /**
     * redefining test completion logic for TestNG
     * @param result test result
     */
    @AfterMethod(alwaysRun = true)
    public void afterTestExecution(ITestResult result) {
        Method testMethod = result.getMethod().getConstructorOrMethod().getMethod(); //getting test method
        String testName = testMethod.getName(); //getting the name of the test method
        boolean testFailed = result.getStatus() == ITestResult.FAILURE;//checking if the test failed
        if (testFailed) { //in case test failed
            if (!testName.contains("Screenshot")) {//in case method name does not contain Screenshot
                saveScreenshot(Selenide.screenshot(OutputType.BYTES)); //adding a screenshot to the failed test
            }
        }
    }
}
