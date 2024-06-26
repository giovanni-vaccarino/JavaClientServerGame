package polimi.ingsoft.server.common;

import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;

/**
 * The VirtualMatchServer interface defines the methods for effectively play a match.
 */
public interface VirtualMatchServer extends Remote, Serializable {
    /**
     * Send a generic match server command to be executed
     *
     * @param command a match server command object to be sent to the server
     * @throws IOException if the communication is interrupted
     */
    void sendMessage(MatchServerCommand command) throws IOException;

    /**
     * Request to set the color for a player, if available
     *
     * @param nickname client sending the request
     * @param color requested color
     * @throws IOException if the communication is interrupted
     */
    void setColor(String nickname, PlayerColor color) throws IOException;

    /**
     * Request to set the initial card face up or down
     *
     * @param nickname client sending the request
     * @param isInitialCardFacingUp initial card face
     * @throws IOException if the communication is interrupted
     */
    void setIsInitialCardFacingUp(String nickname, Boolean isInitialCardFacingUp) throws IOException;

    /**
     * Request to set the personal quest card
     *
     * @param nickname client sending the request
     * @param questCard the personal quest card
     * @throws IOException if the communication is interrupted
     */
    void setQuestCard(String nickname, QuestCard questCard) throws IOException;

    /**
     * Request to send a broadcast message to the chat
     *
     * @param player client sending the request
     * @param message message text
     * @throws IOException if the communication is interrupted
     */
    void sendBroadcastMessage(String player, String message) throws IOException;

    /**
     * Request to send a private message to another player
     *
     * @param player client sending the request
     * @param recipient client receiving the message
     * @param message message text
     * @throws IOException if the communication is interrupted
     */
    void sendPrivateMessage(String player, String recipient, String message) throws IOException;

    /**
     * Request to draw a card from a given slot in the public board
     *
     * @param player client sending the request
     * @param deckType public board type the client is trying to draw from
     * @param slot slot in the public board the client is trying to draw from
     * @throws IOException if the communication is interrupted
     */
    void drawCard(String player, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws IOException;

    /**
     * Request to place a card in the client's board
     *
     * @param player client sending the request
     * @param card card the client is trying to place
     * @param coordinates coordinates where the client is trying to place the card in the board
     * @param facingUp face of the card (true if the card is facing up)
     * @throws IOException if the communication is interrupted
     */
    void placeCard(String player, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException;

    /**
     * Ping request / Response to a pong from a client
     *
     * @param nickname client sending the request
     * @throws IOException if the communication is interrupted
     */
    void ping(String nickname) throws IOException;
}
