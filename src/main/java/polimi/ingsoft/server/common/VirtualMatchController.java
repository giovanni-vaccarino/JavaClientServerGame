package polimi.ingsoft.server.common;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMatchController extends Remote {
    void sendMessage(Player player, String message) throws RemoteException;

    void sendPrivateMessage(Player player, String message) throws RemoteException;

    void drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException;

    void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException;

}
