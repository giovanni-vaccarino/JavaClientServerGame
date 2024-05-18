package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiMatchControllerServer implements VirtualMatchController {

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
            case SET_COLOR_REQUEST -> {
                String player = (String) args[0];
                PlayerColors color = (PlayerColors) args[1];

                try{
                    matchController.setPlayerColor(player, color);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            /*
                            TODO retrieve clientToUpdate
                            if(client == clientToUpdate){
                                client.showUpdateColor();
                            }
                            */
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    //TODO handle exception
                    //client.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    //client.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (ColorAlreadyPickedException exception){
                    //client.reportError(ERROR_MESSAGES.COLOR_ALREADY_PICKED);
                } catch (InitalChoiceAlreadySetException exception){
                    //client.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case SET_INITIAL_REQUEST -> {
                String player = (String) args[0];
                Boolean isFaceUp = (Boolean) args[1];

                try{
                    matchController.setFaceInitialCard(player, isFaceUp);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            /*
                            TODO retrieve clientToUpdate
                            if(client == clientToUpdate){
                                client.showUpdateColor();
                            }
                            */
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    //TODO handle exception
                    //client.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    //client.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (InitalChoiceAlreadySetException exception){
                    //client.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case SET_QUEST_REQUEST -> {
                String player = (String) args[0];
                QuestCard questCard = (QuestCard) args[1];

                try{
                    matchController.setQuestCard(player, questCard);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            /*
                            TODO retrieve clientToUpdate
                            if(client == clientToUpdate){
                                client.showUpdateColor();
                            }
                            */
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongGamePhaseException exception){
                    //TODO handle exception
                    //client.reportError(ERROR_MESSAGES.WRONG_GAME_PHASE);
                } catch (WrongStepException exception){
                    //client.reportError(ERROR_MESSAGES.WRONG_STEP);
                } catch (InitalChoiceAlreadySetException exception){
                    //client.reportError(ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case MATCH_SEND_MESSAGE_REQUEST -> {
                Player player = (Player) args[0];
                String message = (String) args[1];

                try{
                    matchController.writeMessage(player.getNickname(), message);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            //client.showUpdateChat();
                        }
                    }

                }catch (Exception e){
                    //client.reportUpdateChatError();
                    System.out.println(".");
                }
            }

            case MATCH_DRAW_REQUEST -> {
                Player player = (Player) args[0];
                String deckType = (String) args[1];
                PlaceInPublicBoard.Slots slot = (PlaceInPublicBoard.Slots) args[2];

                try{
                    MixedCard drawedCard = matchController.drawCard(player, deckType, slot);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            /*
                            TODO retrieve clientToUpdate
                            if(client == clientToUpdate){
                                client.showUpdateDraw(drawedCard);
                            }
                            */
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception){
                    //TODO Add Exception Handler
                    //client.reportErrorDraw();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case MATCH_PLACE_REQUEST -> {
                Player player = (Player) args[0];
                MixedCard card = (MixedCard) args[1];
                Coordinates coordinates = (Coordinates) args[2];
                Boolean isFacingUp = (Boolean) args[3];

                try{
                    // Add the card to the player board
                    matchController.placeCard(player, card, coordinates, isFacingUp);

                    // Get of the played card
                    PlayedCard playedCard = player.getBoard().getCard(coordinates);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            /*
                            TODO retrieve clientToUpdate
                            if(client == clientToUpdate){
                                client.showUpdatePlayerHand();
                            }
                            */
                            client.showUpdateBoard(player, coordinates, playedCard);
                            client.showUpdateGameState(matchController.getGameState());
                        }
                    }
                } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception ){
                    //client.reportErrorPlace();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @Override
    public void setColor(String nickname,PlayerColors color) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_COLOR_REQUEST, new Object[]{color}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setFaceInitialCard(String nickname,Boolean isFaceUp) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_INITIAL_REQUEST, new Object[]{isFaceUp}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.SET_QUEST_REQUEST, new Object[]{questCard}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendMessage(Player player, String message) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_SEND_MESSAGE_REQUEST, new Object[]{player, message}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendPrivateMessage(Player player, String message) throws RemoteException {

    }

    @Override
    public void drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_DRAW_REQUEST, new Object[]{player, deckType, slot}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException {
        try {
            methodQueue.put(new RmiMethodCall(MessageCodes.MATCH_PLACE_REQUEST, new Object[]{player, card, coordinates, facingUp}));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
