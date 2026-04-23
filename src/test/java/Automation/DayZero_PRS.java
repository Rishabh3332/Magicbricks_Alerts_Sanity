package Automation;

import Alerts_Mailer.BaseTest;
import Alerts_Mailer.CardCTA;
import Alerts_Mailer.Day_Zero_PRS_Mailer;
import Alerts_Mailer.PropertyRedirectionResult;
import Alerts_Mailer.Subject_Line_excel;
import Alerts_Mailer.TopMatchesPage;
import Alerts_Mailer.UnsubscribePage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class DayZero_PRS extends BaseTest {
	
	private Day_Zero_PRS_Mailer day_0;
	private PropertyRedirectionResult result;
	private TopMatchesPage topMatchesPage;
	Subject_Line_excel subject_Line_excel;
	SoftAssert softAssert = new SoftAssert();
	private UnsubscribePage unsubscribePage;

	
	@BeforeClass
	public void createPOMObject() {
	setup();
	day_0=new Day_Zero_PRS_Mailer(driver);
	subject_Line_excel = new Subject_Line_excel(driver);
	topMatchesPage = new TopMatchesPage(driver);
	unsubscribePage = new UnsubscribePage(driver);

	}
	 
    @Test(priority = 1)
    public void Find_mail_and_open() throws Exception {
    	
    	String subject =subject_Line_excel.getCellValue("sheet1", 2, 1);
    	day_0.openMailBySubject(subject);
//    	gmail.getPropertyCountFromMail();
//    	gmail.Click_ViewDetail_CTA();
//        gmail.Enter_email();
//        CloseWindow();
    }
    
    @Test(priority = 2, enabled = true)
    public void validatePropertyCountInMail() {	
        int propertyCount = day_0.getPropertyCountFromMail();
        if(propertyCount>0) {
        	System.out.println("➡ Total properties received in mail: " + propertyCount);
        }
        else {
        	softAssert.assertTrue(propertyCount > 0, "⚠️ No properties found in mail!");
		    }
		} 
    
    @Test(priority = 3, enabled = true)
    public void View_Photos_CTA_redirection() {
    	  result = day_0.Click_ViewDetail_CTA();
    	
    	 System.out.println("📌 URL Parameters:");
    	 result.getUrlParams().forEach((key, value)->
    	  System.out.println(" " + key + " = " + value));

    	    // ✅ Soft Assertions
    	    softAssert.assertTrue( result.getRedirectedUrl().contains("top-matches-aln"),
    	            "❌ Redirected URL is incorrect");

    	    softAssert.assertEquals(result.getUrlParams().get("ctaType"),
    	            "viewDetails","❌ CTA Type mismatch");

//    	    softAssert.assertAll();
    	}
    @Test(priority = 4, enabled = true)
    public void verify_property_price_consistency_between_mail_and_top_matches() {

     // capture mail data
        String Mail_Price = result.getPrice();
        System.out.println("📧 Property Price on Mail: " + Mail_Price);

        //  Capture price from Top Matches page
        String topMatchesPrice = topMatchesPage.getFirstPropertyPrice();

        //  Assertion
        softAssert.assertEquals(topMatchesPrice, result.getPrice(),
        		"❌ Property price mismatch between mail and Top Matches card");

//        softAssert.assertAll();
    }
    
    @Test(priority = 5, enabled = true)
    public void Verify_BHK_and_Property_Type_Consistency() {

    	// capture mail data
        String mailPropertyType = result.getPropertyType();
        System.out.println("📧 Mail Property Type: " + mailPropertyType);

        //  Capture property type from Top Matches
        String topMatchesPropertyType = topMatchesPage.getFirstPropertyType();

        //  Assertion
        softAssert.assertTrue(topMatchesPropertyType.contains(mailPropertyType.split(" ")[0]),
                "❌ Property Type mismatch between Mail and Top Matches");

        softAssert.assertAll();
    }
    
    private String normalizeProjectLocation(String text) {
        return text.toLowerCase()
                .replace("project", "")
                .replace("andaman & nicobar", "")
                .replace("in", "")
                .replace(",", "")
                .replaceAll("[^a-z0-9 ]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
    
    @Test(priority = 6, enabled = true)
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


        //  Soft Assertions
//        softAssert.assertNotNull(mailProjectLocation,
//                "❌ Project & Location missing in Mail");
//
//        softAssert.assertNotNull(topMatchesProjectLocation,
//                "❌ Project & Location missing in Top Matches");

        softAssert.assertTrue(normalizedTop.contains(normalizedMail) || normalizedMail.contains(normalizedTop),
                "❌ Project name mismatch between Mail and Top Matches");

//        softAssert.assertAll();
    }
    
    @Test(priority = 7,enabled = true, description = "Verify redirection to Listing Detail Page (LDP) on clicking a property card from Top 									 										Matches page")
    	public void verify_redirection_to_LDP_on_clicking_property_card() {

    		//Click on property card (excluding CTAs)
    	    topMatchesPage.clickOnFirstPropertyCard();
    	    
    	    switchToTab(driver, 2); 
    	    // Validate redirection to LDP
    	    String currentUrl = driver.getCurrentUrl();
    	    System.out.println("🔗 Redirected LDP URL: " + currentUrl);

    	    softAssert.assertTrue(currentUrl.contains("propertyDetails"),
    	            "❌ User is not redirected to Listing Detail Page (LDP)");
    	    
    	    switchToTab(driver, 1); 
//    	    softAssert.assertAll();
    	}
    
    @Test(priority = 8, enabled = true, description = "Verify Not Interested CTA functionality on Top Matches page")
    	public void verify_Not_Interested_CTA_functionality_on_Top_Matches_page() {
    	
        // Get card index before click
        int beforeCardIndex = topMatchesPage.getCurrentCardIndex();
        int totalCards = topMatchesPage.getTotalCardCount();
//        getCurrentCardIndex
        System.out.println("🔢 Before Click: " + beforeCardIndex + " out of " + totalCards);

        // Validation: Ensure more cards exist
        softAssert.assertTrue(beforeCardIndex < totalCards, "Not Interested CTA should not be clicked on last card");

        //  Click Not Interested CTA
        topMatchesPage.clickCTAOnCurrentCard(CardCTA.NOT_INTERESTED.getText());

        //  Wait until card index changes (swipe happens)
        topMatchesPage.waitForCardIndexToChange(beforeCardIndex);
        
//        topMatchesPage.clickCTAOnCurrentCard(CardCTA.NOT_INTERESTED.getText());

        //  Get card index after click
        int afterCardIndex = topMatchesPage.getCurrentCardIndex();
        System.out.println("🔢 After Click: " + afterCardIndex + " out of " + totalCards);

        //  Assertion
        softAssert.assertEquals(afterCardIndex,beforeCardIndex + 1,
                "After clicking Not Interested, next card should be shown" );

//        softAssert.assertAll();
    }    	
    
    @Test(priority = 9,enabled = true, description = "Verify Yes, Connect Me CTA functionality on Top Matches page")
    public void verify_Yes_Connect_Me_CTA_functionality_on_Top_Matches_page() {

        int beforeIndex = topMatchesPage.getCurrentCardIndex();
        int totalCards = topMatchesPage.getTotalCardCount();
//      getCurrentCardIndex
      System.out.println("🔢 Before Click: " + beforeIndex + " out of " + totalCards);


        topMatchesPage.clickCTAOnCurrentCard(CardCTA.YES_CONNECT_ME.getText());

        topMatchesPage.waitForCardIndexToChange(beforeIndex);
        
//        topMatchesPage.clickCTAOnCurrentCard(CardCTA.YES_CONNECT_ME.getText());
        
        int afterIndex = topMatchesPage.getCurrentCardIndex();
        System.out.println("🔢 After Click: " + afterIndex + " out of " + totalCards);

        softAssert.assertEquals(afterIndex, beforeIndex + 1,
                "After Yes, Connect Me, next card should be shown");

//        softAssert.assertAll();
    }
    
    @Test(priority = 11, enabled = true, description = "Verify View Number / View Owner's Number CTA redirection")
    	public void View_Number_CTA_redirection() {

    	   	result = day_0.Click_ViewNumber_CTA();

    	    // ✅ Assertion: User lands on Top Matches page
    	    softAssert.assertTrue(result.getRedirectedUrl().contains("magicbricks.com"),
    	            "User should be redirected to Top Matches page");

    	    // ✅ Assertion: Property details captured
    	    softAssert.assertNotNull(result.getPropertyType(), "Property type should not be null");
    	    softAssert.assertNotNull(result.getPrice(), "Price should not be null");
    	    softAssert.assertNotNull(result.getLocation(), "Location should not be null");

    	    // ✅ Assertion: Auto-contact parameter (if present)
    	    softAssert.assertTrue( result.getUrlParams().size() > 0, "URL parameters should be present after redirection");

//    	    softAssert.assertAll();
    	}
    @Test(priority = 14, enabled = false , description = "Verify See Matching Properties CTA redirection")
    	public void See_Matching_Properties_CTA_redirection() {

    	    PropertyRedirectionResult result =  day_0.Click_SeeMatchingProperties_CTA();
    	   
    	    System.out.println("📌 URL Parameters:");
	       	result.getUrlParams().forEach((key, value)->
	       	System.out.println(" " + key + " = " + value));
	
       	    // ✅ Soft Assertions
       	    softAssert.assertTrue( result.getRedirectedUrl().contains("top-matches-aln"),
       	            "❌ Redirected URL is incorrect");

       	    softAssert.assertEquals(result.getUrlParams().get("ctaType"),
       	            "viewDetails","❌ CTA Type mismatch");

    	    // ✅ First property consistency check
    	    softAssert.assertTrue(result.getTopMatchPropertyType().contains(result.getPropertyType().split(" ")[0]),
    	            "First property type should match mail first property");

    	    softAssert.assertNotNull(result.getTopMatchPrice(),"Top Matches first property price should not be null");

    	    softAssert.assertNotNull(result.getTopMatchLocation(),"Top Matches first property location should not be null");

    	    softAssert.assertAll();
    	    
    	}
    
    @Test(priority = 15,enabled = false, description = "Verify Unsubscribe CTA redirection")
	    public void Verify_Unsubscribe_CTA_redirection() {
	
	        PropertyRedirectionResult result = day_0.click_Unsubscribe();
	
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
