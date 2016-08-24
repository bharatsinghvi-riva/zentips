import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;

public class MessagingService {

    private static final String USER_TOKEN = "81af130a-59bd-4a5e-b112-70166fefcaea";

    public static void sendMessage(String token, Message message) {
        FlockApiClient flockApiClient = new FlockApiClient(token, false);
        FlockMessage flockMessage = new FlockMessage(message);
        try {
            String response = flockApiClient.chatSendMessage(flockMessage);
            System.out.println("Send message response:" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
