package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Utils;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.common.ConnectionsClient;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
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

    private final Map<Integer, VirtualMatchServer> matchControllerServer = new HashMap<>();

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


    private void executeMethod(RmiMethodCall methodCall){
        MessageCodes methodName = methodCall.getMethodName();
        Object[] args = methodCall.getArgs();


        switch (methodName) {
            case CONNECT -> {
                this.addClient((VirtualView) args[0], Utils.getRandomNickname());
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
                String playerNickname = (String) args[0];
                Integer requiredNumPlayers = (Integer) args[1];

                try{
                    Integer matchId = this.addMatch(requiredNumPlayers);
                    MatchController matchController = mainController.getMatch(matchId);
                    List<Integer> listMatches = this.listMatches();

                    // Creating a new list for the players
                    synchronized (matchNotificationList){
                        matchNotificationList.put(matchId, new ArrayList<>());
                    }

                    // Creating the MatchControllerRmiServer
                    VirtualMatchServer stubController = this.createMatchControllerServer(matchController, matchNotificationList.get(matchId), this.logger);

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
                    //client.reportError(ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND);
                }
            }

            case MATCH_JOIN_REQUEST -> {
                String playerNickname = (String) args[0];
                Integer matchId = (Integer) args[1];
                String nickname = (String) args[2];
                VirtualView client = clients.get(playerNickname);

                try{
                    this.mainController.joinMatch(matchId, nickname);

                    MatchController match = this.mainController.getMatch(matchId);
                    List<String> players = match.getNamePlayers();

                    client.showUpdateMatchJoin();

                    //Adding the client to the match notification list
                    synchronized (matchNotificationList){
                        matchNotificationList.get(matchId).add(client);
                    }
                    client.setMatchControllerServer(matchControllerServer.get(matchId));

                } catch (MatchAlreadyFullException | MatchNotFoundException exception){
                    //TODO
                    try{
                        client.reportError(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH);
                    } catch (IOException e){
                        throw new RuntimeException(e);
                    }
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
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
    public void createMatch(String nickname, Integer requiredNumPlayers) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_CREATE_REQUEST, new Object[]{nickname, requiredNumPlayers}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_JOIN_REQUEST, new Object[]{playerNickname, matchId}));
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

            //Removing the precedent (key, value) of the client
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

    private VirtualMatchServer createMatchControllerServer(MatchController matchController, List<VirtualView> clients, PrintStream logger){
        try {
            RmiMatchControllerServer matchControllerRmiServer = new RmiMatchControllerServer(matchController, clients, logger);
            VirtualMatchServer stub = (VirtualMatchServer) UnicastRemoteObject.exportObject(matchControllerRmiServer, 0);

            logger.println("RmiServer: New RMI MatchController Server is running");

            return stub;
        } catch (RemoteException e) {
            logger.println("RmiServer: Error occurred while starting RMI MatchController server:" + e.getMessage());
            return null;
        }
    }
}
