package Alerts_Mailer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnsubscribePage {

    WebDriver driver;
    WebDriverWait wait;

    public UnsubscribePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

   

	// 🔘 3rd Option Radio Button
    private By thirdOptionRadio = By.xpath("(//input[@value='not_looking_now'])[1]");
    
    // Success message text
    private By successMessage =  By.xpath("//*[contains(text(),\"You've been unsubscribed\")]");
    
    // Go to Magicbricks CTA
    private By goToMagicbricksCTA = By.xpath("(//button[@class='unsubscribe-button unsubscribe-button--outline'])[1]");


    // 📌 Select 3rd option
    public boolean selectThirdUnsubscribeReason() {

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(thirdOptionRadio));

        option.click();

        System.out.println("➡ Selected: I am not looking for a property anymore");

        return option.isSelected();
    }

    // 🔎 Verify correct page loaded
    public boolean isUnsubscribeSuccessPageDisplayed() {

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        System.out.println("➡ Unsubscribe Success Message Displayed");

        return message.isDisplayed();
    }
    
    public void clickGoToMagicbricksCTA() {

        WebElement goToMb = wait.until(ExpectedConditions.elementToBeClickable(goToMagicbricksCTA));

        goToMb.click();

        System.out.println("➡ Clicked 'Go to Magicbricks' CTA");
    }
}
