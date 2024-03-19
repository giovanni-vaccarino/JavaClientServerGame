package polimi.ingsoft;

public class Message {
    private String sender;
    private String text;

    public Message(String sender,String text){
        this.sender=new String(sender);
        this.text=new String(text);
    }
    public String getText(){
        return this.text;
    }
    public String getSender() {
        return sender;
    }
    public String printMessage(){ //questo metodo ci permette di decidere eventuale preindentazione-es qui sotto
        return(this.sender+": "+this.text); //questo metodo stampa messaggi di tipo "Giovanni: CIAO!"
    }

}
