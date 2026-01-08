package Alerts_Mailer;
import java.util.Map;

public class PropertyRedirectionResult {

    private String propertyType;
    private String price;
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
}
