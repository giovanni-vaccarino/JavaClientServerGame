package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Utils;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NicknameNotAvailableException;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.rmi.RmiMethodCall;
import polimi.ingsoft.server.common.protocol.MessageCodes;
import polimi.ingsoft.server.common.protocol.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionHandler implements Runnable, VirtualView {
    private final Socket socket;
    private final MainController controller;
    private MatchController matchController = null;
    private final VirtualView view;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final SocketServer server;

    private final PrintStream logger;
    private String nickname = Utils.getRandomNickname();

    public ConnectionHandler(Socket socket, MainController controller, SocketServer server, PrintStream logger) throws IOException {
        this.socket = socket;
        this.controller = controller;
        this.server = server;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.view = new ClientProxy(out);
        this.logger = logger;
        this.server.addClient(this, nickname);
    }

    @Override
    public void run() {
        try {
            NetworkMessage item;

            while ((item = (NetworkMessage) in.readObject()) != null) {
                MessageCodes type = item.type;
                Object payload = item.payload;
                logger.println("SOCKET: Received request with type: " + type.toString());
                logger.println("SOCKET: And payload: " + payload);

                // Read message and perform action
                try {
                    switch (type) {
                        case CONNECT -> this.addClient();
                        case SET_NICKNAME_REQUEST -> {
                            String nickname = (String) payload;
                            try {
                                this.server.setNicknameForClient(this.nickname, nickname);
                                this.nickname = nickname;
                                this.server.singleUpdateNickname(this);
                            } catch (NicknameNotAvailableException exception) {
                                this.reportError(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE);
                            }
                        }
                        case MATCHES_LIST_REQUEST -> {
                            List<Integer> matches = controller.getMatches();
                            this.server.singleUpdateMatchesList(this, matches);
                        }
                        case MATCH_JOIN_REQUEST -> {
                            int id = (Integer) payload;
                            try {
                                controller.joinMatch(id, nickname);
                                this.server.singleUpdateMatchJoin(this);

                                MatchController matchController = controller.getMatch(id);
                                GameState gameState = matchController.getGameState();
                                // Set match controller for usage later
                                this.matchController = matchController;
                                List<String> nicknames = matchController.getNamePlayers();
                                this.server.lobbyUpdatePlayerJoin(nicknames);
                                //Adding the client to the match notification list
                                synchronized (this.server.matchNotificationList){
                                    this.server.matchNotificationList.get(id).add(this);
                                }
                                if (gameState.getGamePhase() == GAME_PHASE.INITIALIZATION) {
                                    this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        gameState
                                    );
                                }
                            } catch (MatchAlreadyFullException exception) {
                                this.reportError(ERROR_MESSAGES.MATCH_IS_ALREADY_FULL);
                            } catch (MatchNotFoundException exception) {
                                this.reportError(ERROR_MESSAGES.MATCH_DOES_NOT_EXIST);
                            }
                        }
                        case MATCH_CREATE_REQUEST -> {
                            int numberForPlayers = (int) payload;
                            int id = controller.createMatch(numberForPlayers);
                            this.server.singleUpdateMatchCreate(this, id);
                            List<Integer> matches = controller.getMatches();
                            this.server.broadcastUpdateMatchesList(matches);
                            // Creating a new list for the players
                            synchronized (this.server.matchNotificationList){
                                this.server.matchNotificationList.put(id, new ArrayList<>());
                            }
                            // It is client's responsibility to join the match right after
                        }
                        case SET_COLOR_REQUEST -> {
                            PlayerColor color = (PlayerColor) payload;

                            try {
                                matchController.setPlayerColor(nickname, color);
                                PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
                                this.server.singleUpdateInitialSettings(
                                        this,
                                        settings
                                );
                                this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        matchController.getGameState()
                                );
                            } catch (NullPointerException exception) {
                                this.reportError(ERROR_MESSAGES.PLAYER_IS_NOT_IN_A_MATCH);
                            } catch (WrongGamePhaseException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                            } catch (WrongStepException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_STEP);
                            } catch (ColorAlreadyPickedException exception) {
                                this.reportError(ERROR_MESSAGES.COLOR_ALREADY_PICKED);
                            } catch (InitalChoiceAlreadySetException exception) {
                                this.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                            }
                        }
                        case SET_INITIAL_CARD_REQUEST -> {
                            Boolean isInitialCardFacingUp = (Boolean) payload;

                            try {
                                matchController.setFaceInitialCard(nickname, isInitialCardFacingUp);
                                PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
                                this.server.singleUpdateInitialSettings(
                                        this,
                                        settings
                                );
                                this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        matchController.getGameState()
                                );
                            } catch (NullPointerException exception) {
                                this.reportError(ERROR_MESSAGES.PLAYER_IS_NOT_IN_A_MATCH);
                            } catch (WrongGamePhaseException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                            } catch (WrongStepException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_STEP);
                            } catch (InitalChoiceAlreadySetException exception) {
                                this.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                            }
                        }
                        case SET_QUEST_CARD_REQUEST -> {
                            QuestCard questCard = (QuestCard) payload;

                            try {
                                matchController.setQuestCard(nickname, questCard);
                                PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
                                this.server.singleUpdateInitialSettings(
                                        this,
                                        settings
                                );
                                if (matchController.getGameState().getGamePhase() == GAME_PHASE.PLAY)
                                    this.startGameUpdate();
                                this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        matchController.getGameState()
                                );
                            } catch (NullPointerException exception) {
                                this.reportError(ERROR_MESSAGES.PLAYER_IS_NOT_IN_A_MATCH);
                            } catch (WrongGamePhaseException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                            } catch (WrongStepException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_STEP);
                            } catch (InitalChoiceAlreadySetException exception) {
                                this.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                            }
                        }
                        case MATCH_SEND_BROADCAST_MESSAGE_REQUEST -> {
                            NetworkMessage.BroadcastMessagePayload broadcastMessagePayload = (NetworkMessage.BroadcastMessagePayload) payload;
                            String sender = broadcastMessagePayload.sender();
                            String message = broadcastMessagePayload.message();

                            Message messageSent = matchController.writeBroadcastMessage(sender, message);
                            this.server.matchUpdateBroadcastMessage(
                                    matchController.getMatchId(),
                                    messageSent
                            );
                        }
                        case MATCH_SEND_PRIVATE_MESSAGE_REQUEST -> {
                            NetworkMessage.PrivateMessagePayload privateMessagePayload = (NetworkMessage.PrivateMessagePayload) payload;
                            String sender = privateMessagePayload.sender();
                            String recipient = privateMessagePayload.receiver();
                            String message = privateMessagePayload.message();

                            try {
                                Message messageSent = matchController.writePrivateMessage(sender, recipient, message);
                                this.server.singleUpdatePrivateMessage(sender, recipient, messageSent);
                                this.server.singleUpdatePrivateMessage(recipient, recipient, messageSent);
                            } catch (PlayerNotFoundException e) {
                                this.reportError(ERROR_MESSAGES.PLAYER_NOT_FOUND);
                            }
                        }
                        case MATCH_DRAW_REQUEST -> {
                            NetworkMessage.DrawCardPayload drawCardPayload = (NetworkMessage.DrawCardPayload) payload;
                            TYPE_HAND_CARD deckType = drawCardPayload.deckType();
                            PlaceInPublicBoard.Slots slot = drawCardPayload.slot();

                            Player player = matchController.getPlayerByNickname(nickname)
                                    .orElse(null);

                            try {
                                matchController.drawCard(player, deckType, slot);
                                this.server.singleUpdatePlayerHand(this, player.getHand());

                                this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        matchController.getGameState()
                                );
                                this.server.matchUpdatePublicBoard(
                                        matchController.getMatchId(),
                                        matchController.getPublicBoard()
                                );
                            } catch (NullPointerException exception) {
                                this.reportError(ERROR_MESSAGES.UNKNOWN_ERROR);
                            } catch (WrongGamePhaseException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                            } catch (WrongStepException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_STEP);
                            } catch (WrongPlayerForCurrentTurnException exception) {
                                this.reportError(ERROR_MESSAGES.WRONG_PLAYER_TURN);
                            }
                        }
                        case MATCH_PLACE_REQUEST -> {
                            NetworkMessage.PlaceCardPayload placeCardPayload = (NetworkMessage.PlaceCardPayload) payload;
                            MixedCard card = placeCardPayload.card();
                            Coordinates coordinates = placeCardPayload.coordinates();
                            Boolean isFacingUp = placeCardPayload.isFacingUp();

                            Player player = matchController.getPlayerByNickname(nickname)
                                    .orElse(null);

                            try {
                                matchController.placeCard(player, card, coordinates, isFacingUp);
                                PlayedCard playedCard = player.getBoard().getCard(coordinates);
                                this.server.singleUpdatePlayerHand(this, player.getHand());
                                this.server.matchUpdateGameState(
                                        matchController.getMatchId(),
                                        matchController.getGameState()
                                );
                                this.server.matchUpdatePlayerBoard(
                                        matchController.getMatchId(),
                                        nickname,
                                        coordinates,
                                        playedCard
                                );
                            } catch (NullPointerException exception) {
                                this.reportError(ERROR_MESSAGES.UNKNOWN_ERROR);
                            } catch (WrongGamePhaseException exception){
                                this.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                            } catch (WrongStepException exception){
                                this.reportError(ERROR_MESSAGES.WRONG_STEP);
                            } catch (WrongPlayerForCurrentTurnException exception){
                                this.reportError(ERROR_MESSAGES.WRONG_PLAYER_TURN);
                            } catch (CoordinateNotValidException e) {
                                throw new RuntimeException(e);
                            } catch (NotEnoughResourcesException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        default -> {
                            logger.println("SOCKET: [INVALID MESSAGE]");
                            this.reportError(ERROR_MESSAGES.UNKNOWN_COMMAND);
                        }
                    }
                } catch (ClassCastException exception) {
                    this.reportError(ERROR_MESSAGES.UNKNOWN_COMMAND);
                }
            }

            // Shutdown socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addClient() {
        synchronized (this.server.clients) {
            this.server.clients.put(nickname, this);
        }
    }

    @Override
    public void handleRmiClientMessages(RmiMethodCall rmiMethodCall) {

    }

    @Override
    public void showNicknameUpdate() throws IOException {
        logger.println("SOCKET: Sending nickname update");
        synchronized (this.view) {
            this.view.showNicknameUpdate();
        }
    }

    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        logger.println("SOCKET: Sending lobby player updates: " + players);
        synchronized (this.view) {
            this.view.showUpdateLobbyPlayers(players);
        }
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        logger.println("SOCKET: Sending matches list update: " + matches.toString());
        synchronized (this.view) {
            this.view.showUpdateMatchesList(matches);
        }
    }

    @Override
    public void showUpdateMatchJoin() {
        logger.println("SOCKET: Sending match join update");
        synchronized (this.view) {
            try {
                this.view.showUpdateMatchJoin();
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateMatchCreate(Integer matchId) throws IOException {
        logger.println("SOCKET: Sending match create update: " + matchId);
        synchronized (this.view) {
            this.view.showUpdateMatchCreate(matchId);
        }
    }

    @Override
    public void showUpdateBroadcastChat(Message message) {
        synchronized (this.view) {
            try {
                this.view.showUpdateBroadcastChat(message);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePrivateChat(String recipient, Message message) {
        synchronized (this.view) {
            try {
                this.view.showUpdatePrivateChat(recipient, message);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        logger.println("SOCKET: Sending update player initial settings to " + nickname);
        synchronized (this.view) {
            try {
                this.view.showUpdateInitialSettings(playerInitialSetting);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        logger.println("SOCKET: Sending update game state to " + nickname);
        synchronized (this.view) {
            try {
                this.view.showUpdateGameState(gameState);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateGameStart(PlaceInPublicBoard<ResourceCard> resource, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quest, Map<String, Board> boards) throws IOException {
        logger.println("SOCKET: Sending update game state start to " + nickname);
        synchronized (this.view) {
            try {
                this.view.showUpdateGameStart(resource, gold, quest, boards);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePlayerHand(PlayerHand playerHand) {
        synchronized (this.view) {
            try {
                this.view.showUpdatePlayerHand(playerHand);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException {

    }

    @Override
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard) {
        synchronized (this.view) {
            try {
                this.view.showUpdateBoard(nickname, coordinates, playedCard);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        synchronized (this.view) {
            try {
                this.view.reportError(errorMessage);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void setMatchControllerServer(VirtualMatchServer matchServer) { }

    private void startGameUpdate() {
        PlaceInPublicBoard<ResourceCard> resourcePublicBoard = matchController.getPublicBoard().getPublicBoardResource();
        PlaceInPublicBoard<GoldCard> goldPublicBoard = matchController.getPublicBoard().getPublicBoardGold();
        PlaceInPublicBoard<QuestCard> questPublicBoard = matchController.getPublicBoard().getPublicBoardQuest();
        Map<String, Board> playerBoards = matchController.getPlayerBoards();

        this.server.matchUpdateGameStart(
                matchController.getMatchId(),
                resourcePublicBoard,
                goldPublicBoard,
                questPublicBoard,
                playerBoards
        );
    }
}
