package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.model.Chat;
import polimi.ingsoft.server.model.Message;

import java.io.Serializable;

public class ChatController implements Serializable {
    private final Chat gameChat;

    public ChatController() {
        this.gameChat = new Chat();
    }

    public Message writeMessage(String sender, String message){
        return this.gameChat.addMessage(sender, message);
    }
}
