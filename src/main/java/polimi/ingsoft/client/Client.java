package polimi.ingsoft.client;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, AutoCloseable, VirtualView {
    void run() throws RemoteException;
    UI getUI() throws RemoteException;
    void setNickname(String nickname) throws IOException;
    void clientJoinMatch(Integer matchId, String nickname) throws RemoteException;

    void getMatches(VirtualView client) throws IOException;

    void createMatch(Integer requiredNumPlayers) throws IOException;

    void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException;

    void reJoinMatch(Integer matchId, String nickname) throws IOException;

    void addMessage(int matchId, String message) throws IOException;

    void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException;

    void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException;
}
