package polimi.ingsoft.server.model.chat;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.chat.Message;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Message} class.
 */
class MessageTest {

    /**
     * Tests the creation of a {@link Message} object.
     * Verifies that the sender and text are correctly assigned.
     */
    @Test
    public void shouldCreateMessage() {
        String playerSender = "Gianni";
        String textMessage = "Gianni il cuoco";

        Message message = new Message(playerSender, textMessage);

        assertEquals(message.getSender(), playerSender);
        assertEquals(message.getText(), textMessage);
    }

    /**
     * Tests the {@link Message#getText()} method.
     * Verifies that the text of the message is correctly returned.
     */
    @Test
    public void shouldGetText() {
        String textMessage = "Gianni il cuoco";
        Message message = new Message("Gianni", textMessage);

        assertEquals(message.getText(), textMessage);
    }

    /**
     * Tests the {@link Message#getSender()} method.
     * Verifies that the sender of the message is correctly returned.
     */
    @Test
    public void shouldGetSender() {
        String playerSender = "Gianni";
        Message message = new Message(playerSender, "Gianni il cuoco");

        assertEquals(message.getSender(), playerSender);
    }

    /**
     * Tests the {@link Message#printable()} method.
     * Verifies that the printable format of the message is correctly returned.
     */
    @Test
    public void shouldGetPrintableFormatMessage() {
        String playerSender = "Gianni";
        String textMessage = "Gianni il cuoco";
        Message message = new Message(playerSender, textMessage);

        assertEquals(message.printable(), playerSender + ": " + textMessage);
    }
}