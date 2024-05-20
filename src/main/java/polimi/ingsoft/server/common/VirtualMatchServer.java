package polimi.ingsoft.server.common;

import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMatchServer extends Remote {
    void setColor(String nickname, PlayerColors color) throws RemoteException;

    void setFaceInitialCard(String nickname, Boolean isFaceUp) throws RemoteException;

    void setQuestCard(String nickname, QuestCard questCard) throws RemoteException;

    void sendMessage(String player, String message) throws RemoteException;

    void sendPrivateMessage(String player, String message) throws RemoteException;

    void drawCard(String player, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException;

    void placeCard(String player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException;
}
