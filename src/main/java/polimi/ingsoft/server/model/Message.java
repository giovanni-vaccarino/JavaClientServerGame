package polimi.ingsoft.server.model;

import java.io.Serializable;

public class Message implements Serializable {
    private final String sender;
    private final String text;

    public Message(String sender, String text){
        this.sender = sender;
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public String getSender() {
        return sender;
    }

    public String printable(){
        return this.sender + ": " + this.text;
    }
}
