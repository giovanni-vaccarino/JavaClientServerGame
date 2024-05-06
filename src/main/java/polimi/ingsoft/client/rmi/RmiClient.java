package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;


public class RmiClient extends UnicastRemoteObject implements Client {
    private final VirtualServerInterface server;

    private final UI ui;

    public RmiClient(String rmiServerHostName,
                     String rmiServerName,
                     Integer rmiServerPort,
                     UIType ui,
                     PrintStream printStream,
                     Scanner scanner) throws RemoteException, NotBoundException{

        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        VirtualServerInterface rmiServer = (VirtualServerInterface) registry.lookup(rmiServerName);
        this.server = rmiServer;
        if (ui == UIType.CLI)
            this.ui = new CLI(scanner, printStream, this);
        else
            // TODO create GUI here
            this.ui = new CLI(scanner, printStream, this);
    }

    @Override
    public void run() throws RemoteException {
        this.server.connect(this);
    }

    @Override
    public UI getUI() {
        return null;
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws RemoteException {

    }

    @Override
    public void showUpdateMatchJoin(Boolean success) throws RemoteException {

    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws RemoteException {

    }

    @Override
    public void showUpdateChat(Message message) throws RemoteException {

    }

    @Override
    public void showUpdatePublicBoard() throws RemoteException {

    }

    @Override
    public void showUpdateBoard() throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void getMatches() throws IOException {

    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws IOException {

    }

    @Override
    public void joinMatch(Integer matchId, String nickname) throws IOException {

    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    @Override
    public void addMessage(int matchId, String message) throws IOException {

    }

    @Override
    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {

    }

    @Override
    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {

    }
}
