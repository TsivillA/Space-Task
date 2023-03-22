package appium.pages;

import appium.driver.DriverHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage{
    private SelenideElement searchButton = $(AppiumBy.accessibilityId("Search"));
    private SelenideElement searchBar = $(AppiumBy.id("com.reddit.frontpage:id/search"));

    @Step("Searching for Banking")
    public void searchFor(String query) {

        searchButton.should(Condition.visible);
        searchButton.click();
        searchBar.should(Condition.visible);
        searchBar.click();
        DriverHelper.sendKeysAndFind(searchBar, query);
    }
}
