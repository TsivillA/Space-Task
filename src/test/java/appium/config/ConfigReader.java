package appium.config;

import org.aeonbits.owner.ConfigFactory;

/**
 * Class for reading .properties files
 */
public class ConfigReader {

    /**
     * Reader for emulator.properties
     */
    public static final EmulatorConfig emulatorConfig = ConfigFactory.create(EmulatorConfig.class, System.getProperties());

    /**
     * Reader test.properties
     */
    public static final TestConfig testConfig = ConfigFactory.create(TestConfig.class, System.getProperties());
}
