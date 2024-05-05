package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServerInterface {
    final MainController mainController;

    final List<VirtualView> clients = new ArrayList<>();

    private final PrintStream logger;

    private final BlockingQueue<RmiMethodCall> methodQueue = new LinkedBlockingQueue<>();

    public RmiServer(MainController mainController, PrintStream logger) {
        this.mainController = mainController;
        this.logger = logger;
        methodWorkerThread.start();
    }

    private final Thread methodWorkerThread = new Thread(() -> {
        while (true) {
            try {
                RmiMethodCall methodCall = methodQueue.take();
                executeMethod(methodCall);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    });

    //TODO Fix the casting
    private void executeMethod(RmiMethodCall methodCall) {
        String methodName = methodCall.getMethodName();
        Object[] args = methodCall.getArgs();


        switch (methodName) {
            case "connect":
                this.addClient((VirtualView) args[0]);
                // TODO put the notification to the client
                break;

            case "getMatches":
                List<Integer> matches = this.listMatches();
                // TODO put the return list to the client
                break;

            case "createMatch":
                this.addMatch((Integer) args[0]);
                // TODO put the notification to the client
                break;

            case "joinMatch":
                Boolean joinResult = this.enterMatch((Integer) args[0], (String) args[1]);
                // TODO put the notification to the client
                if(joinResult){
                    //client.showJoinMatchResult();
                }else{
                    //client.reportJoinMatchError();
                }
                break;

            case "addMessage":
                Boolean addMessageResult = this.writeMessage((Integer) args[0], (String) args[1]);
                // TODO put the return list to the client
                if(addMessageResult){
                    // another blocking queue for the client chat updates?
                    // for each client in the game
                    //client.showUpdateChat();
                }else{
                    //client.reportUpdateChatError();
                }
                break;

            case "drawCard":
                Boolean addDrawResult = this.takeCard((Integer) args[0],
                                                        (Player) args[1],
                                                        (String) args[2],
                                                        (PlaceInPublicBoard.Slots) args[3]);
                // TODO put the notification to the client
                if(addDrawResult){
                    //for each client in the game
                    //client.showUpdateDraw();
                }else{
                    //client.reportErrorDraw();
                }
                break;

            case "placeCard":
                Boolean placeCardResult = this.addBoardCard((Integer) args[0],
                                                            (Player) args[1],
                                                            (MixedCard) args[2],
                                                            (Coordinates) args[3],
                                                            (Boolean) args[4]);
                // TODO put the notification to the client
                if(placeCardResult){
                    //for each client in the game
                    //client.showUpdatePlace();
                }else{
                    //client.reportErrorPlace();
                }

                break;
        }
    }

    //TODO For connect, getMatches, and other getters should I use methodQueue?
    @Override
    public void connect(VirtualView client) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("connect", new Object[]{client}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void getMatches() throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("getMatches", new Object[]{}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("createMatch", new Object[]{requiredNumPlayers}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void joinMatch(Integer matchId, String nickname) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("joinMatch", new Object[]{matchId, nickname}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws RemoteException {

    }

    @Override
    public void addMessage(int matchId, String message) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("addMessage", new Object[]{matchId, message}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("drawCard", new Object[]{matchId, playerName, deckType, slot}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("placeCard", new Object[]{matchId, playerName, card, coordinates, facingUp}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void addClient(VirtualView client){
        synchronized (this.clients){
            this.clients.add(client);
        }
    }

    private List<Integer> listMatches(){
        return mainController.getMatches();
    }

    private Integer addMatch(Integer requiredNumPlayers){
        return this.mainController.createMatch(requiredNumPlayers);
    }

    private Boolean enterMatch(Integer matchId, String nickname){
        return this.mainController.joinMatch(matchId, nickname);
    }

    private Boolean writeMessage(Integer matchId, String message){
        MatchController matchController = mainController.getMatch(matchId);
        if (matchController != null) {
            matchController.writeMessage(message);
            return true;
        }

        return false;
    }

    private Boolean takeCard(Integer matchId, Player player, String deckType, PlaceInPublicBoard.Slots slot){
        MatchController matchController = mainController.getMatch(matchId);
        if (matchController != null) {
            try{
                matchController.drawCard(player, deckType, slot);
                return true;
            } catch (WrongStepException | WrongPlayerForCurrentTurnException exception){
                //TODO Add Exception Handler
            }
        }

        return false;
    }

    private Boolean addBoardCard(Integer matchId, Player player, MixedCard card, Coordinates coordinates, Boolean isFacingUp){
        MatchController matchController = mainController.getMatch(matchId);
        if (matchController != null) {
            try{
                matchController.placeCard(player, card, coordinates, isFacingUp);
                return true;
            } catch (WrongStepException | WrongPlayerForCurrentTurnException exception){
                //TODO Add Exception Handler
            }
        }

        return false;
    }
}
