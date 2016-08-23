import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;

public class MessagingService {

    private static final String BOT_TOKEN = "53691730-d1e1-4f46-aa52-e6a6569bee44";
    private static final String USER_TOKEN = "92b93fcb-965e-4767-a0c9-d720d735f0c1";

    public static void sendMessage(Message message) {
        FlockApiClient flockApiClient = new FlockApiClient(USER_TOKEN, false);
        FlockMessage flockMessage = new FlockMessage(message);
        try {
            String response = flockApiClient.chatSendMessage(flockMessage);
            System.out.println("Send message response:" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
