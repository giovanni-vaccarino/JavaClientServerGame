package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.command.ClientCommand;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class ClientProxy implements VirtualView {

    @Override
    public abstract void sendMessage(ClientCommand command) throws IOException;

    @Override
    public void showNicknameUpdate() throws IOException {
        sendMessage((ClientCommand) VirtualView::showNicknameUpdate);
    }

    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateLobbyPlayers(players));
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateMatchesList(matches));
    }

    @Override
    public void showUpdateMatchJoin() throws IOException {
        sendMessage((ClientCommand) VirtualView::showUpdateMatchJoin);
    }

    @Override
    public void showUpdateMatchCreate(Integer matchId) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateMatchCreate(matchId));
    }

    @Override
    public void showUpdateBroadcastChat(Message _message) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateBroadcastChat(_message));
    }

    @Override
    public void showUpdatePrivateChat(String recipient, Message _message) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdatePrivateChat(recipient, _message));
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) throws IOException {
        PlayerInitialSetting playerInitialSettingClone = playerInitialSetting.clone();
        sendMessage((ClientCommand) client -> client.showUpdateInitialSettings(playerInitialSettingClone));
    }

    @Override
    public void showUpdateGameState(GameState gameState) throws IOException {
        GameState gameStateClone = gameState.clone();
        sendMessage((ClientCommand) client -> client.showUpdateGameState(gameStateClone));
    }

    @Override
    public void showUpdateGameStart(PlaceInPublicBoard<ResourceCard> resource, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quest, Map<String, Board> boards) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateGameStart(resource, gold, quest, boards));
    }

    @Override
    public void showUpdatePlayerHand(PlayerHand playerHand) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdatePlayerHand(playerHand));
    }

    @Override
    public void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException{
        sendMessage((ClientCommand) client -> client.showUpdatePublicBoard(deckType, placeInPublicBoard));
    }

    @Override
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) throws IOException {
        sendMessage((ClientCommand) client -> client.showUpdateBoard(nickname, coordinates, playedCard, score));
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) throws IOException {
        sendMessage((ClientCommand) client -> client.reportError(errorMessage));
    }
}
