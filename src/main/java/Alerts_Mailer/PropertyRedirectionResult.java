package Alerts_Mailer;
import java.util.Map;

public class PropertyRedirectionResult {

    private String propertyType;
    private String price;
    private String location;
    private String redirectedUrl;
    private Map<String, String> urlParams;

    // ---------- Getters & Setters ----------

    public String getPropertyType() {
        return propertyType;   
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
 

    public String getRedirectedUrl() {
        return redirectedUrl;
    }

    public void setRedirectedUrl(String redirectedUrl) {
        this.redirectedUrl = redirectedUrl;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
     }
    
    public String getTopMatchPropertyType() {
        return propertyType;   
    }

    public void setTopMatchPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getTopMatchPrice() {
        return price;
    }

    public void setTopMatchPrice(String price) {
        this.price = price;
    }
    
    public String getTopMatchLocation() {
        return location;
    }

    public void setTopMatchLocation(String location) {
        this.location = location;
    }
 

    public String getTopMatchRedirectedUrl() {
        return redirectedUrl;
    }

    public void setTopMatchRedirectedUrl(String redirectedUrl) {
        this.redirectedUrl = redirectedUrl;
    }

    public Map<String, String> getTopMatchUrlParams() {
        return urlParams;
    }

    public void setTopMatchUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
     }
    
    
}
