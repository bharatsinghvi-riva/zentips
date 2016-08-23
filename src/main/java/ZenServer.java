import org.json.JSONObject;
import spark.ModelAndView;

import static spark.Spark.*;

import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class ZenServer {

    public static void main(String[] args) {
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
            String body = req.body();
            System.out.println(body);
            return "Info tip created";
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
    }

}
