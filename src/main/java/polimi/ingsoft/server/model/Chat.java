package polimi.ingsoft.server.model;
import polimi.ingsoft.server.exceptions.MatchExceptions.NotValidMessageException;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private final ArrayList<Message> messages = new ArrayList<>();

    public Message addMessage(String sender, String text) throws NotValidMessageException{
        if (sender == null || text == null || text.isEmpty()) {
            throw new NotValidMessageException();
        }

        messages.add(new Message(sender, text));

        return getLastMessage();
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }
    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
