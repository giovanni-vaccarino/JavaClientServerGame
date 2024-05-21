package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
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

                    synchronized (this.clients){
                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                //client.showUpdateColor();
                            }
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (ColorAlreadyPickedException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.COLOR_ALREADY_PICKED);
                } catch (InitalChoiceAlreadySetException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                }
            }

            case SET_INITIAL_CARD_REQUEST -> {
                String player = (String) args[0];
                Boolean isFaceUp = (Boolean) args[1];
                VirtualView clientToUpdate = RmiServer.clients.get(player);

                try{
                    matchController.setFaceInitialCard(player, isFaceUp);

                    synchronized (this.clients){
                        for(var client : this.clients){

                            if(client.equals(clientToUpdate)){
                                //client.showUpdateColor();
                            }

                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (InitalChoiceAlreadySetException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                }
            }

            case SET_QUEST_CARD_REQUEST -> {
                String player = (String) args[0];
                QuestCard questCard = (QuestCard) args[1];
                VirtualView clientToUpdate = RmiServer.clients.get(player);

                try{
                    matchController.setQuestCard(player, questCard);

                    synchronized (this.clients){
                        for(var client : this.clients){

                            if(client.equals(clientToUpdate)){
                                //client.showUpdateColor();
                            }

                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (InitalChoiceAlreadySetException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                }
            }

            case MATCH_SEND_MESSAGE_REQUEST -> {
                String playerNickname = (String) args[0];
                String message = (String) args[1];

                Message addedMessage = matchController.writeMessage(playerNickname, message);

                synchronized (this.clients){
                    for(var client : this.clients){
                        client.showUpdateChat(addedMessage);
                    }
                }
            }

            case MATCH_DRAW_REQUEST -> {
                String playerNickname = (String) args[0];
                String deckType = (String) args[1];
                PlaceInPublicBoard.Slots slot = (PlaceInPublicBoard.Slots) args[2];
                VirtualView clientToUpdate = RmiServer.clients.get(playerNickname);

                Player player = matchController.getPlayerByNickname(playerNickname)
                        .orElse(null);


                try{
                    MixedCard drawedCard = matchController.drawCard(player, deckType, slot);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                //client.showUpdatePlayerHand(drawedCard);
                            }

                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (WrongPlayerForCurrentTurnException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_PLAYER_TURN);
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

                    // Get of the played card
                    PlayedCard playedCard = player.getBoard().getCard(coordinates);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            if(client.equals(clientToUpdate)){
                                //client.showUpdatePlayerHand();
                            }

                            client.showUpdateBoard(player, coordinates, playedCard);
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (WrongPlayerForCurrentTurnException exception){
                    clientToUpdate.reportError(ERROR_MESSAGES.WRONG_PLAYER_TURN);
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
    public void sendMessage(String player, String message) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_SEND_MESSAGE_REQUEST, new Object[]{player, message}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendPrivateMessage(String player, String message) throws RemoteException {

    }

    @Override
    public void drawCard(String player, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {
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
}
