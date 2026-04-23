package Alerts_Mailer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailInboxPage {

	WebDriver driver;
	WebDriverWait wait;

	public GmailInboxPage(WebDriver driver) {

		if (driver == null) {
			throw new RuntimeException("❌ Driver is NULL while initializing GmailInboxPage");
		}

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);

	}

	// Search mail by partial subject
	public void searchMailBySubject(String partialSubject) {
		partialSubject = partialSubject.trim();

		WebElement searchBox = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='q' and @aria-label='Search mail']")));

		searchBox.clear();
		searchBox.sendKeys("subject:(\"" + partialSubject + "\")");
		System.out.println("➡ Searched mail with subject: " + partialSubject);
		searchBox.sendKeys(Keys.ENTER);
	}

	// Open first matching mail
	public void openFirstMatchingMail() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@id=':n1']")));

		WebElement firstMail = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@id=':n1']")));

		firstMail.click();
		System.out.println("➡ Opened first matching mail");
	}

	// Combined reusable method
	public void searchAndOpenMailBySubject(String partialSubject) {
		searchMailBySubject(partialSubject);
		openFirstMatchingMail();
	}

	// Get opened mail subject
	public String getOpenedMailSubject() {
		WebElement subjectElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='hP']")));

		String subject = subjectElement.getText().trim();
		System.out.println("➡ Opened Mail Subject: " + subject);
		return subject;
	}

	// Generic regex validator
	public boolean isMailSubjectMatchingRegex(String regex) {
		return getOpenedMailSubject().matches(regex);
	}

}
