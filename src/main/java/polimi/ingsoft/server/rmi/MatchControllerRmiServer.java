package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchController;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MatchControllerRmiServer implements VirtualMatchController {

    private final MatchController matchController;

    private final List<VirtualView> clients;

    private final BlockingQueue<RmiMethodCall> methodQueue = new LinkedBlockingQueue<>();

    public MatchControllerRmiServer(MatchController mainController, List<VirtualView> clients) {
        this.matchController = mainController;
        this.clients = clients;
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
            case MATCH_SEND_MESSAGE_REQUEST -> {
                Player player = (Player) args[0];
                String message = (String) args[1];

                try{
                    this.writeMessage(player, message);

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
                    MixedCard drawedCard = this.takeCard(player, deckType, slot);

                    synchronized (this.clients){
                        for(var client : this.clients){
                            //client.showUpdateDraw();
                            //client.showUpdateGameState();
                        }
                    }
                } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception){
                    //TODO Add Exception Handler
                    //client.reportErrorDraw();
                }
            }

            case MATCH_PLACE_REQUEST -> {
                Player player = (Player) args[0];
                MixedCard card = (MixedCard) args[1];
                Coordinates coordinates = (Coordinates) args[2];
                Boolean isFacingUp = (Boolean) args[3];

                try{
                    // Add the card to the player board
                    this.addBoardCard(player, card, coordinates, isFacingUp);

                    // Get of the played card
                    PlayedCard playedCard = player.getBoard().getCard(coordinates);

                    synchronized (this.clients){
                        for(var client : clients){
                            //client.showUpdateBoard(player, coordinates, playedCard);
                            //client.showUpdateGameState();
                        }
                    }
                } catch (WrongStepException | WrongPlayerForCurrentTurnException | WrongGamePhaseException exception){
                    //TODO Add Exception Handler
                    //client.reportErrorPlace();
                }
            }

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

    private void writeMessage(Player player, String message){
        matchController.writeMessage(message);
    }

    private MixedCard takeCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws WrongGamePhaseException, WrongPlayerForCurrentTurnException, WrongStepException {
        return matchController.drawCard(player, deckType, slot);
    }

    private void addBoardCard(Player player, MixedCard card, Coordinates coordinates, Boolean isFacingUp) throws WrongGamePhaseException, WrongPlayerForCurrentTurnException, WrongStepException {
        matchController.placeCard(player, card, coordinates, isFacingUp);
    }
}
