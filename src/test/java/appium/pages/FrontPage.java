package appium.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class FrontPage extends BasePage{
    private SelenideElement skipButton = $(AppiumBy.className("android.widget.Button"));

    @Step("Opening main page")
    public void openMainPage() {
        skipButton.should(Condition.visible);
        skipButton.click();
    }
}
