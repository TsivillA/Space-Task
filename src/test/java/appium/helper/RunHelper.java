package appium.helper;

import appium.config.ConfigReader;
import appium.driver.DriverFactory;

public class RunHelper {

    private RunHelper() {
    }

    /**
     * Static constructor to call methods from a class without creating an instance
     * @return
     */
    public static RunHelper runHelper() {
        return new RunHelper();
    }

    /**
     * Implementing AndroidDriver through custom classes for each device variant
     * @return
     */
    public Class<?> getDriverClass() {
        String deviceHost = ConfigReader.testConfig.deviceHost();

        switch (deviceHost) {
            case "browserstack":
                //    return BrowserstackMobileDriver.class;
            case "selenoid":
                //   return SelenoidMobileDriver.class;
            case "emulator":
                return DriverFactory.class; //Class for session initialization for the emulator
            case "real":
                //    return RealMobileDriver.class; We can create this class and implement logic for real devices (additional fields are needed)
            default:
                throw new RuntimeException("There is no parameter in the config file deviceHost: " +
                        "browserstack/selenoid/emulator/real");
        }
    }
}
