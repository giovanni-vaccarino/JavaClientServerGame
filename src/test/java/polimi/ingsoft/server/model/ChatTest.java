package polimi.ingsoft.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        String playerSender = "Player1";
        String textMessage = "Gianni il cuoco";
        chat.addMessage(playerSender, textMessage);

        assertEquals(1, chat.getMessages().size());
    }

    @Test
    public void shouldAddManyMessages() {
        int messagesNumber = 20;
        String textMessage = "Gianni la papera";
        for(int i = 0 ; i < messagesNumber ; i++){
            chat.addMessage("Player1", textMessage);
        }

        assertEquals(messagesNumber, chat.getMessages().size());
    }

    @Test
    public void shouldThrowExceptionForEmptyText() {
        String playerSender = "Player1";
        String textMessage = "";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender, textMessage));
    }

    @Test
    public void shouldThrowExceptionForNullText() {
        String playerSender = "Player1";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender, null));
    }

    @Test
    public void shouldThrowExceptionForNullSender() {
        String textMessage = "Gianni la papera";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(null, textMessage));
    }

}