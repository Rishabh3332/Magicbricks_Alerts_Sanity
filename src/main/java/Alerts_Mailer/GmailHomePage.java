package Alerts_Mailer;


import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    BaseTest base;
    
    
  private  PropertyRedirectionResult result;
  //  SoftAssert softAssert = new SoftAssert();

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
    	base = new BaseTest(); 
//    	property_edirection_Result = new PropertyRedirectionResult(driver);
    }

    // Use ONLY if login page appears
    public void enterEmail(String emailId) {
        wait.until(ExpectedConditions.visibilityOf(emailElement))
                .sendKeys(emailId);

        wait.until(ExpectedConditions.elementToBeClickable(clickNextElement))
                .click();
    }
    
  
    public void openMailBySubject(String partialSubject) {
//    	
//    	partialSubject = partialSubject.trim();
    	
    	 WebElement searchBox = new WebDriverWait(driver, Duration.ofSeconds(30))
    	            .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='q' and @aria-label='Search mail']")));

    	 	searchBox.clear();
    	    searchBox.sendKeys("subject:(" + partialSubject + ")");
    	    System.out.println("➡ Search for mail by using Subject Line: " + partialSubject);
    	    searchBox.sendKeys(Keys.ENTER);

        // Click first matching mail
    	    WebElement mailRow = new WebDriverWait(driver, Duration.ofSeconds(30))
    	.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[contains(@class,'zA' )and @id=':n1' and @tabindex='-1' and @role='row']")));
    	
    	    mailRow.click();  
    }
    
    public int getPropertyCountFromMail() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='View Photos']")));

        // Get all property cards
        List<WebElement> propertyCards = driver.findElements(By.xpath("//a[text()='View Photos']"));
       
        return propertyCards.size();
    }
     
//    @FindBy(xpath = "(//a[contains(@href,'magicbricks.com') ])[2]")
    @FindBy(xpath = "//a[text()='View Photos']")
    private List<WebElement> click_View_detail_Element;
    
    @FindBy(xpath ="(//td[contains(@style,'padding:12px 7%')])[position()>=2 and position()<=6]")
    private List<WebElement> propertyCards;
     
    public PropertyRedirectionResult  Click_ViewDetail_CTA() {
//    	WebElement clickElement = new WebDriverWait(driver, Duration.ofSeconds(30))
//    			wait.until(ExpectedConditions.elementToBeClickable(click_View_detail_Element)).click();
	 wait.until(ExpectedConditions.visibilityOfAllElements(propertyCards));
	 int totalCTAs = propertyCards.size();

	    if (totalCTAs == 0) {
	        throw new RuntimeException("❌ No View Detail CTAs found in mail!");
	    }

	    // Generate random index
	    int randomIndex = new Random().nextInt(totalCTAs);
	    WebElement card = propertyCards.get(randomIndex);
	    
	    System.out.println("➡ Selected Card Index: " + randomIndex);

	    // 🏠 Property Type
	    String propertyType = card.findElement( By.xpath(".//td[contains(text(),'BHK')]")).getText();

	    // 💰 Price
	    String price = card.findElement(By.xpath(".//td[contains(text(),'₹')]")).getText();
	    
	    // 💰 Project Name & location
	    String projectAndLocation = card.findElement(By.xpath(".//td[contains(@style,'font-size:14px') and contains(@style,'color:#909090')]")).getText();

	    System.out.println("➡ Selected Property Details:");
	    System.out.println("   🏠 Type : " + propertyType);
	    System.out.println("   💰 Price: " + price);
	    System.out.println("   📍 Project and location: " + projectAndLocation);
//	    
//	    String[]list=projectAndLocation.split(",");
//
//	    System.out.println(list[0] );
//	    System.out.println(list[1] );
	    // 🔴 View Photos CTA inside SAME CARD
	    WebElement viewPhotosCTA = card.findElement(By.xpath(".//a[normalize-space()='View Photos']"));
	    wait.until(ExpectedConditions.elementToBeClickable(viewPhotosCTA)).click();
	    
	    ArrayList<String> add=new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(add.get(1));
	 // 🌐 Capture redirected URL
	    wait.until(ExpectedConditions.urlContains("magicbricks.com"));
	    String View_detail_redirectedUrl = driver.getCurrentUrl();

	    System.out.println("➡ Redirected URL:"+ View_detail_redirectedUrl);
	    
	 // 🔍 Extract URL parameters
	    Map<String, String> params = extractUrlParams(View_detail_redirectedUrl);

	    // 📦 Fill DTO
	    result = new PropertyRedirectionResult();
	    result.setPropertyType(propertyType);
	    result.setPrice(price);
	    result.setLocation(projectAndLocation);
	    result.setRedirectedUrl(View_detail_redirectedUrl);
	    result.setUrlParams(params);
	       
	    return result;
	    
	}
    
    public PropertyRedirectionResult Click_ViewNumber_CTA() {

        wait.until(ExpectedConditions.visibilityOfAllElements(propertyCards));
        int totalCards = propertyCards.size();

        if (totalCards == 0) {
            throw new RuntimeException("❌ No property cards found in PRS mail!");
        }

        // 🎯 Pick random card
        int randomIndex = new Random().nextInt(totalCards);
        WebElement card = propertyCards.get(randomIndex);

        System.out.println("➡ Selected Card Index: " + randomIndex);

        // 🏠 Property Type
        String propertyType = card.findElement(
                By.xpath(".//td[contains(text(),'BHK')]")).getText();

        // 💰 Price
        String price = card.findElement(
                By.xpath(".//td[contains(text(),'₹')]")).getText();

        // 📍 Project & Location
        String projectAndLocation = card.findElement(
                By.xpath(".//td[contains(@style,'font-size:14px')]")).getText();

        System.out.println("➡ Selected Property Details:");
        System.out.println("   🏠 Type : " + propertyType);
        System.out.println("   💰 Price: " + price);
        System.out.println("   📍 Location: " + projectAndLocation);

     // View Number / View Owner's Number CTA inside card
        By viewNumberCTA = By.xpath(".//a[normalize-space()='View Number' or normalize-space()=\"View Owner's Number\"]");

        // 🔴 Click View Number CTA (inside SAME card)
        WebElement viewNumber = card.findElement(viewNumberCTA);
        wait.until(ExpectedConditions.elementToBeClickable(viewNumber)).click();

        // 🔄 Switch tab
        ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(1));

        // 🌐 Wait for Top Matches page
        wait.until(ExpectedConditions.urlContains("magicbricks.com"));

        String redirectedUrl = driver.getCurrentUrl();
        System.out.println("➡ Redirected URL: " + redirectedUrl);

        // 🔍 Extract URL params
        Map<String, String> params = extractUrlParams(redirectedUrl);

        // 📦 Prepare Result DTO
        PropertyRedirectionResult result = new PropertyRedirectionResult();
        result.setPropertyType(propertyType);
        result.setPrice(price);
        result.setLocation(projectAndLocation);
        result.setRedirectedUrl(redirectedUrl);
        result.setUrlParams(params);

        return result;
    }

   
    
    
    private Map<String, String> extractUrlParams(String url) {
        Map<String, String> params = new HashMap<String, String>();

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
    
	 // See Matching Properties CTA (Mail level)
	    private By seeMatchingPropertiesCTA =
	            By.xpath("//a[normalize-space()='See Matching Properties']");
	    
	    public PropertyRedirectionResult Click_SeeMatchingProperties_CTA() {

	        wait.until(ExpectedConditions.visibilityOfAllElements(propertyCards));

	        if (propertyCards.size() == 0) {
	            throw new RuntimeException("❌ No property cards found in PRS mail!");
	        }

	        // 🥇 FIRST PROPERTY FROM MAIL
	        WebElement firstCard = propertyCards.get(0);

	        String mailPropertyType = firstCard
	                .findElement(By.xpath(".//td[contains(text(),'BHK')]"))
	                .getText();

	        String mailPrice = firstCard
	                .findElement(By.xpath(".//td[contains(text(),'₹')]"))
	                .getText();

	        String mailProjectLocation = firstCard
	                .findElement(By.xpath(".//td[contains(@style,'font-size:14px')]"))
	                .getText();

	        System.out.println("📧 Mail – First Property:");
	        System.out.println("   🏠 Type : " + mailPropertyType);
	        System.out.println("   💰 Price: " + mailPrice);
	        System.out.println("   📍 Location: " + mailProjectLocation);

	        // 🔴 Click See Matching Properties CTA
	        WebElement seeMatchingCTA =
	                wait.until(ExpectedConditions.elementToBeClickable(seeMatchingPropertiesCTA));
	        seeMatchingCTA.click();

	        // 🔄 Switch to new tab
	        base.switchToTab(driver, 1); 

	        // 🌐 Wait for Top Matches page
	        wait.until(ExpectedConditions.urlContains("magicbricks.com"));

	        String redirectedUrl = driver.getCurrentUrl();
	        System.out.println("➡ Redirected URL: " + redirectedUrl);
	        
	        // 🔍 Extract URL parameters
		    Map<String, String> params = extractUrlParams(redirectedUrl);

	    

	        // 📦 Fill DTO
	        PropertyRedirectionResult result = new PropertyRedirectionResult();
	        result.setPropertyType(mailPropertyType);
	        result.setPrice(mailPrice);
	        result.setLocation(mailProjectLocation);
	        result.setRedirectedUrl(redirectedUrl);
	        result.setUrlParams(params);
	        
	        base.switchToTab(driver, 0);
	        return result;
	        
	    }
	    
	    
	    
    
    
}
