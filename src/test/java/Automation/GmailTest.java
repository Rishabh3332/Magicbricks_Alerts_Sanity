package Automation;

import Alerts_Mailer.BaseTest;
import Alerts_Mailer.GmailHomePage;
import Alerts_Mailer.PropertyRedirectionResult;
import Alerts_Mailer.Subject_Line_excel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GmailTest extends BaseTest {
	
	private GmailHomePage gmail;
	private PropertyRedirectionResult result;
	Subject_Line_excel subject_Line_excel;
	  SoftAssert softAssert = new SoftAssert();

	
	@BeforeClass
	public void createPOMObject() {
		setup();
	gmail=new GmailHomePage(driver);
	subject_Line_excel = new Subject_Line_excel(driver);

	}
	 
    @Test(priority = 1)
    public void Find_mail_and_open() throws Exception {
    	
    	String subject =subject_Line_excel.getCellValue("sheet1", 2, 1);
    	gmail.openMailBySubject(subject);
//    	gmail.getPropertyCountFromMail();
//    	gmail.Click_ViewDetail_CTA();
//        gmail.Enter_email();
//        CloseWindow();
    }
    
    @Test(priority = 2)
    public void validatePropertyCountInMail() {	
        int propertyCount = gmail.getPropertyCountFromMail();
        if(propertyCount>0) {
        	System.out.println("➡ Total properties received in mail: " + propertyCount);
        }
        else {
        	softAssert.assertTrue(propertyCount > 0, "⚠️ No properties found in mail!");
		    }
		} 
    @Test(priority = 3)
    public void View_Photos_CTA_redirection() {
    	  result = gmail.Click_ViewDetail_CTA();
    	
    	 System.out.println("📌 URL Parameters:");
    	    result.getUrlParams().forEach((key, value)->
    	    		System.out.println("   " + key + " = " + value));

    	    // ✅ Soft Assertions
    	    softAssert.assertTrue( result.getRedirectedUrl().contains("top-matches-aln"),
    	            "❌ Redirected URL is incorrect");

    	    softAssert.assertEquals(result.getUrlParams().get("ctaType"),
    	            "viewDetails","❌ CTA Type mismatch");

    	    softAssert.assertAll();
    	}

    	
    }
