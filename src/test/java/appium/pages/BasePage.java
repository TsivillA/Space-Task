package appium.pages;

import io.appium.java_client.AppiumBy;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;

/**
 * Base test class for screenshots
 */
public class BasePage {

    /**
     * Takes a screenshot of the screen without the statusbar
     * @return screenshot file
     */
    public File fullPageScreenshot(){
        return $(AppiumBy.id("composerRootCl")).screenshot();
    }
}
