package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.common.ConnectionsClient;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServerInterface, ConnectionsClient {
    final MainController mainController;

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


    private void executeMethod(RmiMethodCall methodCall) {
        String methodName = methodCall.getMethodName();
        Object[] args = methodCall.getArgs();


        switch (methodName) {
            case "connect" -> {
                this.addClient((VirtualView) args[0], (String) args[1]);
                // TODO put the notification to the client
            }

            case "getMatches" -> {
                List<Integer> matches = this.listMatches();

                VirtualView client = (VirtualView) args[0];

                //TODO Ensure that the client has already done connect
                synchronized (client) {
                    try {
                        client.showUpdateMatchesList(matches);
                    } catch (IOException exception) {

                    }
                }
            }

            case "createMatch" -> {
                Integer matchId = this.addMatch((Integer) args[0]);
                List<Integer> listMatches = this.listMatches();

                // Creating a new list for the players
                synchronized (matchNotificationList){
                    matchNotificationList.put(matchId, new ArrayList<>());
                }

                // TODO Do a blocking queue also for this?
                synchronized (this.clients) {
                    for (var client : this.clients.values()) {
                        try {
                            client.showUpdateMatchesList(listMatches);
                        } catch (IOException exception) {

                        }
                    }
                }
            }

            case "joinMatch" -> {
                logger.println("RMI: Received join match request");
                VirtualView client = (VirtualView) args[0];
                Integer matchId = (Integer) args[1];
                String nick = (String) args[2];

                Boolean joinResult = this.enterMatch(matchId, nick);

                //Adding the client to the match notification list
                synchronized (matchNotificationList){
                    matchNotificationList.get(matchId).add(client);
                }

                MatchController match = this.mainController.getMatch(matchId);
                List<String> players = match.getNamePlayers();

                // TODO put the notification to the client
                if (joinResult) {
                    try {
                        client.showJoinMatchResult(joinResult, players);
                    } catch (IOException exception) {

                    }
                } else {
                    //client.reportJoinMatchError();
                }
            }

            case "addMessage" -> {
                Integer matchId = (Integer) args[0];
                String message = (String) args[1];

                Boolean addMessageResult = this.writeMessage(matchId, message);

                List<VirtualView> clientsToNotify = this.getPlayersToNotify(matchId);

                // TODO notification to the clients
                if (addMessageResult) {
                    synchronized (clientsToNotify){
                        // another blocking queue for the client chat updates?
                        //client.showUpdateChat();
                    }
                } else {
                    //client.reportUpdateChatError();
                }
            }

            case "drawCard" -> {
                Integer matchId = (Integer) args[0];
                Boolean addDrawResult = this.takeCard(matchId,
                        (Player) args[1],
                        (String) args[2],
                        (PlaceInPublicBoard.Slots) args[3]);

                List<VirtualView> clientsToNotify = this.getPlayersToNotify(matchId);

                // TODO put the notification to the client
                if (addDrawResult) {
                    synchronized (clientsToNotify){
                        for(var client : clientsToNotify){
                            //client.showUpdateDraw();
                        }
                    }
                } else {
                    //client.reportErrorDraw();
                }
            }

            case "placeCard" -> {
                Integer matchId = (Integer) args[0];
                Boolean placeCardResult = this.addBoardCard(matchId,
                        (Player) args[1],
                        (MixedCard) args[2],
                        (Coordinates) args[3],
                        (Boolean) args[4]);

                List<VirtualView> clientsToNotify = this.getPlayersToNotify(matchId);

                // TODO put the notification to the client
                if (placeCardResult) {
                    synchronized (clientsToNotify){
                        for(var client : clientsToNotify){
                            //client.showUpdateDraw();
                        }
                    }
                } else {
                    //client.reportErrorPlace();
                }
            }

        }
    }

    @Override
    public void connect(VirtualView client, String nickname) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("connect", new Object[]{client, nickname}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void getMatches(VirtualView client) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("getMatches", new Object[]{client}));
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
    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall("joinMatch", new Object[]{client, matchId, nickname}));
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

    private void addClient(VirtualView client, String nickname){
        synchronized (this.clients){
            this.clients.put(nickname, client);
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
            } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception){
                //TODO Add Exception Handler
            }
        }

        return false;
    }

    private List<VirtualView> getPlayersToNotify(Integer matchId){
        return matchNotificationList.get(matchId);
    }

    private Boolean addBoardCard(Integer matchId, Player player, MixedCard card, Coordinates coordinates, Boolean isFacingUp){
        MatchController matchController = mainController.getMatch(matchId);
        if (matchController != null) {
            try{
                matchController.placeCard(player, card, coordinates, isFacingUp);
                return true;
            } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception){
                //TODO Add Exception Handler
            }
        }

        return false;
    }
}
