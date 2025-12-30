package Alerts_Mailer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	 public static WebDriver driver;

	    public static void setup()   {
	       
	        WebDriverManager.chromedriver().setup();
			/*
			 * ChromeOptions options = new ChromeOptions();
			 * options.setExperimentalOption("excludeSwitches", new
			 * String[]{"enable-automation"});
			 * //options.setExperimentalOption("useAutomationExtension", false);
			 * 
			 * 
			 * options.
			 * addArguments("user-data-dir=C:\\Users\\Rishabh.Singh1\\AppData\\Local\\Google\\Chrome\\User Data"
			 * ); options.addArguments("profile-directory=Profile 20");
			 * options.addArguments("--disable-dev-shm-usage");
			 * options.addArguments("--no-sandbox"); options.addArguments("--disable-gpu");
			 * options.addArguments("--disable-software-rasterizer");
			 * options.addArguments("--disable-notifications"); //
			 * options.addArguments("--start-maximized");
			 * options.addArguments("--remote-allow-origins=*");
			 * 
			 * WebDriver driver = new ChromeDriver(options);
			 * driver.manage().window().maximize(); driver.get("https://mail.google.com");
			 */
	        ChromeOptions options = new ChromeOptions();
	       
	        // Dedicated Selenium profile
	        options.addArguments("user-data-dir=C:\\Gmail_Acount20");
	        options.addArguments("--disable-notifications");
	      //options.addArguments("user-data-dir=C:\\Gmail_Account_1");

	        driver = new ChromeDriver(options);
	        driver.get("https://mail.google.com");
	        
	    }

	    public void CloseWindow() {
	        if (driver != null) {
	          //  driver.quit();
	        }
	    }
}
