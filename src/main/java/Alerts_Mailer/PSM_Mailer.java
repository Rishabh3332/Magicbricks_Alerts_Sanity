package Alerts_Mailer;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PSM_Mailer {


    private WebDriver driver;
    private CommonUtils utils;
    WebDriverWait wait;
    BaseTest base;

    // 🔹 Locators (clean & reusable)
    private By propertyTypeLocator = By.xpath("//span[contains(@style,'font-size:17px') and contains(@style,'font-weight:600') and contains(@style,'color:#1e2b3c')]");

    private By locationLocator = By.xpath( "(//span[contains(@style,'font-size:14px') and contains(@style,'color:#4a6075')])[1]");

    private By viewDetailCTALocator = By.xpath(
            "//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'view price')]");

    // 🔹 Constructor
    public PSM_Mailer(WebDriver driver) {
        this.driver = driver;
        this.utils = new CommonUtils(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       	base = new BaseTest(); 
    }

    // ============================================================
    // 🔹 MAIN METHOD: CLICK VIEW DETAIL CTA
    // ============================================================

    public PropertyRedirectionResult clickViewDetailCTA() {

        PropertyRedirectionResult result = new PropertyRedirectionResult();

        try {

            // 🏠 Get Property Type
            WebElement typeEle = utils.find(propertyTypeLocator);
            String propertyType = typeEle.getText().trim();

            // 📍 Get Location
            WebElement locationEle = utils.find(locationLocator);
            String location = locationEle.getText().trim();

            System.out.println("🏠 Property Type: " + propertyType);
            System.out.println("📍 Location: " + location);

            // 🔴 CTA Click
            WebElement cta = utils.find(viewDetailCTALocator);

            utils.scrollToElement(cta);
            utils.click(cta);

            // 🔄 Switch to new tab
            utils.switchToNewTab();

            // 🌐 Wait handled inside utils (via click + implicit wait behavior)
            String redirectedUrl = driver.getCurrentUrl();

            System.out.println("➡ Redirected URL: " + redirectedUrl);

            // 🔍 Extract params
            Map<String, String> params = utils.extractUrlParams(redirectedUrl);

         // ✅ Extract ID
            String Topmatch_propertyId = params.get("id");

            System.out.println("🆔 Extracted Property ID: " + Topmatch_propertyId);
            
            // 📦 Fill result
            result.setPropertyType(propertyType);
            result.setLocation(location);
            result.setRedirectedUrl(redirectedUrl);
            result.setUrlParams(params);
            result.setPropertyId(Topmatch_propertyId);


        } catch (Exception e) {
            System.out.println("❌ Error in clickViewDetailCTA: " + e.getMessage());
        }

        return result;
    }

    // ============================================================
    // 🔹 OPTIONAL METHODS (Reusable / Future Ready)
    // ============================================================

    public boolean isCTAVisible() {
        return !driver.findElements(viewDetailCTALocator).isEmpty();
    }

    public int getCTACount() {
        return driver.findElements(viewDetailCTALocator).size();
    }
    
    // Unsubscribe locator
    private By unsubscribeLink = By.xpath("//a[normalize-space()='unsubscribe']");
  
    
    // Click Unsubscribe
    public PropertyRedirectionResult click_Unsubscribe() {
        
    	WebElement unsubscribe = wait.until(ExpectedConditions.elementToBeClickable(unsubscribeLink));
    	// Scroll to element
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].scrollIntoView({block: 'center'});", unsubscribe);
        unsubscribe.click();
        System.out.println("➡ Clicked on Unsubscribe CTA");

        // 🔄 Switch to new tab 
        base.switchToTab(driver, 1);

        // 🌐 Wait for unsubscribe page to load
        wait.until(ExpectedConditions.urlContains("unSubscribeMailer"));

        String unsuscribe_redirectedUrl = driver.getCurrentUrl();
        System.out.println("➡ Redirected URL: " + unsuscribe_redirectedUrl);

        // 🔍 Extract URL parameters
        Map<String, String> params = utils.extractUrlParams(unsuscribe_redirectedUrl);

        // 📦 Prepare DTO result
        PropertyRedirectionResult result = new PropertyRedirectionResult();
        result.setRedirectedUrl(unsuscribe_redirectedUrl);
        result.setUrlParams(params);

        // (Optional) Switch back to mail tab
//        base.switchToTab(driver, 0);

        return result;
    }

}
