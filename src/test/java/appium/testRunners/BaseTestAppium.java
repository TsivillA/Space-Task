package appium.testRunners;

import appium.config.ConfigReader;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import appium.listeners.AllureListener;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static appium.helper.Constants.SCREENSHOT_TO_SAVE_FOLDER;
import static appium.helper.DeviceHelper.executeBash;
import static appium.helper.RunHelper.runHelper;
import static io.qameta.allure.Allure.step;


/**
 * Base test class
 */
@Listeners(AllureListener.class)
@CucumberOptions(
        glue = {"appium/stepDefinition"},
        features = {"src/test/resources/features/Reddit.feature"}
)
public class BaseTestAppium extends AbstractTestNGCucumberTests {
    public static AppiumDriverLocalService service;

    @BeforeSuite
    public static void setup() {
        //adding action logging for report gaits in the form of steps
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        // selenide screenshot folder
        Configuration.reportsFolder = SCREENSHOT_TO_SAVE_FOLDER;
        //initialize android driver
        Configuration.browser = runHelper().getDriverClass().getName();
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
        disableAnimationOnEmulator();
    }

    /**
     * Disabling animations on the emulator so that it does not lag
     */
    private static void disableAnimationOnEmulator() {
        executeBash("adb -s shell settings put global transition_animation_scale 0.0");
        executeBash("adb -s shell settings put global window_animation_scale 0.0");
        executeBash("adb -s shell settings put global animator_duration_scale 0.0");
    }

    /**
     * Checking a screenshot with a standard for checking the layout
     * @param actualScreenshot actual screenshot
     * @param expectedFileName comparison file name
     */
    public void assertScreenshot(File actualScreenshot, String expectedFileName) {
        //the name of the test method is always passed to the method, so we change the brackets to a file with an extension for further saving
        expectedFileName = expectedFileName.replace("()", ".png");
        //folder for storing reference screenshots
        String expectedScreensDir = "src/test/resources/expectedScreenshots/";
        //in case screenshots need to be updated
        if (ConfigReader.testConfig.isScreenshotsNeedToUpdate()) {
            try {
                //move the current screenshot to the folder with the standard and replace the file
                Files.move(actualScreenshot.toPath(), new File(expectedScreensDir + expectedFileName).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        //in case the screenshot needs to be compared
        // Uploading the expected image for comparison.
        BufferedImage expectedImage = ImageComparisonUtil
                .readImageFromResources(expectedScreensDir + expectedFileName);

        // Uploading the current screenshot.
        BufferedImage actualImage = ImageComparisonUtil
                .readImageFromResources(SCREENSHOT_TO_SAVE_FOLDER + actualScreenshot.getName());

        // Where will we store the screenshot with the differences in case the test fails.
        File resultDestination = new File("diff/diff_" + expectedFileName);

        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination)
                .compareImages();
        //in case the screenshots are different
        if (imageComparisonResult.getImageComparisonState().equals(ImageComparisonState.MISMATCH)) {
            try {
                //adding a screenshot with differences to the allure report in the form of a step
                byte[] diffImageBytes = Files.readAllBytes(resultDestination.toPath());
                AllureListener.saveScreenshot(diffImageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Assert.assertEquals(imageComparisonResult.getImageComparisonState(), ImageComparisonState.MATCH);
    }

    /**
     * Before each test, open the application
     */
    @BeforeMethod
    public void startDriver() {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(ConfigReader.emulatorConfig.appiumNode()))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
        step("Open application", (Allure.ThrowableRunnableVoid) Selenide::open);
    }

    /**
     * After each test, we close AndroidDriver so that the test is atomic
     */
    @AfterMethod
    public void afterEach() {
        step("Close application", Selenide::closeWebDriver);
        service.stop();
    }
}
