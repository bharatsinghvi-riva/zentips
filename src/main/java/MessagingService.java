import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;
import co.flock.www.model.messages.SendAs;

public class MessagingService {

    private static final String BOT_TOKEN = "53691730-d1e1-4f46-aa52-e6a6569bee44";

    private static void sendMessage(String token, Message message) {
        SendAs sendAs = new SendAs("Zen Bot", "https://f.flock.co/a49cd4b1471976484521433a");
        message.setSendAs(sendAs);
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
