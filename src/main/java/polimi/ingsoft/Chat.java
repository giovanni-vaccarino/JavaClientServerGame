package polimi.ingsoft;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    List<Message> messages;
    public Chat(){
        messages=new ArrayList<Message>();
    }
    public void newMessage(String sender, String text){ // il sender sarà un'enumerazione(giallo, verde....)
        messages.add(new Message(sender,text));
        System.out.println(sender+text); //inserire metodo per stampare ultimo messaggio
        }
    public void updatePrintedChat(){}
}
