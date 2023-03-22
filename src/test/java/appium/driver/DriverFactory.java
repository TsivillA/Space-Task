package appium.driver;

import com.codeborne.selenide.WebDriverProvider;
import appium.config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Class for initialization of AndroidDriver
 */
public class DriverFactory implements WebDriverProvider {

    protected static AndroidDriver driver;
    public static URL getUrl() {
        try {
            return new URL(ConfigReader.emulatorConfig.remoteURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", ConfigReader.emulatorConfig.platformName());
        desiredCapabilities.setCapability("platformVersion", ConfigReader.emulatorConfig.platformVersion());
        desiredCapabilities.setCapability("deviceName", ConfigReader.emulatorConfig.deviceName());
        desiredCapabilities.setCapability("automationName", ConfigReader.emulatorConfig.automationName());
        desiredCapabilities.setCapability("appPackage", ConfigReader.emulatorConfig.appPackage());
        desiredCapabilities.setCapability("appActivity", ConfigReader.emulatorConfig.appActivity());
        desiredCapabilities.setCapability("app", ConfigReader.emulatorConfig.app());
        desiredCapabilities.setCapability("autoGrantPermissions", ConfigReader.emulatorConfig.autoGrantPermissions());

        capabilities = desiredCapabilities;

        driver = new AndroidDriver(getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
