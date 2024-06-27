package polimi.ingsoft.server.model.chat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.exceptions.MatchExceptions.NotValidMessageException;
import polimi.ingsoft.server.model.cards.GameCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private Chat chat;

    /**
     * Sets up the test environment by creating a new instance of Chat before each test.
     */
    @BeforeEach
    public void createChat() {
        chat = new Chat();
    }

    /**
     * Verifies that a single message is correctly added to the chat.
     */
    @Test
    public void shouldAddSingleMessage() {
        String playerSender = "Player1";
        String textMessage = "Gianni il cuoco";
        chat.addMessage(playerSender, textMessage);

        assertEquals(1, chat.getMessages().size());
    }

    /**
     * Verifies that multiple messages are correctly added to the chat.
     */
    @Test
    public void shouldAddManyMessages() {
        int messagesNumber = 20;
        String textMessage = "Gianni la papera";
        for(int i = 0 ; i < messagesNumber ; i++){
            chat.addMessage("Player1", textMessage);
        }

        assertEquals(messagesNumber, chat.getMessages().size());
    }

    /**
     * Verifies that adding an empty message throws NotValidMessageException.
     */
    @Test
    public void shouldThrowExceptionForEmptyText() {
        String playerSender = "Player1";
        String textMessage = "";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender, textMessage));
    }

    /**
     * Verifies that adding a null message throws NotValidMessageException.
     */
    @Test
    public void shouldThrowExceptionForNullText() {
        String playerSender = "Player1";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(playerSender, null));
    }

    /**
     * Verifies that adding a message with a null sender throws NotValidMessageException.
     */
    @Test
    public void shouldThrowExceptionForNullSender() {
        String textMessage = "Gianni la papera";

        assertThrows(NotValidMessageException.class, () -> chat.addMessage(null, textMessage));
    }

    static class GameCardTest {
        static GameCard t;
        static Face front,back;
        static int score=1;
        static CornerSpace front1,front2,front3,front4,back1,back2,back3,back4;
        static ArrayList<Item> b=new ArrayList<Item>(),c=new ArrayList<Item>(),d=new ArrayList<Item>(), f=new ArrayList<Item>(),g=new ArrayList<Item>(),h=new ArrayList<Item>(),i=new ArrayList<Item>();
        static ArrayList<Resource> e=new ArrayList<Resource>();
        static CenterSpace center;

        /**
         * Sets up the test environment by initializing static variables before all tests.
         */
        @BeforeAll
        public static void init() {
            c.add(Resource.BUTTERFLY);
            d.add(Resource.BUTTERFLY);
            e.add(Resource.BUTTERFLY);
            center=new CenterSpace(e);
            front1=new CornerSpace(null);
            front2=new CornerSpace(b);
            front3=new CornerSpace(c);
            front4=new CornerSpace(d);
            front=new Face(front1,front2,front3,front4);
            back1=new CornerSpace(f);
            back2=new CornerSpace(g);
            back3=new CornerSpace(h);
            back4=new CornerSpace(i);
            back=new Face(back1,back2,back3,back4,center);
            t=new ResourceCard("id",front,back,score);
        }

        /**
         * Verifies the getScore method of GameCard.
         */
        @Test
        void getScore() {
            assertEquals(1,t.getScore(false));
            assertEquals(0,t.getScore(true));
        }

        /**
         * Verifies the getFront method of GameCard.
         */
        @Test
        void getFront() {
            assertEquals(front,t.getFront());
        }

        /**
         * Verifies the getBack method of GameCard.
         */
        @Test
        void getBack() {
            assertEquals(back,t.getBack());
        }

        /**
         * Verifies the getUpLeftCorner method of GameCard.
         */
        @Test
        void getUpLeftCorner() {
            assertEquals(front1,t.getUpLeftCorner(true));
            assertEquals(back1,t.getUpLeftCorner(false));
        }

        /**
         * Verifies the getUpRightCorner method of GameCard.
         */
        @Test
        void getUpRightCorner() {
            assertEquals(front2,t.getUpRightCorner(true));
            assertEquals(back2,t.getUpRightCorner(false));
        }

        /**
         * Verifies the getBottomLeftCorner method of GameCard.
         */
        @Test
        void getBottomLeftCorner() {
            assertEquals(front3,t.getBottomLeftCorner(true));
            assertEquals(back3,t.getBottomLeftCorner(false));
        }

        /**
         * Verifies the getBottomRightCorner method of GameCard.
         */
        @Test
        void getBottomRightCorner() {
            assertEquals(front4,t.getBottomRightCorner(true));
            assertEquals(back4,t.getBottomRightCorner(false));
        }
    }

    static class MessageTest {
        /**
         * Verifies that a Message object is correctly created.
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
         * Verifies the getText method of Message.
         */
        @Test
        public void shouldGetText() {
            String textMessage = "Gianni il cuoco";
            Message message = new Message("Gianni", textMessage);

            assertEquals(message.getText(), textMessage);
        }

        /**
         * Verifies the getSender method of Message.
         */
        @Test
        public void shouldGetSender() {
            String playerSender = "Gianni";
            Message message = new Message(playerSender, "Gianni il cuoco");

            assertEquals(message.getSender(), playerSender);
        }

        /**
         * Verifies the printable format of the message.
         */
        @Test
        public void shouldGetPrintableFormatMessage() {
            String playerSender = "Gianni";
            String textMessage = "Gianni il cuoco";
            Message message = new Message(playerSender, textMessage);

            assertEquals(message.printable(), playerSender + ": " + textMessage);
        }
    }
}