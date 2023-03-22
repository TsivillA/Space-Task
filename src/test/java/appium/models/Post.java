package appium.models;

public class Post {
    private String user;
    private String date;
    private String comments;

    public Post(String user, String date, String comments) {
        this.user = user;
        this.date = date;
        this.comments = comments;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }
}
