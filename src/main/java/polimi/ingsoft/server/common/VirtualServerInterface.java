package polimi.ingsoft.server.common;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServerInterface extends Remote {
    void connect(VirtualView client) throws RemoteException;

    void createMatch(String nickname, Integer requiredNumPlayers) throws RemoteException;

    void joinMatch(Integer matchId, String nickname) throws RemoteException;

    void reJoinMatch(Integer matchId, String nickname) throws RemoteException;

    void addMessage(int matchId, String message) throws RemoteException;

    void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException;

    void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException;
}
