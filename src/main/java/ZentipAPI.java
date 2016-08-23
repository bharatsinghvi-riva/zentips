import co.flock.www.model.messages.Attachments.*;
import co.flock.www.model.messages.Message;
import model.ZenTip;

public class ZentipAPI {

    private static final String LIKE_BUTTON_ICON = "https://f.flock.co/a49cd4b14719892015a7d2ee";
    private static final String THANKS_BUTTON_ICON = "https://f.flock.co/a49cd4b1471988939a597be9";
    private static final String TWEET_BUTTON_ICON = "https://f.flock.co/a49cd4b1471989314798cfe4";

    public static void createMessage(ZenTip zenTip) {
        Message message = new Message(zenTip.getTo(), zenTip.getMessage());

        Attachment attachment = new Attachment();

        View view = new View();
        ImageView imageView = new ImageView();
        Image image = new Image();
        image.setSrc(zenTip.getImage());
        imageView.setOriginal(image);
        view.setImage(imageView);
        attachment.setViews(view);

        Button[] buttons = new Button[3];

        buttons[0] = new Button();
        buttons[0].setId("button-0-id");
        buttons[0].setIcon(LIKE_BUTTON_ICON);
        buttons[0].setName("Like");
        Action action = new Action();
        action.addDispatchEvent();
        buttons[0].setAction(action);

        buttons[1] = new Button();
        buttons[1].setId("button-1-id");
        buttons[1].setIcon(THANKS_BUTTON_ICON);
        buttons[1].setName("Say Thanks");
        action = new Action();
        action.addDispatchEvent();
        buttons[1].setAction(action);

        buttons[2] = new Button();
        buttons[2].setId("button-2-id");
        buttons[2].setIcon(TWEET_BUTTON_ICON);
        buttons[2].setName("Tweet");
        action = new Action();
        action.addDispatchEvent();
        buttons[2].setAction(action);

        attachment.setButtons(buttons);

        Attachment[] attachments = new Attachment[1];
        attachments[0] = attachment;

        message.setAttachments(attachments);
        MessagingService.sendMessage(message);
    }

}
