package api.model;

public class Activity {
    private String activity;
    private String type;
    private int participants;
    private int price;
    private String link;
    private String key;
    private double accessibility;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(double accessibility) {
        this.accessibility = accessibility;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activity='" + activity + '\'' +
                ", type='" + type + '\'' +
                ", participants=" + participants +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", key='" + key + '\'' +
                ", accessibility=" + accessibility +
                '}';
    }
}
