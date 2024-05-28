package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.MatchExceptions.PlayerNotFoundException;
import polimi.ingsoft.server.model.Chat;
import polimi.ingsoft.server.model.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChatController implements Serializable {
    private final Map<String, Chat> privateChats;
    private final Chat broadcastChat;

    public ChatController() {
        privateChats = new HashMap<>();
        broadcastChat = new Chat();
    }

    public void addPrivateChat(String nickname) {
        privateChats.put(nickname, new Chat());
    }

    public Message writeBroadcastMessage(String sender, String message) {
        return broadcastChat.addMessage(sender, message);
    }

    public Message writePrivateMessage(String sender, String recipient, String message) throws PlayerNotFoundException {
        Chat chat = privateChats.get(recipient);
        if (chat == null)
            throw new PlayerNotFoundException();

        return chat.addMessage(sender, message);
    }
}
