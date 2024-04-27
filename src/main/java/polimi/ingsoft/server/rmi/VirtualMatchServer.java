package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard.Slots;

import java.rmi.Remote;
import java.rmi.RemoteException;


//Interfaccia che utliazziamo dal client per interagire col server
public interface VirtualMatchServer extends Remote{
    // Metodo per segnalare al server chi siamo, per contattarci
    // gli passo lo stub del client
    void connect(VirtualView client) throws RemoteException;

    void addMessage(String message) throws RemoteException;

    void drawCard(Player player, String deckType, Slots slot) throws RemoteException;

    void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException;
}
