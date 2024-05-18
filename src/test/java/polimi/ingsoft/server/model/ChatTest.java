package polimi.ingsoft.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.exceptions.NotValidMessageException;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private Chat chat;

    @BeforeEach
    public void createChat() {
        chat = new Chat();
    }

    @Test
    public void shouldAddSingleMessage() {
        PlayerColors playerSender = PlayerColors.GREEN;
        String textMessage = "Gianni il cuoco";
        chat.addMessage(playerSender.toString(), textMessage);

        assertEquals(1, chat.getMessages().size());
    }

    @Test
    public void shouldAddManyMessages() {
        int messagesNumber = 20;
        String textMessage = "Gianni la papera";
        for(int i = 0 ; i < messagesNumber ; i++){
            chat.addMessage(PlayerColors.GREEN.toString(), textMessage);
        }

        assertEquals(messagesNumber, chat.getMessages().size());
    }

    @Test
    public void shouldThrowExceptionForEmptyText() {
        PlayerColors playerSender = PlayerColors.GREEN;
        String textMessage = "";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender.toString(), textMessage));
    }

    @Test
    public void shouldThrowExceptionForNullText() {
        PlayerColors playerSender = PlayerColors.GREEN;

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender.toString(), null));
    }

    @Test
    public void shouldThrowExceptionForNullSender() {
        String textMessage = "Gianni la papera";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(null, textMessage));
    }

}