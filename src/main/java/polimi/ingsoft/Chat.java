package polimi.ingsoft;
import java.util.ArrayList;

public class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();

    public void addMessage(PlayerColors sender, String text) {
        messages.add(new Message(sender, text));
    }

    public String printable() {
        return "";
    }

    public void updatePrintedChat() { }
}
