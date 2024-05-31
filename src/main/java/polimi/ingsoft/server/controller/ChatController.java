package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.MatchExceptions.PlayerNotFoundException;
import polimi.ingsoft.server.model.Chat;
import polimi.ingsoft.server.model.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The ChatController class is responsible for managing private
 * chats and broadcast chat.
 */
public class ChatController implements Serializable {
    private final Map<String, Chat> privateChats;
    private final Chat broadcastChat;

    public ChatController() {
        privateChats = new HashMap<>();
        broadcastChat = new Chat();
    }


    /**
     * Adds a private chat for a player.
     *
     * @param nickname the nickname of the player for whom the private chat is created
     */
    public void addPrivateChat(String nickname) {
        privateChats.put(nickname, new Chat());
    }


    /**
     * Sends a message to the broadcast chat.
     *
     * @param sender the nickname of the sender
     * @param message the text of the message
     * @return the Message object representing the added message
     */
    public Message writeBroadcastMessage(String sender, String message) {
        return broadcastChat.addMessage(sender, message);
    }


    /**
     * Sends a private message to a specific recipient.
     *
     * @param sender the nickname of the sender
     * @param recipient the nickname of the recipient
     * @param message the content of the message
     * @return the Message object representing the added message
     * @throws PlayerNotFoundException if the recipient is not found
     */
    public Message writePrivateMessage(String sender, String recipient, String message) throws PlayerNotFoundException {
        Chat chat = privateChats.get(recipient);
        if (chat == null)
            throw new PlayerNotFoundException();

        return chat.addMessage(sender, message);
    }
}
