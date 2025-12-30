package Automation;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class gmailTest1{

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        
        

     // Dedicated Selenium profile
     options.addArguments("user-data-dir=C:\\Gmail_Acount20");
        //options.addArguments("user-data-dir=C:\\Gmail_Account_1");

     WebDriver driver = new ChromeDriver(options);
     driver.get("https://mail.google.com");
    }
}
