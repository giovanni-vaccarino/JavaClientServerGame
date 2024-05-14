package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.io.InputStream;
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
                     InputStream inputStream) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        VirtualServerInterface rmiServer = (VirtualServerInterface) registry.lookup(rmiServerName);
        this.server = rmiServer;
        if (ui == UIType.CLI){
            this.ui = new CLI(inputStream, printStream, this);
        } else{
            // TODO create GUI here
            this.ui = new CLI(inputStream, printStream, this);
        }

    }

    @Override
    public void run() throws RemoteException {
        this.server.connect(this);
    }

    @Override
    public UI getUI() {
        return this.ui;
    }

    @Override
    public void clientJoinMatch(Integer matchId, String nickname) throws RemoteException {
        try {
            server.joinMatch(this, matchId, nickname);
        } catch (IOException exception){

        }
    }

    @Override
    public void showJoinMatchResult(Boolean joinResult, List<Player> players) throws IOException {
        System.out.println("puta madre");
        List<String> playersName = players.stream().map(Player::getNickname).toList();
        if (joinResult){
            System.out.println("Successfully joined the match. Waiting for the required players to start... ");
            System.out.println("Players in lobby: ");
            for(var name : playersName){
                System.out.println(name);
            }
        }
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws RemoteException {
        //TODO Add a queue of updates, and in another thread update
        System.out.println("Got update: MATCHES LIST: " + matches);
        try {
            ui.showMatchesList(matches);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public void getMatches(VirtualView client) throws IOException {
        server.getMatches(client);
    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws IOException {
        server.createMatch(requiredNumPlayers);
    }

    @Override
    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException {
        server.joinMatch(client, matchId, nickname);
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
