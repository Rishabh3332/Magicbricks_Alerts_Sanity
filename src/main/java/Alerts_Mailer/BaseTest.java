package Alerts_Mailer;

import java.util.ArrayList;

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
	        
	        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	        options.setExperimentalOption("useAutomationExtension", false);

	        driver = new ChromeDriver(options);
	        driver.get("https://mail.google.com");
	        System.out.println("➡ User Succesfully logged-in to GMAIL Account " );
	        
	    }
	     
	    public void switchToTab(WebDriver driver, int index) { 
	        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

	        if (index < tabs.size()) {
	            driver.switchTo().window(tabs.get(index));
	        } else {
	            throw new RuntimeException("Tab index " + index + " not found. Total tabs: " + tabs.size());
	        }
	    }

	    public void CloseWindow() {
	        if (driver != null) {
	          //  driver.quit();
	        }
	    }
}
