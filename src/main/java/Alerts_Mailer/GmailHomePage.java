package Alerts_Mailer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailHomePage {

    WebDriver driver;
    WebDriverWait wait;
    Subject_Line_excel subject_Line_excel;

    @FindBy(id = "identifierId")
    private WebElement emailElement;

    @FindBy(xpath = "//span[text()='Next']")
    private WebElement clickNextElement;

//    // Gmail global search box
//    @FindBy(xpath = "//input[@aria-label='Search mail']")
//    private WebElement searchBoxElement;

    // Constructor
    public GmailHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    	subject_Line_excel = new Subject_Line_excel(driver);
    }

    // Use ONLY if login page appears
    public void enterEmail(String emailId) {
        wait.until(ExpectedConditions.visibilityOf(emailElement))
                .sendKeys(emailId);

        wait.until(ExpectedConditions.elementToBeClickable(clickNextElement))
                .click();
    }
    
    String partialSubject =subject_Line_excel.getCellValue("sheet1", 1, 1);
    public void openMailBySubject(String partialSubject) {
    	
    	partialSubject = partialSubject.trim();
    	
    	 WebElement searchBox = new WebDriverWait(driver, Duration.ofSeconds(30))
    	            .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='q' and @aria-label='Search mail']")));

    	 searchBox.clear();
    	 	
    	    searchBox.sendKeys("subject:(" + partialSubject + ")");
    	    searchBox.sendKeys(Keys.ENTER);

        // Click first matching mail
    	    WebElement mailRow = new WebDriverWait(driver, Duration.ofSeconds(30))
    	.until(ExpectedConditions.elementToBeClickable(By.xpath("(//tr[contains(@class,'zA') and contains(@class,'yO')])[21]")));

    	    mailRow.click();  
    }
    
    
    @FindBy(xpath = "(//a[contains(@href,'magicbricks.com') ])[2]")
    private WebElement click_View_detail_Element;
    
    public void Click_ViewDetail_CTA() {
//    	WebElement clickElement = new WebDriverWait(driver, Duration.ofSeconds(30))
    			wait.until(ExpectedConditions.elementToBeClickable(click_View_detail_Element)).click();
	}
    
}
