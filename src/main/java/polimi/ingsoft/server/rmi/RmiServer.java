package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.common.MatchActionsInterface;
import polimi.ingsoft.server.common.MatchManagerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.rmi.RemoteException;

public class RmiServer implements MatchManagerInterface, MatchActionsInterface {
    final MainController mainController;

    public RmiServer(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void createMatch(String nickname, Integer requiredNumPlayers) throws RemoteException {
        this.mainController.createMatch(nickname, requiredNumPlayers);
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
