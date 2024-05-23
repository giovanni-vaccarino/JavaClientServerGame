package polimi.ingsoft.client.common;


import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.rmi.RmiMethodCall;

import java.io.IOException;
import java.rmi.Remote;
import java.util.List;

public interface VirtualView extends Remote {
    void handleRmiClientMessages(RmiMethodCall rmiMethodCall) throws IOException;
    void showNicknameUpdate() throws IOException;
    void showUpdateMatchesList(List<Integer> matches) throws IOException;
    void showUpdateMatchJoin() throws IOException;
    void showUpdateLobbyPlayers(List<String> players) throws IOException;
    void showUpdateMatchCreate(Integer matchId) throws IOException;
    void showUpdateChat(Message message) throws IOException;
    void showUpdateInitialSettings(PlayerColor color, Boolean isFacingUp, QuestCard questCard) throws IOException;
    void showUpdateGameState(GameState gameState) throws IOException;
    void showUpdatePlayerHand(PlayerHand<MixedCard> playerHand) throws IOException;
    void showUpdatePublicBoard(PublicBoard publicBoard) throws IOException;
    void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard) throws IOException;
    void reportError(ERROR_MESSAGES errorMessage) throws IOException;
    void setMatchControllerServer(VirtualMatchServer server) throws IOException;
}
