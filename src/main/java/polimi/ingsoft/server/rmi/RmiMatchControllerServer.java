package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiMatchControllerServer implements VirtualMatchServer {

    private final MatchController matchController;

    private final List<VirtualView> clients;

    private final PrintStream logger;

    private final BlockingQueue<RmiMethodCall> methodQueue = new LinkedBlockingQueue<>();

    public RmiMatchControllerServer(MatchController mainController, List<VirtualView> clients, PrintStream logger) {
        this.matchController = mainController;
        this.clients = clients;
        this.logger = logger;
        this.methodWorkerThread.start();
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
            case SET_COLOR_REQUEST -> {
                String player = (String) args[0];
                PlayerColor color = (PlayerColor) args[1];
                VirtualView clientToUpdate = RmiServer.clients.get(player);

                try{
                    matchController.setPlayerColor(player, color);
                    GameState gameState = matchController.getGameState();
                    PlayerInitialSetting playerInitialSetting = matchController.getPlayerInitialSettingByNickname(player).orElse(null);

                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCallPlayerInitialSetting = new RmiMethodCall(MessageCodes.SET_INITIAL_SETTINGS_UPDATE, new Object[]{playerInitialSetting});
                        RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE, new Object[]{gameState});

                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallPlayerInitialSetting);
                            }
                            client.handleRmiClientMessages(rmiMethodCallGameState);
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                            new Object[]{ERROR_MESSAGES.WRONG_GAME_PHASE});
                    clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                } catch (WrongStepException exception){
                    RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                            new Object[]{ERROR_MESSAGES.WRONG_STEP});
                    clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                } catch (ColorAlreadyPickedException exception){
                    RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                            new Object[]{ERROR_MESSAGES.COLOR_ALREADY_PICKED});
                    clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                } catch (InitalChoiceAlreadySetException exception){
                    RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                            new Object[]{ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET});
                    clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                }
            }

            case SET_INITIAL_CARD_REQUEST -> {
                String player = (String) args[0];
                Boolean isFaceUp = (Boolean) args[1];
                VirtualView clientToUpdate = RmiServer.clients.get(player);

                try{
                    matchController.setFaceInitialCard(player, isFaceUp);
                    GameState gameState = matchController.getGameState();
                    PlayerInitialSetting playerInitialSetting = matchController.getPlayerInitialSettingByNickname(player).orElse(null);

                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCallPlayerInitialSetting = new RmiMethodCall(MessageCodes.SET_INITIAL_SETTINGS_UPDATE, new Object[]{playerInitialSetting});
                        RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE, new Object[]{gameState});

                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallPlayerInitialSetting);
                            }
                            client.handleRmiClientMessages(rmiMethodCallGameState);
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_GAME_PHASE});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongStepException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_STEP});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (InitalChoiceAlreadySetException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

            case SET_QUEST_CARD_REQUEST -> {
                String player = (String) args[0];
                QuestCard questCard = (QuestCard) args[1];
                VirtualView clientToUpdate = RmiServer.clients.get(player);

                try{
                    matchController.setQuestCard(player, questCard);
                    GameState gameState = matchController.getGameState();
                    PlayerInitialSetting playerInitialSetting = matchController.getPlayerInitialSettingByNickname(player).orElse(null);

                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCallPlayerInitialSetting = new RmiMethodCall(MessageCodes.SET_INITIAL_SETTINGS_UPDATE, new Object[]{playerInitialSetting});
                        RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE, new Object[]{gameState});

                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallPlayerInitialSetting);
                            }
                            client.handleRmiClientMessages(rmiMethodCallGameState);
                        }

                        if(gameState.getGamePhase() == GAME_PHASE.PLAY){
                            startGameUpdate();
                        }

                    }
                } catch (WrongGamePhaseException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_GAME_PHASE});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongStepException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_STEP});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (InitalChoiceAlreadySetException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

            case MATCH_SEND_BROADCAST_MESSAGE_REQUEST -> {
                String playerNickname = (String) args[0];
                String message = (String) args[1];

                matchController.writeBroadcastMessage(playerNickname, message);

                synchronized (this.clients){
                    for(var client : this.clients){
                        client.showUpdateBroadcastChat(playerNickname, message);
                    }
                }
            }

            case MATCH_DRAW_REQUEST -> {
                String playerNickname = (String) args[0];
                TYPE_HAND_CARD deckType = (TYPE_HAND_CARD) args[1];
                PlaceInPublicBoard.Slots slot = (PlaceInPublicBoard.Slots) args[2];
                VirtualView clientToUpdate = RmiServer.clients.get(playerNickname);

                Player player = matchController.getPlayerByNickname(playerNickname)
                        .orElse(null);

                try{
                    //TODO remove return from drawCard if not necessary
                    MixedCard drawedCard = matchController.drawCard(player, deckType, slot);
                    PlayerHand playerHand = player.getHand();
                    GameState gameState = matchController.getGameState();
                    PlaceInPublicBoard<?> publicBoardUpdate = (deckType == TYPE_HAND_CARD.RESOURCE) ?
                            matchController.getPublicBoard().getPublicBoardResource()
                            :
                            matchController.getPublicBoard().getPublicBoardGold();

                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE,
                                new Object[]{gameState});
                        RmiMethodCall rmiMethodCallPlayerHand = new RmiMethodCall(MessageCodes.MATCH_PLAYER_HAND_UPDATE,
                                new Object[]{playerHand});
                        RmiMethodCall rmiMethodCallPublicBoard = new RmiMethodCall(MessageCodes.MATCH_PUBLIC_BOARD_UPDATE,
                                new Object[]{deckType, publicBoardUpdate});

                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallPlayerHand);
                            }

                            client.handleRmiClientMessages(rmiMethodCallPublicBoard);
                            client.handleRmiClientMessages(rmiMethodCallGameState);
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_GAME_PHASE});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongStepException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_STEP});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongPlayerForCurrentTurnException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_PLAYER_TURN});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

            case MATCH_PLACE_REQUEST -> {
                String playerNickname = (String) args[0];
                MixedCard card = (MixedCard) args[1];
                Coordinates coordinates = (Coordinates) args[2];
                Boolean isFacingUp = (Boolean) args[3];
                VirtualView clientToUpdate = RmiServer.clients.get(playerNickname);

                Player player = matchController.getPlayerByNickname(playerNickname)
                        .orElse(null);

                try{
                    // Add the card to the player board
                    matchController.placeCard(player, card, coordinates, isFacingUp);

                    GameState gameState = matchController.getGameState();
                    PlayerHand playerHand = player.getHand();

                    // Get of the played card
                    PlayedCard playedCard = player.getBoard().getCard(coordinates);

                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCallGameState = new RmiMethodCall(MessageCodes.MATCH_GAME_STATE_UPDATE,
                                new Object[]{gameState});
                        RmiMethodCall rmiMethodCallBoardUpdate = new RmiMethodCall(MessageCodes.MATCH_BOARD_UPDATE,
                                new Object[]{playerNickname, coordinates, playedCard});
                        RmiMethodCall rmiMethodCallPlayerHand = new RmiMethodCall(MessageCodes.MATCH_PLAYER_HAND_UPDATE,
                                new Object[]{playerHand});

                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                client.handleRmiClientMessages(rmiMethodCallPlayerHand);
                            }

                            client.handleRmiClientMessages(rmiMethodCallBoardUpdate);
                            client.handleRmiClientMessages(rmiMethodCallGameState);
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_GAME_PHASE});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongStepException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_STEP});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                } catch (WrongPlayerForCurrentTurnException exception){
                    synchronized (this.clients){
                        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.ERROR,
                                new Object[]{ERROR_MESSAGES.WRONG_PLAYER_TURN});
                        clientToUpdate.handleRmiClientMessages(rmiMethodCall);
                    }
                }
            }

        }
    }

    @Override
    public void setColor(String nickname, PlayerColor color) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_COLOR_REQUEST, new Object[]{nickname,color}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setIsInitialCardFacingUp(String nickname,Boolean isFaceUp) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_INITIAL_CARD_REQUEST, new Object[]{nickname, isFaceUp}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_QUEST_CARD_REQUEST, new Object[]{nickname, questCard}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendBroadcastMessage(String player, String message) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_SEND_BROADCAST_MESSAGE_REQUEST, new Object[]{player, message}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendPrivateMessage(String player, String recipient, String message) throws RemoteException {

    }

    @Override
    public void drawCard(String player, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_DRAW_REQUEST, new Object[]{player, deckType, slot}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void placeCard(String player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_PLACE_REQUEST, new Object[]{player, card, coordinates, facingUp}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void startGameUpdate() throws IOException {
        PlaceInPublicBoard<ResourceCard> resourcePublicBoard = matchController.getPublicBoard().getPublicBoardResource();
        PlaceInPublicBoard<GoldCard> goldPublicBoard = matchController.getPublicBoard().getPublicBoardGold();
        PlaceInPublicBoard<QuestCard> questPublicBoard = matchController.getPublicBoard().getPublicBoardQuest();
        Map<String, Board> playerBoards = matchController.getPlayerBoards();

        RmiMethodCall rmiMethodCall = new RmiMethodCall(MessageCodes.GAME_START_UPDATE,
                new Object[]{resourcePublicBoard, goldPublicBoard, questPublicBoard, playerBoards});

        for(var client : this.clients){
            try{
                client.handleRmiClientMessages(rmiMethodCall);
            } catch (RemoteException exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
