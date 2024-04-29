package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.ChatController;

public class ChatControllerFactory {
    public static ChatController createChatController() {
        return new ChatController();
    }
}
