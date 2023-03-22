Feature: Reddit Banking Posts

Scenario: Search for a popular banking post on Reddit
   Given I have opened the Reddit app
   When I search for "Banking"
   And I sort the results by Hot
   And I pick the post with the most UpVotes from the first 20 results
   Then I should see the name of the page/user that posted it, the time it was posted, and the number of comments

