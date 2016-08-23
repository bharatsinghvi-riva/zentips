import org.json.JSONObject;

import static spark.Spark.*;

public class ZenRunner {

    public static void main(String[] args) {
        ZenRunner.startServer();
    }

    private static void startServer() {
        port(8000);

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
