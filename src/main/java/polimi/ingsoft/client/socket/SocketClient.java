package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.NetworkMessage;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SocketClient extends Client {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final VirtualServer server;

    private VirtualMatchServer matchServer;

    public SocketClient(
            String hostName,
            int port,
            UIType uiType,
            PrintStream printStream,
            Scanner scanner
    ) throws IOException {
        super(uiType, printStream, scanner);
        socket = new Socket(hostName, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        server = new ServerProxy(out);
        matchServer = new MatchServerProxy(out);
    }

    @Override
    public VirtualServer getServer() {
        return server;
    }

    @Override
    protected VirtualMatchServer getMatchServer() {
        return matchServer;
    }

    @Override
    public void setMatchControllerServer(VirtualMatchServer controller) {
        this.matchServer = controller;
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (IOException | ClassNotFoundException e) {
                // TODO handle
            }
        }).start();
    }

    private void runVirtualServer() throws IOException, ClassNotFoundException {
        NetworkMessage item;
        while ((item = (NetworkMessage) in.readObject()) != null) {
            MessageCodes type = item.type;
            Object payload = item.payload;
            // Read message and perform action
            switch (type) {
                case SET_NICKNAME_UPDATE -> {
                    this.showNicknameUpdate();
                }
                case MATCHES_LIST_UPDATE -> {
                    List<Integer> matches = (List<Integer>) payload;
                    this.showUpdateMatchesList(matches);
                }
                case MATCH_JOIN_UPDATE -> {
                    this.showUpdateMatchJoin();
                }
                case MATCH_CREATE_UPDATE -> {
                    Integer matchId = (Integer) payload;
                    this.showUpdateMatchCreate(matchId);
                }
                case LOBBY_PLAYERS_UPDATE -> {
                    List<String> nicknames = (List<String>) payload;
                    this.showUpdateLobbyPlayers(nicknames);
                }
                case SET_INITIAL_SETTINGS_UPDATE -> {
                    NetworkMessage.InitialSettings initialSettings = (NetworkMessage.InitialSettings) payload;
                    PlayerColor color = initialSettings.color();
                    Boolean isInitialCardFacingUp = initialSettings.isInitialCardFacingUp();
                    QuestCard questCard = initialSettings.questCard();
                    this.showUpdateInitialSettings(color, isInitialCardFacingUp, questCard);
                }
                case MATCH_GAME_STATE_UPDATE -> {
                    GameState gameState = (GameState) payload;
                    this.showUpdateGameState(gameState);
                }
                case MATCH_PUBLIC_BOARD_UPDATE -> {
                    PublicBoard publicBoard = (PublicBoard) payload;
                    this.showUpdatePublicBoard(publicBoard);
                }
                case MATCH_BOARD_UPDATE -> {
                    NetworkMessage.BoardUpdatePayload boardUpdatePayload = (NetworkMessage.BoardUpdatePayload) payload;
                    String nickname = boardUpdatePayload.nickname();
                    Coordinates coordinates = boardUpdatePayload.coordinates();
                    PlayedCard playedCard = boardUpdatePayload.playedCard();
                    this.showUpdateBoard(nickname, coordinates, playedCard);
                }
                case MATCH_PLAYER_HAND_UPDATE -> {
                    PlayerHand<MixedCard> playerHand = (PlayerHand<MixedCard>) payload;
                    this.showUpdatePlayerHand(playerHand);
                }
                case MATCH_BROADCAST_MESSAGE_UPDATE -> {
                    NetworkMessage.BroadcastMessagePayload broadcastMessagePayload = (NetworkMessage.BroadcastMessagePayload) payload;
                    String sender = broadcastMessagePayload.sender();
                    String message = broadcastMessagePayload.message();

                }
                case MATCH_PRIVATE_MESSAGE_UPDATE -> {
                    NetworkMessage.PrivateMessagePayload privateMessagePayload = (NetworkMessage.PrivateMessagePayload) payload;
                    String sender = privateMessagePayload.sender();
                    String recipient = privateMessagePayload.receiver();
                    String message = privateMessagePayload.message();

                }
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }
}
