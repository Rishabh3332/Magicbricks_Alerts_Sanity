package Alerts_Mailer;

import java.time.Duration;
import java.util.Map;
import java.util.HashMap;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public CommonUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // 🔹 Wait + Find
    public WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // 🔹 Scroll
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // 🔹 Click
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // 🔹 Switch to new tab
    public void switchToNewTab() {
        String parent = driver.getWindowHandle();

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    // 🔹 Extract URL params
    public Map<String, String> extractUrlParams(String url) {

        Map<String, String> params = new HashMap<>();

        if (!url.contains("?")) return params;

        String query = url.substring(url.indexOf("?") + 1);
        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}