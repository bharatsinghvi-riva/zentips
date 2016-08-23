import model.ZenTip;

public class MockZenServer {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ZentipAPI.createMessage(new ZenTip("A", "http://what-buddha-said.net/Pics/Buddha.blue.star.jpg", "http://www.google.com", "u:fhm6yka0mms6yffx", "u:1ll9h9h18l9liggl"));
        }
    }
}
