package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.Chat;
import polimi.ingsoft.server.model.Message;

import java.io.Serializable;
import java.util.List;

public class ChatController implements Serializable {
    final Chat gameChat;
    public ChatController(){
        this.gameChat = new Chat();
    }

    public Message writeMessage(String message){
        synchronized (this.gameChat){
            return this.gameChat.addMessage(PlayerColors.RED, message);
        }
    }
}
