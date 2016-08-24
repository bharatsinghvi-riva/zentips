import co.flock.www.model.messages.Attachments.*;
import co.flock.www.model.messages.Message;
import model.Subscriptions;
import model.Tags;
import model.ZenTip;

import java.util.ArrayList;
import java.util.List;

public class ZentipAPI {

    private static final String LIKE_BUTTON_ICON = "https://f.flock.co/a49cd4b14719892015a7d2ee";
    private static final String TWEET_BUTTON_ICON = "https://f.flock.co/a49cd4b1471994305894837d";
    private static final String FOLLOW_BUTTON_ICON = "https://f.flock.co/a49cd4b14719960893716dc8";
    private static final String UNSUBSCRIBE_BUTTON_ICON = "https://cdn4.iconfinder.com/data/icons/vectory-bonus-1/40/rss_delete-128.png";
    private static final String THANKS_BUTTON_ICON = "https://f.flock.co/a49cd4b1471988939a597be9";

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
        action.addDispatchEvent();
        buttons[1].setAction(action);

        buttons[2] = new Button();
        buttons[2].setId("button-2-id");
        buttons[2].setIcon(FOLLOW_BUTTON_ICON);
        buttons[2].setName("Follow");
        action = new Action();
        action.addDispatchEvent();
        buttons[2].setAction(action);

        buttons[3] = new Button();
        buttons[3].setId("button-3-id");
        buttons[3].setIcon(THANKS_BUTTON_ICON);
        buttons[3].setName("Appreciate");
        action = new Action();
        action.addDispatchEvent();
        buttons[3].setAction(action);

        buttons[4] = new Button();
        buttons[4].setId("button-4-id");
        buttons[4].setIcon(UNSUBSCRIBE_BUTTON_ICON);
        buttons[4].setName("Unsubscribe");
        action = new Action();
        action.addDispatchEvent();
        buttons[4].setAction(action);

        attachment.setButtons(buttons);

        Attachment[] attachments = new Attachment[1];
        attachments[0] = attachment;

        message.setAttachments(attachments);
        MessagingService.sendMessage(message);
    }

    public static void getTags(String userId) {
        List<String> tags = new ArrayList<>(new Tags().getTags());
        String msg = "Current tags are: \n";
        for (String tag : tags) {
            msg += tag + "\n";
        }
        MessagingService.sendMessage(new Message(userId, msg));
    }
}
