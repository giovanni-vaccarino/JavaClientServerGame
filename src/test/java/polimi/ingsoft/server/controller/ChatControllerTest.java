package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.exceptions.MatchExceptions.PlayerNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {
    static ChatController controller;

    @BeforeEach
    void init() {
    controller=new ChatController();
    }

    @Test
    void addPrivateChat() throws PlayerNotFoundException {
        assertThrows(PlayerNotFoundException.class,() -> controller.writePrivateMessage("caso","caso2","ciao"));
        controller.addPrivateChat("caso");
        controller.writePrivateMessage("caso2","caso","caso");
    }

    @Test
    void writeBroadcastMessage() {
        assertNotNull(controller.writeBroadcastMessage("caso","ciao a tutti"));
    }

    @Test
    void writePrivateMessage() throws PlayerNotFoundException {
        assertThrows(PlayerNotFoundException.class,() -> controller.writePrivateMessage("caso","caso2","ciao"));
        controller.addPrivateChat("caso");
        controller.writePrivateMessage("caso2","caso","caso");
    }
}