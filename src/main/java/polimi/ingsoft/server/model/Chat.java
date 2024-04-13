package polimi.ingsoft.server.model;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.exceptions.NotValidMessageException;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();

    public void addMessage(PlayerColors sender, String text) throws NotValidMessageException{
        if (sender == null || text == null || text.isEmpty()) {
            throw new NotValidMessageException();
        }

        messages.add(new Message(sender, text));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
