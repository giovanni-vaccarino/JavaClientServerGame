package polimi.ingsoft.client.ui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.MatchData;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.model.player.PlayerColor;
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
import java.util.List;
import java.util.Map;

/**
 * Abstract class representing the user interface for the client.
 * This class provides methods to interact with the client and server, and manage the game state.
 */
public abstract class UI implements Serializable {
    private transient final Client client;
    private String nickname;
    private String stub;

    private Boolean isUpdateMatchRequested = false;

    /**
     * Constructs a UI object with the specified client.
     *
     * @param client the client to be associated with this UI
     */
    public UI(Client client) {
        this.client = client;
    }

    /**
     * Displays the welcome screen.
     *
     * @throws IOException if an I/O error occurs
     */
    public abstract void showWelcomeScreen() throws IOException;

    /**
     * Updates the nickname of the player.
     */
    public abstract void updateNickname();

    /**
     * Updates the list of matches.
     *
     * @param matches the list of matches to update
     */
    public abstract void updateMatchesList(List<MatchData> matches);

    /**
     * Shows the update for match join.
     */
    public abstract void showUpdateMatchJoin();

    /**
     * Updates the list of players in the lobby.
     *
     * @param nicknames the list of player nicknames in the lobby
     */
    public abstract void updatePlayersInLobby(List<String> nicknames);

    /**
     * Displays the created match with the specified match ID.
     *
     * @param matchId the ID of the created match
     */
    public abstract void showMatchCreate(Integer matchId);

    /**
     * Reports an error with the specified error message.
     *
     * @param errorMessage the error message to report
     */
    public abstract void reportError(ERROR_MESSAGES errorMessage);

    /**
     * Shows the update for the game state.
     *
     * @param gameState the game state to update
     */
    public abstract void showUpdateGameState(GameState gameState);

    /**
     * Shows the update for initial settings of the player.
     *
     * @param playerInitialSetting the initial settings of the player
     */
    public abstract void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting);

    /**
     * Creates the public board with the specified resource cards, gold cards, and quest cards.
     *
     * @param resourceCards the resource cards for the public board
     * @param goldCards     the gold cards for the public board
     * @param questCards    the quest cards for the public board
     */
    public abstract void createPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards);

    /**
     * Updates the public board with the specified deck type and place in public board.
     *
     * @param deckType         the type of hand card deck
     * @param placeInPublicBoard the place in public board to update
     */
    public abstract void updatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard);

    /**
     * Updates the player board with the specified nickname, coordinates, played card, and score.
     *
     * @param nickname    the nickname of the player
     * @param coordinates the coordinates of the played card
     * @param playedCard  the played card
     * @param score       the score to update
     */
    public abstract void updatePlayerBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score);

    /**
     * Sets the player boards with the specified map of player boards.
     *
     * @param playerBoard the map of player boards to set
     */
    public abstract void setPlayerBoards(Map<String, Board> playerBoard);

    /**
     * Updates the player hand with the specified player hand.
     *
     * @param playerHand the player hand to update
     */
    public abstract void updatePlayerHand(PlayerHand playerHand);

    /**
     * Updates the broadcast chat with the specified message.
     *
     * @param message the message to add to the broadcast chat
     */
    public abstract void updateBroadcastChat(Message message);

    /**
     * Updates the private chat with the specified receiver and message.
     *
     * @param receiver the receiver of the private message
     * @param message  the private message to add to the chat
     */
    public abstract void updatePrivateChat(String receiver, Message message);

    /**
     * Sets the model for rejoining a match with the specified parameters.
     *
     * @param playerInitialSetting the initial settings of the player
     * @param gameState            the game state
     * @param resource             the resource cards for the public board
     * @param gold                 the gold cards for the public board
     * @param quest                the quest cards for the public board
     * @param boards               the map of player boards
     * @param playerHand           the player hand
     */
    public abstract void setRejoinMatchModel(PlayerInitialSetting playerInitialSetting,
                                             GameState gameState,
                                             PlaceInPublicBoard<ResourceCard> resource,
                                             PlaceInPublicBoard<GoldCard> gold,
                                             PlaceInPublicBoard<QuestCard> quest,
                                             Map<String, Board> boards,
                                             PlayerHand playerHand);

    /**
     * Gets the client instance.
     *
     * @return the client instance
     */
    public VirtualView getClient() {
        return client;
    }

    /**
     * Gets the server instance.
     *
     * @return the server instance
     */
    public VirtualServer getServer() {
        return client.getServer();
    }

    /**
     * Gets the match server instance.
     *
     * @return the match server instance
     */
    public VirtualMatchServer getMatchServer() {
        return client.getMatchServer();
    }

    /**
     * Gets the nickname of the player.
     *
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the stub nickname.
     *
     * @param stubNickname the stub nickname to set
     */
    public void setStubNickname(String stubNickname) {
        this.stub = stubNickname;
    }

    /**
     * Gets the stub.
     *
     * @return the stub
     */
    public String getStub() {
        return this.stub;
    }

    /**
     * Sets whether an update match is requested.
     *
     * @param isUpdateMatchRequested true if an update match is requested, false otherwise
     */
    public void setIsUpdateMatchRequested(Boolean isUpdateMatchRequested) {
        this.isUpdateMatchRequested = isUpdateMatchRequested;
    }

    /**
     * Gets whether an update match is requested.
     *
     * @return true if an update match is requested, false otherwise
     */
    public Boolean getIsUpdateMatchRequested() {
        return this.isUpdateMatchRequested;
    }

    /**
     * Sets the nickname of the player and updates the server with the nickname.
     *
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
        try {
            getServer().setNickname(nickname, this.stub);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the color of the player.
     *
     * @param playerColor the player color to set
     */
    public void setColor(PlayerColor playerColor) {
        try {
            getMatchServer().setColor(nickname, playerColor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets whether the initial card is facing up.
     *
     * @param isFaceInitialCardUp true if the initial card is facing up, false otherwise
     */
    public void setIsFaceInitialCardUp(boolean isFaceInitialCardUp) {
        try {
            getMatchServer().setIsInitialCardFacingUp(nickname, isFaceInitialCardUp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the quest card for the player.
     *
     * @param questCard the quest card to set
     */
    public void setQuestCard(QuestCard questCard) {
        try {
            getMatchServer().setQuestCard(nickname, questCard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
