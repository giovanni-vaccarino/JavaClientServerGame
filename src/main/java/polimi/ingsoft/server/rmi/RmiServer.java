package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RmiServer implements VirtualServerInterface {
    final MainController mainController;

    final List<VirtualView> clients = new ArrayList<>();

    private final PrintStream logger;

    public RmiServer(MainController mainController, PrintStream logger) {
        this.mainController = mainController;
        this.logger = logger;
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
        logger.println("RMI: NEW CLIENT ADDED");
    }

    @Override
    public List<Integer> getMatches() throws RemoteException {
        return mainController.getMatches();
    }

    @Override
    public Integer createMatch(Integer requiredNumPlayers) throws RemoteException {
        return this.mainController.createMatch(requiredNumPlayers);
    }

    @Override
    public void joinMatch(Integer matchId, String nickname) throws RemoteException {
        this.mainController.joinMatch(matchId, nickname);
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws RemoteException {

    }

    @Override
    public void addMessage(int matchId, String message) throws RemoteException {
        MatchController matchController = mainController.getMatch(matchId);
        if (matchController != null) {
            // Sorry l'ho tolto ):
            // matchController.writeMessage(message);
        } else {
            throw new RemoteException("Match not found.");
        }
    }

    @Override
    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {

    }

    @Override
    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException {

    }
}
