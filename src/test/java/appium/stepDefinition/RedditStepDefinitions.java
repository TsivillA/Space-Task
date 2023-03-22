package appium.stepDefinition;

import appium.models.Post;
import appium.pages.FrontPage;
import appium.pages.MainPage;
import appium.pages.MostUpvotedPostPage;
import appium.pages.SearchResultPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class RedditStepDefinitions {
    private final FrontPage frontPage;
    private final MainPage mainPage;
    private final MostUpvotedPostPage mostUpvotedPostPage;
    private final SearchResultPage searchResultPage;

    public RedditStepDefinitions(FrontPage frontPage, MainPage mainPage, MostUpvotedPostPage mostUpvotedPostPage, SearchResultPage searchResultPage) {
        this.frontPage = frontPage;
        this.mainPage = mainPage;
        this.mostUpvotedPostPage = mostUpvotedPostPage;
        this.searchResultPage = searchResultPage;
    }

    @Given("I have opened the Reddit app")
    public void iHaveOpenedTheRedditApp() {
        frontPage.openMainPage();
    }
    @When("I search for {string}")
    public void iSearchFor(String query) {
        mainPage.searchFor(query);
    }

    @And("I sort the results by Hot")
    public void iSortTheResultsByHot() {
        searchResultPage.SortPosts();
    }

    @And("I pick the post with the most UpVotes from the first 20 results")
    public void iPickPostsWithMostUpvote() {
        searchResultPage.pickPostWithMostUpVotes();
    }

    @Then("I should see the name of the page\\/user that posted it, the time it was posted, and the number of comments")
    public void validateUserInfo() {
        Post post = mostUpvotedPostPage.fetchPostInfo();
        Assert.assertNotNull(post.getUser());
        Assert.assertNotNull(post.getDate());
        Assert.assertNotNull(post.getComments());
    }
}
