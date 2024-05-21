package polimi.ingsoft.client.common;

import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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
            ui = new GUI();
    }

    protected abstract VirtualServer getServer();

    protected abstract VirtualMatchServer getMatchServer();

    @Override
    public abstract void setMatchControllerServer(VirtualMatchServer controller);

    public abstract void run();

    public UI getUi(){return this.ui;}

    @Override
    public void showNicknameUpdate(boolean result) throws IOException {
        ui.updateNickname(result);
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
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        ui.showMatchCreate(match);
    }

    @Override
    public void showUpdateInitialSettings(PlayerColor color, Boolean isFacingUp, QuestCard questCard) throws IOException {

    }

    @Override
    public void showUpdateGameState(GameState gameState) throws IOException{

    }

    @Override
    public void showUpdatePlayerHand(PlayerHand<MixedCard> playerHand) throws IOException {

    }

    @Override
    public void showUpdatePublicBoard(PublicBoard publicBoard) throws IOException {

    }

    @Override
    public void showUpdateBoard(String nickname, Board board) throws IOException{

    }

    @Override
    public void showUpdateChat(Message message) throws IOException {

    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) throws IOException {

    }

    public void setNickname(String nickname) throws IOException {
        getServer().setNickname(this, nickname);
    }

    public void getMatches(VirtualView client) throws IOException {
        getServer().getMatches(client);
    }

    public void createMatch(String nickname, Integer requiredNumPlayers) throws IOException {
        getServer().createMatch(nickname, requiredNumPlayers);
    }

    public void joinMatch(String nickname, Integer matchId) throws IOException {
        getServer().joinMatch(nickname, matchId);
    }

    public void reJoinMatch(Integer matchId, String nickname) throws IOException {
        getServer().reJoinMatch(matchId, nickname);
    }

    public void addMessage(String nickname, String message) throws IOException {
        getMatchServer().sendMessage(nickname, message);
    }

    public void drawCard(String nickname, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        getMatchServer().drawCard(nickname, deckType, slot);
    }

    public void placeCard(String nickname, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        getMatchServer().placeCard(nickname, card, coordinates, facingUp);
    }
}
