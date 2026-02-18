package Alerts_Mailer;

public enum CardCTA {

    NOT_INTERESTED("Not Interested"),
    YES_CONNECT_ME("Yes, Connect Me");

    private final String text;

    CardCTA(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
