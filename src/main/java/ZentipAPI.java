import co.flock.www.model.messages.Attachments.*;
import co.flock.www.model.messages.Message;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZentipAPI {

    private static final String LIKE_BUTTON_ICON = "https://f.flock.co/a49cd4b14719892015a7d2ee";
    private static final String TWEET_BUTTON_ICON = "https://f.flock.co/a49cd4b1471994305894837d";
    private static final String FOLLOW_BUTTON_ICON = "https://f.flock.co/a49cd4b14719960893716dc8";
    private static final String UNSUBSCRIBE_BUTTON_ICON = "https://cdn4.iconfinder.com/data/icons/vectory-bonus-1/40/rss_delete-128.png";
    private static final String THANKS_BUTTON_ICON = "https://f.flock.co/a49cd4b1471988939a597be9";

    private static final HashMap<String, User> users = new HashMap<>();

    public ZentipAPI() {
        addDefaultTags();
    }

    public static void addDefaultTags() {
        Tags tags = new Tags();
        tags.addTag("Android");
        tags.addTag("TechCrunch");
        tags.addTag("FlockSales");
        tags.addTag("FlockBlog");
        tags.addTag("Directi");
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static void createMessage(ZenTip zenTip) {
        String msg = "*" + "#" + zenTip.getTag() + "*";
        Message message = new Message(zenTip.getTo(), msg);

        Attachment attachment = new Attachment();
        attachment.setDescription(zenTip.getMessage());

        View view = new View();
        ImageView imageView = new ImageView();
        Image image = new Image();
        image.setSrc(zenTip.getImage());
        imageView.setOriginal(image);
        view.setImage(imageView);
        attachment.setViews(view);

        Button[] buttons = new Button[5];

        buttons[0] = new Button();
        buttons[0].setId("button-0-id");
        buttons[0].setIcon(LIKE_BUTTON_ICON);
        buttons[0].setName("Like");
        Action action = new Action();
        action.addDispatchEvent();
        buttons[0].setAction(action);

        buttons[1] = new Button();
        buttons[1].setId("button-1-id");
        buttons[1].setIcon(TWEET_BUTTON_ICON);
        buttons[1].setName("Tweet");
        action = new Action();
        action.addOpenBrowser("http://www.twitter.com", false);
        buttons[1].setAction(action);

        buttons[2] = new Button();
        String buttonId = isFollowing(zenTip) ? "unfollow" : "follow";
        buttonId += "-" + zenTip.getFrom();
        buttons[2].setId(buttonId);
        buttons[2].setIcon(FOLLOW_BUTTON_ICON);
        String buttonName = isFollowing(zenTip) ? "Unfollow" : "Follow";
        buttons[2].setName(buttonName);
        action = new Action();
        action.addDispatchEvent();
        buttons[2].setAction(action);

        buttons[3] = new Button();
        buttons[3].setId("appreciate-" + zenTip.getFrom());
        buttons[3].setIcon(THANKS_BUTTON_ICON);
        buttons[3].setName("Appreciate");
        action = new Action();
        action.addDispatchEvent();
        buttons[3].setAction(action);

        buttons[4] = new Button();
        buttonId = isSubscribed(zenTip) ? "unsubscribe" : "subscribe";
        buttons[4].setId(buttonId + "-" + zenTip.getTag());
        buttons[4].setIcon(UNSUBSCRIBE_BUTTON_ICON);
        buttonName = isSubscribed(zenTip) ? "Unsubscribe" : "Subscribe";
        buttons[4].setName(buttonName);
        action = new Action();
        action.addDispatchEvent();
        buttons[4].setAction(action);

        attachment.setButtons(buttons);

        Attachment[] attachments = new Attachment[1];
        attachments[0] = attachment;

        message.setAttachments(attachments);
        MessagingService.sendMessage(users.get(zenTip.getFrom()).getUserToken(), message);
    }

    public static void getTags(String userId) {
        List<String> tags = new ArrayList<>(new Tags().getTags());
        String msg = "Current tags are: \n";
        for (String tag : tags) {
            msg += tag + "\n";
        }
        MessagingService.sendMessage(users.get(userId).getUserToken(), new Message(userId, msg));
    }

    private static boolean isFollowing(ZenTip zenTip) {
        return new Followers().isFollower(zenTip.getFrom(), zenTip.getTo());
    }

    private static boolean isSubscribed(ZenTip zentip) {
        return new Subscriptions().getSubscribersForTag(zentip.getTag()).contains(zentip.getTo());
    }

    public static String getUserToken(String userId) {
        return users.get(userId).getUserToken();
    }
}
