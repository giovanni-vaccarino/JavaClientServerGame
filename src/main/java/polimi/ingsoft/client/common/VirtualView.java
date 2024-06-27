package polimi.ingsoft.client.common;


import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.common.command.ClientCommand;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Represents a client or its proxy
 */
public interface VirtualView extends Remote, Serializable {
    void sendMessage(ClientCommand command) throws IOException;


    /**
     * Updates the client that the connection was
     * established successfully
     *
     * @param stubNickname The random string assigned to the client
     *                     until its nickname is set explicitly
     * @throws IOException If an I/O error occurs.
     */
    void showConnectUpdate(String stubNickname) throws IOException;


    /**
     * Updates the nickname in the UI.
     *
     * @throws IOException If an I/O error occurs.
     */
    void showNicknameUpdate() throws IOException;


    /**
     * Updates the matches list in the UI.
     *
     * @param matches The list of match IDs.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateMatchesList(List<MatchData> matches) throws IOException;


    /**
     * Ensures that match join has been successful.
     *
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateMatchJoin() throws IOException;


    /**
     * Updates the lobby players list in the UI.
     *
     * @param players The list of player nicknames.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateLobbyPlayers(List<String> players) throws IOException;


    /**
     * Updates the match creation information in the UI.
     *
     * @param matchId The ID of the created match.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateMatchCreate(Integer matchId) throws IOException;


    /**
     * Updates the broadcast chat in the UI.
     *
     * @param message The broadcast message.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateBroadcastChat(Message message) throws IOException;


    /**
     * Updates the private chat in the UI.
     *
     * @param recipient The recipient of the message.
     * @param message   The private message.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdatePrivateChat(String recipient, Message message) throws IOException;


    /**
     * Updates the initial settings of a player in the UI.
     *
     * @param playerInitialSetting The initial settings of the player.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) throws IOException;


    /**
     * Updates the game state in the UI.
     *
     * @param gameState The current game state.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateGameState(GameState gameState) throws IOException;


    /**
     * Updates the match start information in the UI.
     *
     * @param resource The resource placeInPublicboard .
     * @param gold     The gold placeInPublicboard.
     * @param quest    The quest placeInPublicboard.
     * @param boards   The player boards.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateGameStart(
            PlaceInPublicBoard<ResourceCard> resource,
            PlaceInPublicBoard<GoldCard> gold,
            PlaceInPublicBoard<QuestCard> quest,
            Map<String, Board> boards
    ) throws IOException;


    /**
     * Updates the player's hand in the UI.
     *
     * @param playerHand The player's hand.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdatePlayerHand(PlayerHand playerHand) throws IOException;


    /**
     * Updates the public board in the UI.
     *
     * @param deckType          The type of deck.
     * @param placeInPublicBoard The place in the public board.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException;


    /**
     * Updates the board for a specific player in the UI.
     *
     * @param nickname   The nickname of the player.
     * @param coordinates The coordinates where the card is placed.
     * @param playedCard The played card.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) throws IOException;


    /**
     * Reports an error message in the UI.
     *
     * @param errorMessage The error message to be reported.
     * @throws IOException If an I/O error occurs.
     */
    void reportError(ERROR_MESSAGES errorMessage) throws IOException;

    /**
     * Sets the match server.
     *
     * @param server
     * @throws IOException If an I/O error occurs.
     */
    void setMatchServer(VirtualMatchServer server) throws IOException;

    /**
     * Sends a pong to a client. A pong is a ping request from the server.
     *
     * @throws IOException If an I/O error occurs.
     */
    void pong() throws IOException;

    /**
     * Sends a match pong to a client. A match pong is a ping request from a match server.
     *
     * @throws IOException If an I/O error occurs.
     */
    void matchPong() throws IOException;

    /**
     * Updates the client of a successful match rejoin after a disconnection.
     *
     * @param playerInitialSetting Original player initial settings.
     * @param gameState Current match game state.
     * @param resource Resource public board.
     * @param gold Gold public board.
     * @param quest Quest cards public board.
     * @param boards Player boards.
     * @param playerHand Last player hand.
     * @throws IOException If an I/O error occurs.
     */
    void showUpdateRejoinMatch(PlayerInitialSetting playerInitialSetting,
                               GameState gameState,
                               PlaceInPublicBoard<ResourceCard> resource,
                               PlaceInPublicBoard<GoldCard> gold,
                               PlaceInPublicBoard<QuestCard> quest,
                               Map<String, Board> boards,
                               PlayerHand playerHand) throws IOException;
}
