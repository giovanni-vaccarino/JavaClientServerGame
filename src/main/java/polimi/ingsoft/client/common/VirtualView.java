package polimi.ingsoft.client.common;


import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.rmi.RmiMethodCall;

import java.io.IOException;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public interface VirtualView extends Remote {
    void handleRmiClientMessages(RmiMethodCall rmiMethodCall) throws IOException;
    void showNicknameUpdate() throws IOException;
    void showUpdateMatchesList(List<Integer> matches) throws IOException;
    void showUpdateMatchJoin() throws IOException;
    void showUpdateLobbyPlayers(List<String> players) throws IOException;
    void showUpdateMatchCreate(Integer matchId) throws IOException;
    void showUpdateBroadcastChat(String sender, String message) throws IOException;
    void showUpdatePrivateChat(String sender, String recipient, String message) throws IOException;
    void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) throws IOException;
    void showUpdateGameState(GameState gameState) throws IOException;
    void showUpdateGameStart(
            PlaceInPublicBoard<ResourceCard> resource,
            PlaceInPublicBoard<GoldCard> gold,
            PlaceInPublicBoard<QuestCard> quest,
            Map<String, Board> boards
    ) throws IOException;
    void showUpdatePlayerHand(PlayerHand playerHand) throws IOException;
    void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException;
    void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard) throws IOException;
    void reportError(ERROR_MESSAGES errorMessage) throws IOException;
    void setMatchControllerServer(VirtualMatchServer server) throws IOException;
}
