import co.flock.www.model.messages.Message;
import model.Subscriptions;
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
            String type = (String) jsonObject.get("name");
            if (type.equalsIgnoreCase("app.install")) {
                handleAppInstall(jsonObject);
            } else if (type.equalsIgnoreCase("client.slashCommand")) {
                handleSlashCommand(jsonObject);
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
    }

    private static void handleSlashCommand(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String text = jsonObject.getString("text");
        if (text.startsWith("topics")) {
            ZentipAPI.getTags(userId);
        } else if (text.startsWith("subscribe")) {
            new Subscriptions().addSubscription(text.substring("subscribe".length()), userId);
        } else if (text.startsWith("subscriptions")) {
            MessagingService.sendMessage(new Message(userId, new Subscriptions().getSubscriptionsForUser(userId).toString()));
        } else if (text.startsWith("unsubscribe")) {
            MessagingService.sendMessage(new Message(userId, "Unsubscribe successful"));
        }
    }

    private static void handleNewTipCreation(JSONObject jsonObject) {
        String tag = jsonObject.getString("tag");
        String image = jsonObject.getString("image");
        String message = jsonObject.getString("message");
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");
        MessagingService.sendMessage(new Message(from, "test"));
        ZenTip zenTip = new ZenTip(tag, image, message, from, to);
        ZentipAPI.createMessage(zenTip);
    }

}
