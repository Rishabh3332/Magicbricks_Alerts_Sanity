package Automation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Alerts_Mailer.BaseTest;
import Alerts_Mailer.CardCTA;
import Alerts_Mailer.Enc_dec;
import Alerts_Mailer.GmailInboxPage;
import Alerts_Mailer.Owner_Mailer;
import Alerts_Mailer.PropertyRedirectionResult;
import Alerts_Mailer.Subject_Line_excel;
import Alerts_Mailer.TopMatchesPage;
import Alerts_Mailer.UnsubscribePage;

public class Owner_TC extends BaseTest {

	private Owner_Mailer Owner;
	private PropertyRedirectionResult result;
	private TopMatchesPage topMatchesPage;
	private GmailInboxPage gmailInboxPage;
	Subject_Line_excel subject_Line_excel;
	SoftAssert softAssert = new SoftAssert();
	private UnsubscribePage unsubscribePage;

	@BeforeClass 
	public void setUpPages() {
		setup();
		Owner = new Owner_Mailer(driver);
		gmailInboxPage = new GmailInboxPage(driver);
		subject_Line_excel = new Subject_Line_excel(driver);
		topMatchesPage = new TopMatchesPage(driver);
		unsubscribePage = new UnsubscribePage(driver);

	}

	@Test(priority = 1, enabled = true, description = "Verify Owner Mail is received and open")
	public void Verify_Contact_AMP_Mail_Received_open() throws Exception {

		// Fetch partial subject from Excel
		String partialSubject = subject_Line_excel.getCellValue("sheet1", 4, 1).trim();

		// Search and open mail
		gmailInboxPage.searchAndOpenMailBySubject(partialSubject);

		// Validate dynamic property subject format
		String propertyRegex = ".*shared details of .* in your budget.*";

		boolean isValidSubject = gmailInboxPage.isMailSubjectMatchingRegex(propertyRegex);
		System.out.println("➡ Property Subject Validation Result: " + isValidSubject);

		softAssert.assertTrue(isValidSubject, "❌ Owner Mail property subject format is incorrect or mail not opened.");

//        softAssert.assertAll();
	}

	@Test(priority = 2, enabled = true)
	public void View_Photos_CTA_redirection() {
		result = Owner.clickViewDetailCTA();

		System.out.println("📌 URL Parameters:");
		result.getUrlParams().forEach((key, value) -> System.out.println(" " + key + " = " + value));

		// ✅ Soft Assertions
		softAssert.assertTrue(result.getRedirectedUrl().contains("top-matches-aln"), "❌ Redirected URL is incorrect");

		softAssert.assertEquals(result.getUrlParams().get("ctaType"), "viewDetails", "❌ CTA Type mismatch");

//    	    softAssert.assertAll();
	}

	@Test(priority = 3, enabled = true)
	public void Verify_BHK_and_Property_Type_Consistency() {

		// capture mail data
		String mailPropertyType = result.getPropertyType();
		System.out.println("📧 Mail Property Type: " + mailPropertyType);

		// Capture property type from Top Matches
		String topMatchesPropertyType = topMatchesPage.getFirstPropertyType();

		// Assertion
		softAssert.assertTrue(topMatchesPropertyType.contains(mailPropertyType.split(" ")[0]),
				"❌ Property Type mismatch between Mail and Top Matches");

		softAssert.assertAll();
	}

	private String normalizeProjectLocation(String text) {
		return text.toLowerCase().replace("project", "").replace("andaman & nicobar", "").replace("in", "")
				.replace(",", "").replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ").trim();
	}

	@Test(priority = 4, enabled = true)
	public void Verify_Project_and_Location_Consistency() {

		// Click CTA and capture mail data

		String mailProjectLocation = result.getLocation();
		System.out.println("📧 Mail Project & Location: " + mailProjectLocation);

		// Capture project & location from Top Matches
		String topMatchesProjectLocation = topMatchesPage.getProjectAndLocation();

		String normalizedMail = normalizeProjectLocation(mailProjectLocation);
		String normalizedTop = normalizeProjectLocation(topMatchesProjectLocation);

		System.out.println("📧 Normalized Mail: " + normalizedMail);
		System.out.println("🏠 Normalized Top: " + normalizedTop);


		softAssert.assertTrue(normalizedTop.contains(normalizedMail) || normalizedMail.contains(normalizedTop),
				"❌ Project name mismatch between Mail and Top Matches");

//        softAssert.assertAll();
	}

	@Test(priority = 5, enabled = true, description = "Verify redirection to Listing Detail Page (LDP) on clicking a property card from Top 									 										Matches page")
	public void verify_redirection_to_LDP_on_clicking_property_card() {

		// Click on property card (excluding CTAs)
		topMatchesPage.clickOnFirstPropertyCard();

		switchToTab(driver, 2);
		// Validate redirection to LDP
		String currentUrl = driver.getCurrentUrl();
		System.out.println("🔗 Redirected LDP URL: " + currentUrl);
		
		// 🔹 Get IDs
				String ldpPropId = topMatchesPage.get_LDP_PropertyID();
				String topMatchPropId = result.getPropertyId();
				
				String decryptedTopMatchPropId = Enc_dec.decrypt(topMatchPropId);

				System.out.println("🆔 LDP Property ID      : " + ldpPropId);
				System.out.println("🆔 Top Match Property ID: " + decryptedTopMatchPropId);

		softAssert.assertTrue(currentUrl.contains("propertyDetails"),
				"❌ User is not redirected to Listing Detail Page (LDP)");
		
		// ✅ Validate IDs match
				softAssert.assertEquals(ldpPropId, decryptedTopMatchPropId, "❌ Property ID mismatch between TopMatch and LDP");

		switchToTab(driver, 1);
//	    softAssert.assertAll();
	}

	@Test(priority = 6, enabled = true, description = "Verify Not Interested CTA functionality on Top Matches page")
	public void verify_Not_Interested_CTA_functionality_on_Top_Matches_page() {

		// Get card index before click
		int beforeCardIndex = topMatchesPage.getCurrentCardIndex();
		int totalCards = topMatchesPage.getTotalCardCount();
//    getCurrentCardIndex
		System.out.println("🔢 Before Click: " + beforeCardIndex + " out of " + totalCards);

		// Validation: Ensure more cards exist
		softAssert.assertTrue(beforeCardIndex < totalCards, "Not Interested CTA should not be clicked on last card");

		// Click Not Interested CTA
		topMatchesPage.clickCTAOnCurrentCard(CardCTA.NOT_INTERESTED.getText());

		// Wait until card index changes (swipe happens)
		topMatchesPage.waitForCardIndexToChange(beforeCardIndex);

//    topMatchesPage.clickCTAOnCurrentCard(CardCTA.NOT_INTERESTED.getText());

		// Get card index after click
		int afterCardIndex = topMatchesPage.getCurrentCardIndex();
		System.out.println("🔢 After Click: " + afterCardIndex + " out of " + totalCards);

		// Assertion
		softAssert.assertEquals(afterCardIndex, beforeCardIndex + 1,
				"After clicking Not Interested, next card should be shown");

//    softAssert.assertAll();
	}
	
	 @Test(priority = 7,enabled = false, description = "Verify Unsubscribe CTA redirection")
	    public void Verify_Unsubscribe_CTA_redirection() {
	
	        PropertyRedirectionResult result = Owner.click_Unsubscribe();
	
	        System.out.println("📌 Unsubscribe URL: " + result.getRedirectedUrl());
	
	        // ✅ Validate user redirected to unsubscribe page
	        softAssert.assertTrue(result.getRedirectedUrl().contains("unSubscribeMailer"),
	                "❌ User is not redirected to unsubscribe page");
	        
	     // ✅ Verify unsubscribe page loaded
	        softAssert.assertTrue(unsubscribePage.selectThirdUnsubscribeReason(),"❌ 3rd unsubscribe option was not selected");
	        
	        softAssert.assertTrue(unsubscribePage.isUnsubscribeSuccessPageDisplayed(), "❌ Unsubscribe Success page is NOT displayed.");
	        
	     // ✅ Click Go to Magicbricks CTA
	        unsubscribePage.clickGoToMagicbricksCTA();

	     //Validate redirection to homepage
	        String currentUrl = driver.getCurrentUrl();
	        softAssert.assertTrue(currentUrl.contains("magicbricks.com"),
	                "❌ Not redirected to Magicbricks homepage");
	        
	        softAssert.assertAll();
	    }
	 
}
