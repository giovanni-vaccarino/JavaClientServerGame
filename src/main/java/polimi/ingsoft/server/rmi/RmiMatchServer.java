package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.controllerg.MainController;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.GameCard;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard.Slots;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiMatchServer implements VirtualMatchServer {
    final MainController controller;
    final List<VirtualView> clients = new ArrayList<>();

    public RmiMatchServer(MainController controller) {
        this.controller = controller;
    }

    //Al posto di mettere throws eccezioni, gestisci bene in un try catch il tutto
    public static void main(String[] args) throws RemoteException{
        final String serverName = "Server";

        VirtualMatchServer server = new RmiMatchServer(new MainController());
        VirtualMatchServer stub = (VirtualMatchServer) UnicastRemoteObject.exportObject(server,0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(serverName, stub);
        System.out.println("server bound.");
     }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
    }

    @Override
    public void addMessage(String message) throws RemoteException{
        Message messageSent = this.controller.writeMessage(message);

        synchronized (this.clients){
            for(var client : this.clients){
                client.showUpdateChat(messageSent);
            }
        }
    }

    @Override
    public void drawCard(Player player, String deckType, Slots slot) throws RemoteException{
        GameCard drawedCard;
        drawedCard = this.controller.drawCard(player, deckType, slot);

        // Avvisare tutti i giocatori del cambio dentro PublicBoard
        synchronized (this.clients){
            for(var client : this.clients){
                client.showUpdatePublicBoard();
            }
        }
    }

    @Override
    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException{

    }
}
