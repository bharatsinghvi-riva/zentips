package model;

public class ZenTip {

    private final String tag;
    private final String image;
    private final String message;
    private final String from;
    private final String to;

    public ZenTip(String tag, String image, String message, String from, String to) {
        this.tag = tag;
        this.image = image;
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public String getTag() {
        return tag;
    }

    public String getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "ZenTip{" +
                "tag='" + tag + '\'' +
                ", image='" + image + '\'' +
                ", message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
