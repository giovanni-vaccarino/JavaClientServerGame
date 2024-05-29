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

    protected abstract VirtualServer getServer();

    protected abstract VirtualMatchServer getMatchServer();

    @Override
    public abstract void setMatchControllerServer(VirtualMatchServer controller);

    public abstract void run();

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
        System.out.println("nico: "+boards.get("nico").getFirstPlayer());
        System.out.println("gio: "+boards.get("gio").getFirstPlayer());
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

    public void setColor(String nickname, PlayerColor color) throws IOException{
        getMatchServer().setColor(nickname, color);
    }

    public void setIsInitialCardFaceUp(String nickname, Boolean isFaceUp) throws IOException{
        getMatchServer().setIsInitialCardFacingUp(nickname, isFaceUp);
    }

    public void setQuestCard(String nickname, QuestCard questCard) throws IOException{
        getMatchServer().setQuestCard(nickname, questCard);
    }

    public void sendBroadCastMessage(String sender, String receiver, String message) throws IOException {
        getMatchServer().sendBroadcastMessage(sender, message);
    }

    public void sendPrivateMessage(String sender, String receiver, String message) throws IOException {
        getMatchServer().sendPrivateMessage(sender, receiver, message);
    }

    public void drawCard(String nickname, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        getMatchServer().drawCard(nickname, deckType, slot);
    }

    public void placeCard(String nickname, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        getMatchServer().placeCard(nickname, card, coordinates, facingUp);
    }
}
