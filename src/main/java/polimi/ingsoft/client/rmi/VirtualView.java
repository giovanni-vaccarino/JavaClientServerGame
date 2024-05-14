package polimi.ingsoft.client.rmi;


import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.PlayedCard;
import polimi.ingsoft.server.model.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView extends Remote {
    void showNicknameUpdate(boolean result) throws IOException;
    void showJoinMatchResult(Boolean joinResult, List<String> players) throws  IOException;
    void showUpdateMatchesList(List<Integer> matches) throws IOException;
    void showUpdateMatchJoin(Boolean success) throws IOException;
    void showUpdateMatchCreate(MatchController match) throws IOException;
    void showUpdateChat(Message message) throws IOException;

    void showUpdatePublicBoard() throws IOException;

    void showUpdateBoard(Player player, Coordinates coordinates, PlayedCard playedCard) throws IOException;

    void showUpdateGameState(GameState gameState) throws IOException;

    void reportError(String details) throws IOException, RemoteException;

}
