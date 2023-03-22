package appium.pages;

import appium.models.Post;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MostUpvotedPostPage extends BasePage{

    private SelenideElement user = $(AppiumBy.id("com.reddit.frontpage:id/bottom_row_metadata_before_indicators")),
                            timePosted = $(AppiumBy.id("com.reddit.frontpage:id/bottom_row_metadata_after_indicators"));
    private SelenideElement numberOfComments = $(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().resourceIdMatches(\"com.reddit.frontpage:id/comments\"))"));

    @Step("Fetching info of the post")
    public Post fetchPostInfo() {
        return new Post(user.getText(), timePosted.getText(), numberOfComments.getText());
    }
}
