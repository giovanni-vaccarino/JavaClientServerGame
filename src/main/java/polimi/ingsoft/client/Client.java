package polimi.ingsoft.client;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.common.VirtualMatchController;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
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
            // TODO create GUI here
            ui = new CLI(scanner, printStream, this);
    }

    protected abstract VirtualServer getServer();

    public abstract void run();

    public void showNicknameUpdate(boolean result) throws IOException {
        ui.updateNickname(result);
    }
    public void showJoinMatchResult(Boolean joinResult, List<String> players) throws  IOException {

    }
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        ui.updateMatchesList(matches);
    }
    public void showUpdateMatchJoin(Boolean success) throws IOException {

    }
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        ui.showMatchCreate(match);
    }
    public void showUpdateChat(Message message) throws IOException {

    }
    public void showUpdatePublicBoard() throws IOException {

    }

    public void reportError(String details) throws IOException {

    }

    public void setNickname(String nickname) throws IOException {
        getServer().setNickname(nickname);
    }

    public void getMatches(VirtualView client) throws IOException {
        getServer().getMatches(client);
    }

    public void createMatch(Integer requiredNumPlayers) throws IOException {
        getServer().createMatch(requiredNumPlayers);
    }

    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException {
        getServer().joinMatch(client, matchId, nickname);
    }

    public void reJoinMatch(Integer matchId, String nickname) throws IOException {
        getServer().reJoinMatch(matchId, nickname);
    }

    public void addMessage(int matchId, String message) throws IOException {
        //getServer().addMessage(matchId, message);
    }

    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        //getServer().drawCard(matchId, playerName, deckType, slot);
    }

    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        //getServer().placeCard(matchId, playerName, card, coordinates, facingUp);
    }

    @Override
    public void showMatchControllerServerStub(VirtualMatchController controller) throws IOException{

    }

    @Override
    public void showUpdateBoard(Player player, Coordinates coordinates, PlayedCard playedCard) throws IOException{

    }

    @Override
    public void showUpdateGameState(GameState gameState) throws IOException{

    }
}
