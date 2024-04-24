package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.controllerg.MainController;
import polimi.ingsoft.server.Player;
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

public class RmiServer implements VirtualServer{
    final MainController controller;
    final List<VirtualView> clients = new ArrayList<>();

    public RmiServer(MainController controller) {
        this.controller = controller;
    }

    //Al posto di mettere throws eccezioni, gestisci bene in un try catch il tutto
    public static void main(String[] args) throws RemoteException{
        final String serverName = "AdderServer";

        VirtualServer server = new RmiServer(new MainController());
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server,0);
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
        this.controller.writeMessage(message);
        List<Message> currentMessages = this.controller.getChat();

        synchronized (this.clients){
            for(var client : this.clients){
                client.showUpdateChat(currentMessages);
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
    public void placeCard(Player player, MixedCard card) throws RemoteException{

    }
}
