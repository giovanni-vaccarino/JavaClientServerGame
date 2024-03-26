package polimi.ingsoft;

public class Message {
    private final PlayerColors sender;
    private final String text;

    public Message(PlayerColors sender, String text){
        this.sender = sender;
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public PlayerColors getSender() {
        return sender;
    }

    public String printable(){
        return this.sender + ": " + this.text;
    }
}
