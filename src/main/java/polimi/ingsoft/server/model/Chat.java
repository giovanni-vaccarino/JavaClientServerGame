package polimi.ingsoft.server.model;
import polimi.ingsoft.server.exceptions.MatchExceptions.NotValidMessageException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Chat class
 */
public class Chat implements Serializable {
    private final ArrayList<Message> messages = new ArrayList<>();

    /**
     * Adds a message to be sent broadcast
     * @param sender message's sender
     * @param text message's content
     * @return the last received message
     * @throws NotValidMessageException when null arguments or empty text are passed
     */

    public Message addMessage(String sender, String text) throws NotValidMessageException{
        if (sender == null || text == null || text.isEmpty()) {
            throw new NotValidMessageException();
        }

        messages.add(new Message(sender, text));

        return getLastMessage();
    }

    /**
     * Returns the last added message
     * @return the last added message
     */
    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    /**
     * Returns the messages' list
     * @return the messages' list
     */
    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
