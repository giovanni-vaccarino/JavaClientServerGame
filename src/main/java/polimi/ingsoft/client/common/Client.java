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
    private transient final UI ui;

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
    public abstract VirtualServer getServer();


    /**
     * Gets the match server instance.
     *
     * @return The VirtualMatchServer instance.
     */
    public abstract VirtualMatchServer getMatchServer();


    /**
     * Sets the match controller server.
     *
     * @param controller The VirtualMatchServer instance.
     */
    @Override
    public abstract void setMatchServer(VirtualMatchServer controller);

    public abstract void run();


    /**
     * Gets the UI instance.
     *
     * @return The UI instance.
     */
    public UI getUi() {
        return this.ui;
    }

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
        // Do not change this order
        ui.createPublicBoard(resource, gold, quest);
        ui.setPlayerBoards(boards);
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
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) throws IOException{
        ui.updatePlayerBoard(nickname, coordinates, playedCard, score);
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
}
