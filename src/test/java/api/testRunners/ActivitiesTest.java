package api.testRunners;

import api.model.Activity;
import api.model.GetActivityResponseModel;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static api.config.WireMockSetup.closeServer;
import static api.config.WireMockSetup.setup;

public class ActivitiesTest {
    @BeforeMethod
    public void startup() {
        setup();
    }

    @AfterMethod
    public void tearDown() {
        closeServer();
    }

    @Test
    public void testGetActivities() {
        RestAssured.baseURI = "http://localhost:8080/api";
        GetActivityResponseModel responseModel = RestAssured.given()
                .get("/activity")
                .getBody()
                .as(GetActivityResponseModel.class);

        List<Activity> activities = responseModel
                .getActivities()
                .stream()
                .filter(activity -> activity.getPrice() > 0)
                .sorted(Comparator.comparingDouble(Activity::getAccessibility))
                .toList();
        System.out.println(activities);
    }
}
