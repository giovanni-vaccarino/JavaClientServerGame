package polimi.ingsoft.server.common;

import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard.Slots;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchActionsInterface extends Remote {
    void addMessage(int matchId, String message) throws RemoteException;

    void drawCard(int matchId, String playerName, String deckType, Slots slot) throws RemoteException;

    void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException;
}
