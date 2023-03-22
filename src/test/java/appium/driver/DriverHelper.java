package appium.driver;

import com.codeborne.selenide.SelenideElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

public class DriverHelper extends DriverFactory{

    public static void sendKeysAndFind(SelenideElement element, String text){
        element.sendKeys(text);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
    }
    public static void clickElementWithCoordinates(int x, int y) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(input, Duration.ofMillis(200)));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(sequence));
    }

    public static void scrollDown(SelenideElement element) {
        int centerX = element.getRect().x + (element.getSize().width/2);
        double startY = element.getRect().y + (element.getSize().height * 0.9);
        double endY = element.getRect().y + (element.getSize().height * 0.03);


        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");//Type of Pointer Input
        Sequence swipe = new Sequence(finger, 1);;//Creating Sequence object to add actions
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int)startY));//Move finger into starting position
        swipe.addAction(finger.createPointerDown(0));//Finger comes down into contact with screen
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),//Finger moves to end position
                PointerInput.Origin.viewport(), centerX, (int)endY));
        swipe.addAction(finger.createPointerUp(0));//Get up Finger from Screen
        driver.perform(Arrays.asList(swipe));//Perform the actions
    }

    public static void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "up");
        params.put("element", $(AppiumBy.accessibilityId("Clear active filters")));
        js.executeScript("mobile: scroll", params);
    }

    public static void waitToLoad(AppiumBy locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
