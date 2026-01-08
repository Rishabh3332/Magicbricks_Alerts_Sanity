package Alerts_Mailer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	 public static WebDriver driver;

	    public static void setup()   {
	       
	        WebDriverManager.chromedriver().setup();

//			  options.setExperimentalOption("excludeSwitches", new
			  //options.setExperimentalOption("useAutomationExtension", false);
			
	        ChromeOptions options = new ChromeOptions();
	       
	        // Dedicated Selenium profile
	        options.addArguments("user-data-dir=C:\\Gmail_Acount20");
	        options.addArguments("--disable-notifications");
	      //options.addArguments("user-data-dir=C:\\Gmail_Account_1");

	        driver = new ChromeDriver(options);
	        driver.get("https://mail.google.com");
	        System.out.println("➡ User Succesfully logged-in to GMAIL Account " );
	        
	    }

	    public void CloseWindow() {
	        if (driver != null) {
	          //  driver.quit();
	        }
	    }
}
