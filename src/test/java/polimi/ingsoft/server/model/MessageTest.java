package polimi.ingsoft.server.model;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.PlayerColors;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    public void shouldCreateMessage() {
        PlayerColors playerSender = PlayerColors.GREEN;
        String textMessage = "Gianni il cuoco";

        Message message = new Message(playerSender, textMessage);

        assertEquals(message.getSender(), playerSender);
        assertEquals(message.getText(), textMessage);
    }

    @Test
    public void shouldGetText() {
        String textMessage = "Gianni il cuoco";
        Message message = new Message(PlayerColors.BLUE, textMessage);

        assertEquals(message.getText(), textMessage);
    }

    @Test
    public void shouldGetSender() {
        PlayerColors playerSender = PlayerColors.RED;
        Message message = new Message(playerSender, "Gianni il cuoco");

        assertEquals(message.getSender(), playerSender);
    }

    @Test
    public void shouldGetPrintableFormatMessage() {
        PlayerColors playerSender = PlayerColors.RED;
        String textMessage = "Gianni il cuoco";
        Message message = new Message(playerSender, textMessage);

        assertEquals(message.printable(), playerSender + ": " + textMessage);
    }
}