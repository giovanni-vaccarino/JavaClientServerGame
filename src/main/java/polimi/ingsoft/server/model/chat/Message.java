package polimi.ingsoft.server.model.chat;

import java.io.Serializable;

/**
 * Represents a chat's message
 */
public class Message implements Serializable {
    private final String sender;
    private final String text;

    /**
     * Creates a new chat message
     * @param sender the message's sender
     * @param text the message's content
     */
    public Message(String sender, String text){
        this.sender = sender;
        this.text = text;
    }

    /**
     * Returns the message's content
     * @return the message's content
     */
    public String getText(){
        return this.text;
    }

    /**
     * Returns the message's sender
     * @return the message's sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Convert the message in a printable String
     * @return a printable String
     */
    public String printable(){
        return this.sender + ": " + this.text;
    }
}
