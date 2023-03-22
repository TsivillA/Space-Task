package appium.pages;

import appium.driver.DriverHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;

public class SearchResultPage extends BasePage{
    private SelenideElement sortButton = $(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Sort\")"));
    private SelenideElement sortByHot = $(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]"));
    private SelenideElement postPanel = $(AppiumBy.id("com.reddit.frontpage:id/refresh_layout"));

    @Step("Sorting the results by hot")
    public void SortPosts() {
        sortButton.should(Condition.visible);
        sortButton.click();
        sortByHot.should(Condition.visible);
        sortByHot.click();

    }

    @Step("picking the most upvoted post")
    public void pickPostWithMostUpVotes() {
        List<SelenideElement> posts = new ArrayList<>();
        posts.addAll($$(AppiumBy.id("com.reddit.frontpage:id/upvotes")));//adding initial posts prior to scrolling

        String mostUpvote = sort(catchPosts(posts));

        SelenideElement mostUpvotedPost = $(AppiumBy.xpath(".//android.widget.TextView[@text='" + mostUpvote + "']"));
        if (!mostUpvotedPost.is(Condition.visible))
        mostUpvotedPost = $(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
                        + mostUpvote + "\").instance(0))"));
        mostUpvotedPost.click();
    }

    private Set<String> catchPosts(List<SelenideElement> posts) {
        Set<String> postTexts = new HashSet<>(); // to keep track of unique post texts
        int postcount = 3;
        for (int i = 0; i < posts.size(); i++) {
            SelenideElement post = posts.get(i);
            if (postTexts.contains(post.getText())) {
                posts.remove(i);// remove duplicate post
                i--;// adjust index to account for removed post
            } else {
                postTexts.add(post.getText());// add post text to set
            }
        }

        while (postcount <= 20) {
            DriverHelper.scrollDown(postPanel);
            List<SelenideElement> newPosts = $$(AppiumBy.id("com.reddit.frontpage:id/upvotes"));
            for (SelenideElement post : newPosts) {
                if (!postTexts.contains(post.getText())) {
                    postTexts.add(post.getText()); // add post text to set
                    posts.add(post); // add post to list
                    postcount++;
                }
            }
        }
        return postTexts;
    }

    private String sort(Set<String> upvotes) {
        return upvotes.stream()
                .map(upvote -> upvote.split(" "))
                .max((upvote1, upvote2) -> Integer.compare(Integer.parseInt(upvote1[0]), Integer.parseInt(upvote2[0])))
                .map(upvote -> upvote[0] + " upvotes")
                .orElse("No upvotes");
    }
}
