package appium.config;

import org.aeonbits.owner.Config;

/**
 * Reading keys from emulator.properties
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties", //reading env
        "file:src/test/resources/configs/emulator.properties", //reading from file
})
public interface EmulatorConfig extends Config{

    @Key("platformName")
    String platformName();

    @Key("platformVersion")
    String platformVersion();

    @Key("deviceName")
    String deviceName();

    @Key("automationName")
    String automationName();

    @Key("appPackage")
    String appPackage();

    @Key("appActivity")
    String appActivity();

    @Key("app")
    String app();

    @Key("autoGrantPermissions")
    String autoGrantPermissions();

    @Key("remoteURL")
    String remoteURL();

    @Key("appiumNode")
    String appiumNode();
}
