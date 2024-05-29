package polimi.ingsoft.client.common;

import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Client extends UnicastRemoteObject implements VirtualView, Runnable {
    private final UI ui;

    public Client(
            UIType uiType,
            PrintStream printStream,
            Scanner scanner
    ) throws RemoteException {
        super();
        if (uiType == UIType.CLI)
            ui = new CLI(scanner, printStream, this);
        else
            ui = new GUI(this);
    }


    /**
     * Gets the server instance.
     *
     * @return The VirtualServer instance.
     */
    protected abstract VirtualServer getServer();


    /**
     * Gets the match server instance.
     *
     * @return The VirtualMatchServer instance.
     */
    protected abstract VirtualMatchServer getMatchServer();


    /**
     * Sets the match controller server.
     *
     * @param controller The VirtualMatchServer instance.
     */
    @Override
    public abstract void setMatchControllerServer(VirtualMatchServer controller);

    public abstract void run();


    /**
     * Gets the UI instance.
     *
     * @return The UI instance.
     */
    public UI getUi(){return this.ui;}


    @Override
    public void showNicknameUpdate() throws IOException {
        ui.updateNickname();
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        ui.updateMatchesList(matches);
    }


    @Override
    public void showUpdateMatchJoin() throws IOException {
        ui.showUpdateMatchJoin();
    }


    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        ui.updatePlayersInLobby(players);
    }


    @Override
    public void showUpdateMatchCreate(Integer matchId) throws IOException {
        ui.showMatchCreate(matchId);
    }


    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) throws IOException {
        ui.showUpdateInitialSettings(playerInitialSetting);
    }


    @Override
    public void showUpdateGameState(GameState gameState) throws IOException{
        ui.showUpdateGameState(gameState);
    }


    @Override
    public void showUpdateGameStart(PlaceInPublicBoard<ResourceCard> resource, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quest, Map<String, Board> boards) throws IOException {
        ui.setPlayerBoards(boards);
        ui.createPublicBoard(resource, gold, quest);
    }


    @Override
    public void showUpdatePlayerHand(PlayerHand playerHand) throws IOException {
        ui.updatePlayerHand(playerHand);
    }


    @Override
    public void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException{
        ui.updatePublicBoard(deckType, placeInPublicBoard);
    }


    @Override
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard) throws IOException{
        ui.updatePlayerBoard(nickname, coordinates, playedCard);
        System.out.println(nickname+" --x: "+coordinates.getX()+" --y: "+coordinates.getY());
    }

    @Override
    public void showUpdateBroadcastChat(Message message) throws IOException {

    }

    @Override
    public void showUpdatePrivateChat(String recipient, Message message) throws IOException {

    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) throws IOException {
        ui.reportError(errorMessage);
    }


    /**
     * Sets the nickname for the client.
     *
     * @param nickname The nickname to be set.
     * @throws IOException If an I/O error occurs.
     */
    public void setNickname(String nickname) throws IOException {
        getServer().setNickname(this, nickname);
    }


    /**
     * Requests the list of matches from the server.
     *
     * @param client The client requesting the matches.
     * @throws IOException If an I/O error occurs.
     */
    public void getMatches(VirtualView client) throws IOException {
        getServer().getMatches(client);
    }


    /**
     * Creates a new match on the server.
     *
     * @param nickname           The nickname of the player creating the match.
     * @param requiredNumPlayers The required number of players for the match.
     * @throws IOException If an I/O error occurs.
     */
    public void createMatch(String nickname, Integer requiredNumPlayers) throws IOException {
        getServer().createMatch(nickname, requiredNumPlayers);
    }


    /**
     * Joins an existing match.
     *
     * @param nickname The nickname of the player joining the match.
     * @param matchId  The ID of the match to join.
     * @throws IOException If an I/O error occurs.
     */
    public void joinMatch(String nickname, Integer matchId) throws IOException {
        getServer().joinMatch(nickname, matchId);
    }

    public void reJoinMatch(Integer matchId, String nickname) throws IOException {
        getServer().reJoinMatch(matchId, nickname);
    }


    /**
     * Sets the color for the player.
     *
     * @param nickname The nickname of the player.
     * @param color    The color to be set.
     * @throws IOException If an I/O error occurs.
     */
    public void setColor(String nickname, PlayerColor color) throws IOException{
        getMatchServer().setColor(nickname, color);
    }


    /**
     * Sets if the initial card face is up for the player.
     *
     * @param nickname The nickname of the player.
     * @param isFaceUp The face-up state to be set.
     * @throws IOException If an I/O error occurs.
     */
    public void setIsInitialCardFaceUp(String nickname, Boolean isFaceUp) throws IOException{
        getMatchServer().setIsInitialCardFacingUp(nickname, isFaceUp);
    }


    /**
     * Sets the personal quest card for the player.
     *
     * @param nickname  The nickname of the player.
     * @param questCard The quest card to be set.
     * @throws IOException If an I/O error occurs.
     */
    public void setQuestCard(String nickname, QuestCard questCard) throws IOException{
        getMatchServer().setQuestCard(nickname, questCard);
    }


    /**
     * Sends a broadcast message.
     *
     * @param sender   The nickname of the sender.
     * @param message  The message to be sent.
     * @throws IOException If an I/O error occurs.
     */
    public void sendBroadCastMessage(String sender, String message) throws IOException {
        getMatchServer().sendBroadcastMessage(sender, message);
    }


    /**
     * Sends a private message to a specific player receiver.
     *
     * @param sender   The nickname of the sender.
     * @param receiver The nickname of the receiver.
     * @param message  The message to be sent.
     * @throws IOException If an I/O error occurs.
     */
    public void sendPrivateMessage(String sender, String receiver, String message) throws IOException {
        getMatchServer().sendPrivateMessage(sender, receiver, message);
    }


    /**
     * Draws a card from a specific deck type and slot.
     *
     * @param nickname The nickname of the player drawing the card.
     * @param deckType The type of deck to draw from.
     * @param slot     The slot to draw the card from.
     * @throws IOException If an I/O error occurs.
     */
    public void drawCard(String nickname, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        getMatchServer().drawCard(nickname, deckType, slot);
    }


    /**
     * Places a card on the board.
     *
     * @param nickname   The nickname of the player placing the card.
     * @param card       The card to be placed.
     * @param coordinates The coordinates where the card will be placed.
     * @param facingUp   Whether the card will be placed with the face up.
     * @throws IOException If an I/O error occurs.
     */
    public void placeCard(String nickname, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        getMatchServer().placeCard(nickname, card, coordinates, facingUp);
    }
}
