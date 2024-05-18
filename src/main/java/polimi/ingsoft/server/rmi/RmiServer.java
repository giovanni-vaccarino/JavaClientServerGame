package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchController;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.common.ConnectionsClient;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.socket.ConnectionHandler;
import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServerInterface, ConnectionsClient {
    private final MainController mainController;

    private final Map<Integer, VirtualMatchController> matchControllerServer = new HashMap<>();

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
        MessageCodes methodName = methodCall.getMethodName();
        Object[] args = methodCall.getArgs();


        switch (methodName) {
            case CONNECT -> {
                this.addClient((VirtualView) args[0], ConnectionHandler.getRandomNickname());
            }

            case SET_NICKNAME_REQUEST -> {
                VirtualView client = (VirtualView) args[0];
                String nickname = (String) args[1];
                boolean result = this.setNicknameForClient(client, nickname);

                synchronized (this.clients){
                    try {
                        client.showNicknameUpdate(result);
                    } catch (IOException ignore) {

                    }
                }
            }

            case MATCHES_LIST_REQUEST -> {
                VirtualView client = (VirtualView) args[0];
                List<Integer> matches = this.listMatches();

                //TODO Ensure that the client has already done connect
                synchronized (this.clients) {
                    try {
                        client.showUpdateMatchesList(matches);
                    } catch (IOException exception) {

                    }
                }
            }

            case MATCH_CREATE_REQUEST -> {
                Integer requiredNumPlayers = (Integer) args[0];

                try{
                    Integer matchId = this.addMatch(requiredNumPlayers);
                    MatchController matchController = mainController.getMatch(matchId);
                    List<Integer> listMatches = this.listMatches();

                    // Creating a new list for the players
                    synchronized (matchNotificationList){
                        matchNotificationList.put(matchId, new ArrayList<>());
                    }

                    // Creating the MatchControllerRmiServer
                    VirtualMatchController stubController = this.createMatchControllerServer(matchController, matchNotificationList.get(matchId), this.logger);

                    matchControllerServer.put(matchId, stubController);

                    synchronized (this.clients) {
                        for (var client : this.clients.values()) {
                            try {
                                client.showUpdateMatchesList(listMatches);
                            } catch (IOException exception) {

                            }
                        }
                    }
                    //TODO Join the first client
                } catch (NotValidNumPlayersException exception){
                    //TODO
                    //synchronized(this.clients)
                    //client.reportError()
                }
            }

            case MATCH_JOIN_REQUEST -> {
                logger.println("RMI: Received join match request");
                VirtualView client = (VirtualView) args[0];
                Integer matchId = (Integer) args[1];
                String nickname = (String) args[2];


                try{
                    this.mainController.joinMatch(matchId, nickname);
                } catch (MatchAlreadyFullException | MatchNotFoundException exception){
                    //TODO
                    //client.reportError()
                }

                MatchController match = this.mainController.getMatch(matchId);
                List<String> players = match.getNamePlayers();

                try {
                    client.showJoinMatchResult(true, players);

                    //Adding the client to the match notification list
                    synchronized (matchNotificationList){
                        matchNotificationList.get(matchId).add(client);
                    }
                    client.showMatchControllerServerStub(matchControllerServer.get(matchId));
                } catch (IOException exception) {
                    //client.reportJoinMatchError();
                }
            }

        }
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.CONNECT, new Object[]{client}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setNickname(VirtualView client, String nickname) throws IOException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_NICKNAME_REQUEST, new Object[]{client, nickname}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void getMatches(VirtualView client) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCHES_LIST_REQUEST, new Object[]{client}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_CREATE_REQUEST, new Object[]{requiredNumPlayers}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_JOIN_REQUEST, new Object[]{client, matchId, nickname}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws RemoteException {

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

    private Boolean setNicknameForClient(VirtualView client, String nickname){
        synchronized (this.clients) {
            if (clients.containsKey(nickname)) {
                return false;
            }

            clients.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(client))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(clients::remove);

            clients.put(nickname, client);
            return true;
        }
    }

    private List<VirtualView> getPlayersToNotify(Integer matchId){
        return matchNotificationList.get(matchId);
    }

    private VirtualMatchController createMatchControllerServer(MatchController matchController, List<VirtualView> clients, PrintStream logger){
        try {
            RmiMatchControllerServer matchControllerRmiServer = new RmiMatchControllerServer(matchController, clients, logger);
            VirtualMatchController stub = (VirtualMatchController) UnicastRemoteObject.exportObject(matchControllerRmiServer, 0);

            logger.println("RmiServer: RMI MatchController Server is running");

            return stub;
        } catch (RemoteException e) {
            logger.println("RmiServer: Error occurred while starting RMI MatchController server:" + e.getMessage());
            return null;
        }
    }
}
