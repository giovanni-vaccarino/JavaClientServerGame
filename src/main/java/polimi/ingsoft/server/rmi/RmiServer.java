package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Utils;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
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
import java.util.*;
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
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    });


    private void executeMethod(RmiMethodCall methodCall) throws IOException {
        MessageCodes methodName = methodCall.getMethodName();
        Object[] args = methodCall.getArgs();


        switch (methodName) {
            case CONNECT -> {
                this.addClient((VirtualView) args[0], Utils.getRandomNickname());
            }

            case SET_NICKNAME_REQUEST -> {
                VirtualView client = (VirtualView) args[0];
                String nickname = (String) args[1];

                try{
                    this.setNicknameForClient(client, nickname);

                    synchronized (this.clients){
                        System.out.println("Nickname available");

                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.SET_NICKNAME_UPDATE, new Object[]{});
                        client.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (NicknameNotAvailableException exception){
                    synchronized (this.clients){
                        System.out.println("Nickname not available");

                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR, new Object[]{ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE});
                        client.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

            case MATCHES_LIST_REQUEST -> {
                VirtualView client = (VirtualView) args[0];
                List<Integer> matches = this.listMatches();

                synchronized (this.clients) {
                    RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.MATCHES_LIST_UPDATE, new Object[]{matches});
                    client.handleRmiClientMessages(rmiMethodCall);
                }
            }

            case MATCH_CREATE_REQUEST -> {
                String playerNickname = (String) args[0];
                Integer requiredNumPlayers = (Integer) args[1];
                VirtualView clientToUpdate = this.clients.get(playerNickname);

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
                        RmiMethodCall rmiMethodCallMatchCreate = new RmiMethodCall(MessageCodes.MATCH_CREATE_UPDATE, new Object[]{matchId});
                        RmiMethodCall rmiMethodCallMatchesList = new RmiMethodCall(MessageCodes.MATCHES_LIST_UPDATE, new Object[]{listMatches});

                        for (var client : this.clients.values()) {
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallMatchCreate);
                            }
                            client.handleRmiClientMessages(rmiMethodCallMatchesList);
                        }
                    }

                } catch (NotValidNumPlayersException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR, new Object[]{ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

            case MATCH_JOIN_REQUEST -> {
                String playerNickname = (String) args[0];
                Integer matchId = (Integer) args[1];
                System.out.println("MATCH ID" + matchId);
                VirtualView clientToUpdate = clients.get(playerNickname);

                try{
                    this.mainController.joinMatch(matchId, playerNickname);

                    MatchController match = this.mainController.getMatch(matchId);
                    List<String> players = match.getNamePlayers();
                    GameState gameState = match.getGameState();

                    //Adding the client to the match notification list
                    synchronized (matchNotificationList){
                        matchNotificationList.get(matchId).add(clientToUpdate);
                    }

                    PlayerInitialSetting playerInitialSetting = match.getPlayerInitialSettingByNickname(playerNickname)
                            .orElse(null);

                    RmiMethodCall rmiMethodCallMatchJoin = new RmiMethodCall(MessageCodes.MATCH_JOIN_UPDATE, new Object[]{});
                    RmiMethodCall rmiMethodCallMatchControllerStub = new RmiMethodCall(MessageCodes.MATCH_CONTROLLER_STUB_UPDATE, new Object[]{matchControllerServer.get(matchId)});
                    //RmiMethodCall rmiMethodCallPlayerInitialSetting = new RmiMethodCall(MessageCodes.SET_INITIAL_SETTINGS_UPDATE, new Object[]{playerInitialSetting});

                    synchronized (this.clients){
                        clientToUpdate.handleRmiClientMessages(rmiMethodCallMatchJoin);
                        clientToUpdate.handleRmiClientMessages(rmiMethodCallMatchControllerStub);
                        //clientToUpdate.handleRmiClientMessages(rmiMethodCallPlayerInitialSetting);

                        if(gameState.getGamePhase() == GAME_PHASE.INITIALIZATION){
                            for(var player : players){
                                RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE, new Object[]{gameState});
                                VirtualView client = clients.get(player);

                                client.handleRmiClientMessages(rmiMethodCallGameState);
                            }
                        }
                    }



                } catch (MatchAlreadyFullException | MatchNotFoundException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR, new Object[]{ERROR_MESSAGES.MATCH_IS_ALREADY_FULL});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

        }
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        logger.println("RMI: Nuovo client in arrivo");
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.CONNECT, new Object[]{client}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setNickname(VirtualView client, String nickname) throws IOException {
        logger.println("RMI: Nickname nuovo: " + nickname);
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
        logger.println("RMI: Nuova richiesta match da: " + nickname + " con NumPlayers: "+requiredNumPlayers);
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_CREATE_REQUEST, new Object[]{nickname, requiredNumPlayers}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws RemoteException {
        logger.println("RMI: Nuova richiesta join match da: " + playerNickname + " con matchId: "+matchId);
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

    private void setNicknameForClient(VirtualView client, String nickname) throws NicknameNotAvailableException {
        synchronized (this.clients) {
            if (clients.containsKey(nickname)) {
                throw new NicknameNotAvailableException();
            }

            //Removing the precedent (key, value) of the client
            clients.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(client))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(clients::remove);

            clients.put(nickname, client);
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
