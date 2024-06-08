package polimi.ingsoft.server.model;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.chat.Message;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    public void shouldCreateMessage() {
        String playerSender = "Gianni";
        String textMessage = "Gianni il cuoco";

        Message message = new Message(playerSender, textMessage);

        assertEquals(message.getSender(), playerSender);
        assertEquals(message.getText(), textMessage);
    }

    @Test
    public void shouldGetText() {
        String textMessage = "Gianni il cuoco";
        Message message = new Message("Gianni", textMessage);

        assertEquals(message.getText(), textMessage);
    }

    @Test
    public void shouldGetSender() {
        String playerSender = "Gianni";
        Message message = new Message(playerSender, "Gianni il cuoco");

        assertEquals(message.getSender(), playerSender);
    }

    @Test
    public void shouldGetPrintableFormatMessage() {
        String playerSender = "Gianni";
        String textMessage = "Gianni il cuoco";
        Message message = new Message(playerSender, textMessage);

        assertEquals(message.printable(), playerSender + ": " + textMessage);
    }
}