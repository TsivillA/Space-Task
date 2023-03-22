package appium.config;

import org.aeonbits.owner.Config;

/**
 * Reading keys from test.properties
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties", //reading env
        "file:src/test/resources/configs/test.properties", //reading from file
})
public interface TestConfig extends Config {
    @Key("updateScreenshots")
    @DefaultValue("false")
    boolean isScreenshotsNeedToUpdate();

    @Key("deviceHost")
    String deviceHost();
}
