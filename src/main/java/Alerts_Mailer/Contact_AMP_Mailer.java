package Alerts_Mailer;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Contact_AMP_Mailer {

    WebDriver driver;
    WebDriverWait wait;

    public Contact_AMP_Mailer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void scrollToViewSimilarProperties() {

        By locator = By.xpath("//div[contains(text(),'View Similar Properties')]");

        // Wait until element is present
        WebElement element = wait.until( ExpectedConditions.presenceOfElementLocated(locator));

        // Scroll to element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        System.out.println("✅ Scrolled to 'View Similar Properties'");
    }

    // ✅ STEP 2: Get property cards
    public int  getPropertyCards() {
    	
    	scrollToViewSimilarProperties();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'tinder--main-cont')]")));

        // Get all property cards
        List<WebElement> propertyCards = driver.findElements(By.xpath("//div[contains(@class,'tinder--main-cont')]"));
       
        return propertyCards.size();
    }

//    // ✅ STEP 3: Get property count
//    public int getPropertyCountFromMail() {
//
//    
//
//        List<WebElement> cards = getPropertyCards();
//
//        System.out.println("✅ Total property cards: " + cards.size());
//
//        return cards.size();
//    }

    // ✅ STEP 4: Click CTA (Show Next / Send Details)
//    public void clickCTAOnCards() {
//
//        List<WebElement> cards = getPropertyCards();
//
//        for (int i = 0; i < cards.size(); i++) {
//
//            WebElement card = cards.get(i);
//
//            try {
//                ((JavascriptExecutor) driver)
//                        .executeScript("arguments[0].scrollIntoView(true);", card);
//
//                System.out.println("➡ Processing card: " + (i + 1));
//
//                // Try Show Next
//                try {
//                    WebElement showNext = card.findElement(
//                            By.xpath(".//a[contains(text(),'Show Next')]"));
//
//                    if (showNext.isDisplayed()) {
//                        showNext.click();
//                        System.out.println("✅ Clicked Show Next");
//                        continue;
//                    }
//                } catch (Exception ignored) {}
//
//                // Try Send Details
//                try {
//                    WebElement sendDetails = card.findElement(
//                            By.xpath(".//a[contains(text(),'Send Details')]"));
//
//                    if (sendDetails.isDisplayed()) {
//                        sendDetails.click();
//                        System.out.println("✅ Clicked Send Details");
//                    }
//                } catch (Exception ignored) {
//                    System.out.println("⚠️ No CTA found");
//                }
//
//            } catch (Exception e) {
//                System.out.println("❌ Error on card: " + e.getMessage());
//            }
//        }
//    }
}