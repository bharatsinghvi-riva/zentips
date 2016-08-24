import co.flock.www.model.messages.Message;
import model.Followers;
import model.Subscriptions;
import model.User;
import model.ZenTip;
import org.json.JSONObject;
import spark.ModelAndView;

import static spark.Spark.*;

import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class ZenServer {

    public static void main(String[] args) {
        new ZentipAPI();
        ZenServer.startServer();
    }

    private static void startServer() {
        port(8000);

        staticFileLocation("/public");
        HashMap<String, String> map = new HashMap<>();
        map.put("resourcePrefix", "");
        get("/new", (req, res) -> new ModelAndView(map, "newInfoTip.html"),
                new MustacheTemplateEngine());

        post("/create", (req, res) -> {
            JSONObject jsonObject = new JSONObject(req.body());
            System.out.println(req.body());
            handleNewTipCreation(jsonObject);
            return "New tip created";
        });

        post("/", (req, res) -> {
            JSONObject jsonObject = new JSONObject(req.body());
            System.out.println(req.body());
            String type = (String) jsonObject.get("name");
            if (type.equalsIgnoreCase("app.install")) {
                handleAppInstall(jsonObject);
            } else if (type.equalsIgnoreCase("client.slashCommand")) {
                handleSlashCommand(jsonObject);
            } else if (type.equalsIgnoreCase("client.pressButton")) {
                handleAttachmentButton(jsonObject);
            }
            res.status(200);
            return "";
        });

    }

    private static void handleAppInstall(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String userToken = jsonObject.getString("userToken");
        System.out.println("Userid: " + userId);
        System.out.println("userToken: " + userToken);
        User user = new User(userId, userToken);
        ZentipAPI.addUser(user);
    }

    private static void handleSlashCommand(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String text = jsonObject.getString("text");
        if (text.startsWith("topics")) {
            ZentipAPI.getTags(userId);
        } else if (text.startsWith("subscribe")) {
            new Subscriptions().addSubscription(text.substring("subscribe".length()), userId);
        } else if (text.startsWith("subscriptions")) {
            MessagingService.sendMessage(ZentipAPI.getUserToken(userId), new Message(userId, new Subscriptions().getSubscriptionsForUser(userId).toString()));
        } else if (text.startsWith("unsubscribe")) {
            MessagingService.sendMessage(ZentipAPI.getUserToken(userId), new Message(userId, "Unsubscribe successful"));
        }
    }

    private static void handleAttachmentButton(JSONObject jsonObject) {
        String buttonId = jsonObject.getString("buttonId");
        String userId = jsonObject.getString("userId");
        if (buttonId.startsWith("follow")) {
            String split[] = buttonId.split("-");
            new Followers().addFollower(split[1], userId);
        } else if (buttonId.startsWith("unfollow")) {
            String split[] = buttonId.split("-");
            new Followers().removeFollower(split[1], userId);
        } else if (buttonId.startsWith("subscribe")) {
            String split[] = buttonId.split("-");
            new Subscriptions().addSubscription(split[1], userId);
        } else if (buttonId.startsWith("unsubscribe")) {
            String split[] = buttonId.split("-");
            new Subscriptions().removeSubscription(split[1], userId);
        } else if (buttonId.startsWith("appreciate")) {
            String split[] = buttonId.split("-");
            MessagingService.sendMessage(ZentipAPI.getUserToken(userId), new Message(split[1], "Awesome article!"));
        }
    }

    private static void handleNewTipCreation(JSONObject jsonObject) {
        String tag = jsonObject.getString("tag");
        String image = jsonObject.getString("image");
        String message = jsonObject.getString("message");
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");
        ZenTip zenTip = new ZenTip(tag, image, message, from, to);
        ZentipAPI.createMessage(zenTip);
        for (String userId: new Subscriptions().getSubscribersForTag(tag)) {
            zenTip = new ZenTip(tag, image, message, from, userId);
            ZentipAPI.createMessage(zenTip);
        }
        for (String userId: new Followers().getFollowers(from)) {
            zenTip = new ZenTip(tag, image, message, from, userId);
            ZentipAPI.createMessage(zenTip);
        }
    }

}
