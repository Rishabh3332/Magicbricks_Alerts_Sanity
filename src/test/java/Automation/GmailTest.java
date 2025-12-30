package Automation;

import Alerts_Mailer.BaseTest;
import Alerts_Mailer.GmailHomePage;
import Alerts_Mailer.Subject_Line_excel;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.dockerjava.api.model.Driver;

public class GmailTest extends BaseTest {
	
	private GmailHomePage gmail;
	Subject_Line_excel subject_Line_excel;

	
	@BeforeClass
	public void createPOMObject() {
		setup();
	gmail=new GmailHomePage(driver);
	subject_Line_excel = new Subject_Line_excel(driver);

	}
	
    @Test(priority = 1)
    public void Find_mail_and_open() throws Exception {
    	
    	
    	gmail.openMailBySubject("SubjectLine");
    	gmail.Click_ViewDetail_CTA();
//        gmail.Enter_email();
//        CloseWindow();
    }
}