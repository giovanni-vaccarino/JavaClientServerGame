package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.rmi.RmiMethodCall;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.NetworkMessage;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiClient extends Client {
    private final BlockingQueue<RmiMethodCall> methodQueue = new LinkedBlockingQueue<>();

    private final VirtualServerInterface server;

    private VirtualMatchServer matchServer;

    public RmiClient(String rmiServerHostName,
                     String rmiServerName,
                     Integer rmiServerPort,
                     UIType ui,
                     PrintStream printStream,
                     Scanner scanner
    ) throws RemoteException, NotBoundException {
        super(ui, printStream, scanner);
        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        this.server = (VirtualServerInterface) registry.lookup(rmiServerName);

        System.out.println(Arrays.toString(registry.list()));

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

    private void executeMethod(RmiMethodCall rmiMethodCall) throws IOException {
        MessageCodes messageCode = rmiMethodCall.getMethodName();
        Object[] args = rmiMethodCall.getArgs();

        switch (messageCode) {
            case SET_NICKNAME_UPDATE -> {
                this.showNicknameUpdate();
            }

            case MATCHES_LIST_UPDATE -> {
                List<Integer> matches = (List<Integer>) args[0];
                System.out.println("List Available matches");
                System.out.println(matches);
                this.showUpdateMatchesList(matches);
            }

            case MATCH_JOIN_UPDATE -> {
                this.showUpdateMatchJoin();
            }

            case MATCH_CREATE_UPDATE -> {
                Integer matchId = (Integer) args[0];
                this.showUpdateMatchCreate(matchId);
            }

            case MATCH_CONTROLLER_STUB_UPDATE -> {
                VirtualMatchServer matchServer = (VirtualMatchServer) args[0];
                this.setMatchControllerServer(matchServer);
            }

            case LOBBY_PLAYERS_UPDATE -> {
                List<String> nicknames = (List<String>) args[0];
                this.showUpdateLobbyPlayers(nicknames);
            }

            case SET_INITIAL_SETTINGS_UPDATE -> {
                PlayerInitialSetting playerInitialSetting = (PlayerInitialSetting) args[0];
                this.showUpdateInitialSettings(playerInitialSetting);
            }

            case MATCH_GAME_STATE_UPDATE -> {
                GameState gameState = (GameState) args[0];
                this.showUpdateGameState(gameState);
            }

            case MATCH_PUBLIC_BOARD_UPDATE -> {
                PublicBoard publicBoard = (PublicBoard) args[0];
                this.showUpdatePublicBoard(publicBoard);
            }

            case MATCH_BOARD_UPDATE -> {
                NetworkMessage.BoardUpdatePayload boardUpdatePayload = (NetworkMessage.BoardUpdatePayload) args[0];
                String nickname = boardUpdatePayload.nickname();
                Coordinates coordinates = boardUpdatePayload.coordinates();
                PlayedCard playedCard = boardUpdatePayload.playedCard();
                this.showUpdateBoard(nickname, coordinates, playedCard);
            }

            case MATCH_PLAYER_HAND_UPDATE -> {
                PlayerHand playerHand = (PlayerHand) args[0];
                this.showUpdatePlayerHand(playerHand);
            }

            case ERROR -> {
                ERROR_MESSAGES errorMessage= (ERROR_MESSAGES) args[0];
                this.reportError(errorMessage);
            }

            default -> System.err.println("RMI CLIENT: [INVALID MESSAGE]");
        }
    }

    @Override
    protected VirtualServer getServer() {
        return server;
    }

    @Override
    protected VirtualMatchServer getMatchServer() {
        return matchServer;
    }

    @Override
    public void setMatchControllerServer(VirtualMatchServer server) {
        this.matchServer = server;
    }

    @Override
    public void run() {
        try {
            this.server.connect(this);
        } catch (RemoteException e) {
            // TODO handle
        }
    }

    @Override
    public void handleRmiClientMessages(RmiMethodCall rmiMethodCall) throws RemoteException{
        try {
            methodQueue.put(rmiMethodCall);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
