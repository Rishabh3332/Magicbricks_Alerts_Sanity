package Alerts_Mailer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TopMatchesPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public TopMatchesPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}

	/*
	 * -------------------------------------------------------- 🔹 First Property
	 * Card Locators (Top Matches)
	 * --------------------------------------------------------
	 */

	// 🏠 BHK + Property Type
	private By firstPropertyType = By.xpath("(//span[contains(@class,'m-srp-card__title__bhk')])[1]");

	// 💰 Price
	private By firstPropertyPrice = By.xpath("(//span[contains(@class,'top-matches__price')])[1]");

	// 💰 ProjectAndLocation
	private By firstProjectAndLocation = By.xpath("(//div[@class='m-srp-card__society'])[1]");

	private By Locality = By
			.xpath("(//span[@class='m-srp-card__title']//span[@class='m-srp-card__title__iconwname'])[1]");

	// top matches Property card
	private By firstPropertyCard = By.xpath("(//div[contains(@class,'tinder--card')])[2]");

	private By cardCounterText = By.xpath("//div[contains(@class,'tinder--card--pagination')]");

	// 🏠 BHK + Property Type
	private By PropertyID_LDP = By.xpath("//span[contains(@class,'mb-ldp__posted--propid')]");

	// 🔴 Not Interested CTA
//    private By notInterestedCTA = By.xpath("(//a[normalize-space()='Not Interested'])[1]");

	/*
	 * -------------------------------------------------------- 🔹 Page Load
	 * Validation --------------------------------------------------------
	 */
	public void waitForTopMatchesPage() {
		wait.until(ExpectedConditions.urlContains("magicbricks.com"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstPropertyType));
	}

	public void waitForLDP_Page() {
		wait.until(ExpectedConditions.urlContains("www.magicbricks.com/propertyDetails"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(PropertyID_LDP));
	}

	/*
	 * -------------------------------------------------------- 🔹 Get Property Type
	 * --------------------------------------------------------
	 */
	public String getFirstPropertyType() {
		waitForTopMatchesPage();
		String type = driver.findElement(firstPropertyType).getText();
		System.out.println("🏠 Top Matches Property Type: " + type);
		return type;
	}

	/*
	 * -------------------------------------------------------- 🔹 Get Property
	 * Price --------------------------------------------------------
	 */
	public String getFirstPropertyPrice() {
		waitForTopMatchesPage();
		String price = driver.findElement(firstPropertyPrice).getText();
		System.out.println("💰 Top Matches Property Price: " + price);
		return price;
	}

	/*
	 * -------------------------------------------------------- 🔹 Get
	 * projectAndLocation --------------------------------------------------------
	 */
	public String getProjectAndLocation() {
		waitForTopMatchesPage();
		String locationText = "";

		// Check if ProjectAndLocation element exists and is visible
		if (!driver.findElements(firstProjectAndLocation).isEmpty()) {
			locationText = driver.findElement(firstProjectAndLocation).getText().trim();
		}
		// Fallback to Locality if ProjectAndLocation is missing
		else if (!driver.findElements(Locality).isEmpty()) {
			locationText = driver.findElement(Locality).getText().trim();
		}
		// Optional: handle case where neither element is found
		else {
			System.out.println("⚠️ No project/location info found!");
			locationText = "N/A";
		}

		System.out.println("💰 Top Matches Property Project Name & Location: " + locationText);
		return locationText;
	}

	/*
	 * -------------------------------------------------------- 🔹 Click on First
	 * Property Card (excluding CTAs)
	 * --------------------------------------------------------
	 */
	public void clickOnFirstPropertyCard() {

		waitForTopMatchesPage();

		WebElement propertyCard = wait.until(ExpectedConditions.elementToBeClickable(firstPropertyCard));

		propertyCard.click();
	}

	public String getCardCounterText() {
		return driver.findElement(cardCounterText).getText().replaceAll("\\s+", " ").trim();
	}

	public int getCurrentCardIndex() {
		String text = getCardCounterText(); // "2 out of 9"
		return Integer.parseInt(text.split("out of")[0].trim());
	}

	public int getTotalCardCount() {
		String text = getCardCounterText(); // "2 out of 9"
		return Integer.parseInt(text.split("out of")[1].trim());
	}
	
		/* --------------------------------------------------------
		   🔹 Click Not Interested CTA
		   -------------------------------------------------------- */
//		public void clickNotInterestedCTA() {
//
//		    waitForTopMatchesPage();
//
////		    WebElement notInterested = wait.until(ExpectedConditions.elementToBeClickable(notInterestedCTA) );
//
//		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", notInterested );
//
//		    System.out.println("🚫 Clicked on Not Interested CTA");
//		}
		
		public void waitForCardIndexToChange(int oldIndex) {

			wait.until(driver -> {
				try {
					int newIndex = getCurrentCardIndex();
					return newIndex > oldIndex;
				} catch (Exception e) {
					return false;
				}
			});

			// Small buffer for animation to complete
			try {
				Thread.sleep(800);
			} catch (InterruptedException ignored) {
			}
		}

		/*
		 * -------------------------------------------------------- 🔹 Click CTA on
		 * currently visible Top Matches card
		 * --------------------------------------------------------
		 */
		public void clickCTAOnCurrentCard(String ctaText) {

			int maxAttempts = 5; // safe limit (you can increase if needed)

			for (int i = 1; i <= maxAttempts; i++) {
				try {
					By ctaLocator = By.xpath("(//a[contains(@class,'top-match__button') and normalize-space()='"
							+ ctaText + "'])[" + i + "]");

					WebElement cta = driver.findElement(ctaLocator);

					if (cta.isDisplayed()) {

						wait.until(ExpectedConditions.elementToBeClickable(cta));

						((JavascriptExecutor) driver).executeScript("arguments[0].click();", cta);

						System.out.println("👉 Clicked CTA: '" + ctaText + "' at index: " + i);
						return;
					}

				} catch (Exception ignored) {
					// Element not found at this index → move to next
				}
			}

			throw new RuntimeException("❌ No visible CTA found for: " + ctaText);
		}
		
		public String get_LDP_PropertyID() {
			waitForLDP_Page();
			String PropertyId = driver.findElement(PropertyID_LDP).getText().trim();
		
			// "Property ID: 82995547"

			// Extract only digits
			String propertyId = PropertyId.replaceAll("\\D+", "");
			System.out.println("💰 PropertyId on LDP : " + propertyId);
			return propertyId;
		}


}