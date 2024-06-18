package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.exceptions.ExceptionHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MatchServer implements VirtualMatchServer {
    private final PrintStream logger;
    private final MatchController matchController;
    // TODO temp
    private final Server server;

    private final Map<Class<? extends Exception>, ExceptionHandler> exceptionHandlers = new HashMap<>();


    public MatchServer(PrintStream logger, MatchController controller, Server server) {
        this.logger = logger;
        this.matchController = controller;
        this.server = server;

        initExceptionHandlers();
    }

    @Override
    public void sendMessage(MatchServerCommand command) throws IOException {

    }

    @Override
    public void setColor(String nickname, PlayerColor color) throws IOException {
        VirtualView clientToUpdate = server.getClient(nickname).getVirtualView();

        try {
            matchController.setPlayerColor(nickname, color);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void setIsInitialCardFacingUp(String nickname, Boolean isInitialCardFacingUp) throws IOException {
        VirtualView clientToUpdate = server.getClient(nickname).getVirtualView();

        try {
            matchController.setFaceInitialCard(nickname, isInitialCardFacingUp);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws IOException {
        VirtualView clientToUpdate = server.getClient(nickname).getVirtualView();

        try {
            matchController.setQuestCard(nickname, questCard);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            if (matchController.getGameState().getGamePhase() == GAME_PHASE.PLAY)
                this.startGameUpdate();
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void sendBroadcastMessage(String player, String message) throws IOException {
        Message messageSent = matchController.writeBroadcastMessage(player, message);
        this.server.matchUpdateBroadcastMessage(
                matchController.getMatchId(),
                messageSent
        );
    }

    @Override
    public void sendPrivateMessage(String player, String recipient, String message) throws IOException {
        VirtualView clientToUpdate = server.getClient(player).getVirtualView();
        System.out.println("Sto mandando messaggio 2");
        try {
            Message _message = matchController.writePrivateMessage(player, recipient, message);
            System.out.println("Sto mandando messaggio 3");
            this.server.singleUpdatePrivateMessage(matchController.getMatchId(), player, recipient, _message);
            this.server.singleUpdatePrivateMessage(matchController.getMatchId(), recipient, player, _message);
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void drawCard(String nickname, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        VirtualView clientToUpdate = server.getClient(nickname).getVirtualView();
        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);

        try {
            matchController.drawCard(player, deckType, slot);
            PlaceInPublicBoard<?> publicBoardUpdate = (deckType == TYPE_HAND_CARD.RESOURCE) ?
                    matchController.getPublicBoard().getPublicBoardResource()
                    :
                    matchController.getPublicBoard().getPublicBoardGold();

            this.server.singleUpdatePlayerHand(matchController.getMatchId(), clientToUpdate, player.getHand());
            this.server.matchUpdatePublicBoard(
                    matchController.getMatchId(),
                    deckType,
                    publicBoardUpdate
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void placeCard(String nickname, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        VirtualView clientToUpdate = server.getClient(nickname).getVirtualView();
        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);

        try {
            matchController.placeCard(player, card, coordinates, facingUp);
            PlayedCard playedCard = player.getBoard().getCard(coordinates);
            Integer score = player.getBoard().getScore();
            this.server.singleUpdatePlayerHand(matchController.getMatchId(), clientToUpdate, player.getHand());
            this.server.matchUpdatePlayerBoard(
                    matchController.getMatchId(),
                    nickname,
                    coordinates,
                    playedCard,
                    score
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );

            if(matchController.getGameState().getGamePhase() == GAME_PHASE.END){
                server.deleteGame(matchController.getMatchId());
            }
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void ping(String nickname) throws IOException {

    }

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

    private void initExceptionHandlers() {
        exceptionHandlers.put(NullPointerException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.UNKNOWN_ERROR));

        exceptionHandlers.put(WrongGamePhaseException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_GAME_PHASE));

        exceptionHandlers.put(WrongStepException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_STEP));

        exceptionHandlers.put(ColorAlreadyPickedException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.COLOR_ALREADY_PICKED));

        exceptionHandlers.put(InitalChoiceAlreadySetException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET));

        exceptionHandlers.put(WrongPlayerForCurrentTurnException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_PLAYER_TURN));

        exceptionHandlers.put(PlayerNotFoundException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.PLAYER_NOT_FOUND));

        exceptionHandlers.put(CoordinateNotValidException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.COORDINATE_NOT_VALID));

        exceptionHandlers.put(NotEnoughResourcesException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.NOT_ENOUGH_RESOURCES));
    }

    private void handleException(VirtualView clientToUpdate, Exception exception) {
        ExceptionHandler handler = exceptionHandlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(clientToUpdate, exception);
        } else {
            server.reportError(clientToUpdate, ERROR_MESSAGES.UNKNOWN_ERROR);
        }
    }
}
