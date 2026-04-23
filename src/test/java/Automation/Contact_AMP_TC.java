package Automation;

import Alerts_Mailer.Contact_AMP_Mailer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Alerts_Mailer.BaseTest;
import Alerts_Mailer.GmailInboxPage;
import Alerts_Mailer.Subject_Line_excel;

public class Contact_AMP_TC extends BaseTest {

	private GmailInboxPage gmailInboxPage;
	Subject_Line_excel subject_Line_excel;
	SoftAssert softAssert = new SoftAssert();
	private Contact_AMP_Mailer contactAmpMailer;

    @BeforeClass
    public void setUpPages() {
    	setup();
        gmailInboxPage = new GmailInboxPage(driver);
        subject_Line_excel = new Subject_Line_excel(driver);
        contactAmpMailer = new Contact_AMP_Mailer(driver);
    }

    @Test(priority = 1, enabled = true, description = "Verify Contact AMP Mail is received and open")
    public void TC_125_Verify_Contact_AMP_Mail_Received_open() throws Exception {

    	// Fetch partial subject from Excel
        String partialSubject = subject_Line_excel.getCellValue("sheet1", 3, 1).trim();

        // Search and open mail
        gmailInboxPage.searchAndOpenMailBySubject(partialSubject);

        // Validate dynamic property subject format
        String propertyRegex = "^Contact Information for .+ - .+$";

        boolean isValidSubject = gmailInboxPage.isMailSubjectMatchingRegex(propertyRegex);
        System.out.println("➡ Property Subject Validation Result: " + isValidSubject);

        softAssert.assertTrue(isValidSubject,"❌ Contact AMP property subject format is incorrect or mail not opened.");

//        softAssert.assertAll();
    }
    @Test(priority = 2, enabled = true, description = "Verify dynamic property cards are displayed")
    public void validatePropertyCountInMail() {

        int propertyCount = contactAmpMailer.getPropertyCards();

        if (propertyCount > 0) {
            System.out.println("➡ Properties found: " + propertyCount);

            // Click CTA
//            contactAmpMailer.clickCTAOnCards();
        } else {
            softAssert.assertTrue(propertyCount > 0, "❌ No property cards found in AMP mail");
        }

        softAssert.assertAll();
    }
}
