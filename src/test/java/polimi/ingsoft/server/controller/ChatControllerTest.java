package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.exceptions.MatchExceptions.PlayerNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChatController.
 */

class ChatControllerTest {
    static ChatController controller;

    /**
     * Initializes the ChatController before each test.
     */
    @BeforeEach
    void init() {
    controller=new ChatController();
    }

    /**
     * Tests adding a private chat and writing a private message.
     *
     * @throws PlayerNotFoundException if the player is not found.
     */
    @Test
    void addPrivateChat() throws PlayerNotFoundException {
        assertThrows(PlayerNotFoundException.class,() -> controller.writePrivateMessage("caso","caso2","ciao"));
        controller.addPrivateChat("caso");
        controller.writePrivateMessage("caso2","caso","caso");
    }

    /**
     * Tests writing a broadcast message.
     */
    @Test
    void writeBroadcastMessage() {
        assertNotNull(controller.writeBroadcastMessage("caso","ciao a tutti"));
    }

    /**
     * Tests writing a private message.
     *
     * @throws PlayerNotFoundException if the player is not found.
     */
    @Test
    void writePrivateMessage() throws PlayerNotFoundException {
        assertThrows(PlayerNotFoundException.class,() -> controller.writePrivateMessage("caso","caso2","ciao"));
        controller.addPrivateChat("caso");
        controller.writePrivateMessage("caso2","caso","caso");
    }
}