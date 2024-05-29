package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.cli.Printer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class GameManager implements CLIPhaseManager {

    private static class GameModel {
        List<String> nicknames;
        Map<String, Board> playerBoards;
        PlaceInPublicBoard<ResourceCard> resourceCards;
        PlaceInPublicBoard<GoldCard> goldCards;
        PlaceInPublicBoard<QuestCard> questCards;
        QuestCard personalQuestCard;
        PlayerHand hand;
        GAME_PHASE gamePhase;
        TURN_STEP turnStep;
        String currentPlayerNickname;

        public GameModel() {
            gamePhase = GAME_PHASE.PLAY;
        }
    }

    private static class TemporaryModel {
        InitialCard initialCard;
        boolean isInitialCardFacingUp;
        GameState initialGameState;
    }

    private GameModel model = new GameModel();
    private TemporaryModel temporaryModel = new TemporaryModel();

    enum State {
        WAITING_FOR_PUBLIC_BOARDS,
        WAITING_FOR_PLAYER_BOARDS,
        WAITING_FOR_INITIALIZATION,
        WAITING_FOR_TURN,
        PLACE,
        WAITING_FOR_PLACE,
        DRAW,
        WAITING_FOR_DRAW
    }

    private State state = State.WAITING_FOR_PUBLIC_BOARDS;

    private final Scanner in;
    private final PrintStream out;
    private final CLI cli;
    private final Printer printer;

    public GameManager(Scanner in, PrintStream out, CLI cli) {
        this.in = in;
        this.out = out;
        this.cli = cli;
        this.printer = new Printer(out);
    }

    @Override
    public void start() {
        out.println(MESSAGES.GAME_START.getValue());

//        out.println("\n\nGAME MODEL REPORT: \n");
//        out.println("Nicknames: " + model.nicknames);
//        out.println("Player boards: " + model.playerBoards);
//        out.println("Player hand: " + model.hand.getCards());
//        out.println("Game phase: " + model.gamePhase);
//        out.println("Turn step: " + model.turnStep);
//        out.println("Current player: " + model.currentPlayerNickname);
//        out.println("State: " + state);

        updateGameState(temporaryModel.initialGameState);
    }

    public void initializeModel(
        PlayerHand hand,
        QuestCard personalQuestCard,
        InitialCard initialCard,
        boolean isInitialCardFacingUp,
        GameState gameState
    ) {
        if (state == State.WAITING_FOR_INITIALIZATION) {
            model.hand = hand;
            model.personalQuestCard = personalQuestCard;
            model.turnStep = gameState.getCurrentTurnStep();
            model.currentPlayerNickname = gameState.getCurrentPlayerNickname();
            temporaryModel.initialCard = initialCard;
            temporaryModel.isInitialCardFacingUp = isInitialCardFacingUp;
            temporaryModel.initialGameState = gameState;
            state = State.WAITING_FOR_TURN;
        }
    }

    public void initializePublicBoards(
            PlaceInPublicBoard<ResourceCard> resourceCards,
            PlaceInPublicBoard<GoldCard> goldCards,
            PlaceInPublicBoard<QuestCard> questCards
    ) {
        if (state == State.WAITING_FOR_PUBLIC_BOARDS) {
            model.resourceCards = resourceCards;
            model.goldCards = goldCards;
            model.questCards = questCards;
            state = State.WAITING_FOR_PLAYER_BOARDS;
        }
    }

    public void initializeBoards(Map<String, Board> playerBoard) {
        if (state == State.WAITING_FOR_PLAYER_BOARDS) {
            model.playerBoards = playerBoard;
            model.nicknames = playerBoard.keySet().stream().toList();
            state = State.WAITING_FOR_INITIALIZATION;
        }
    }

    public void updateGameState(GameState gameState) {
        GAME_PHASE gamePhase = gameState.getGamePhase();
        TURN_STEP turnStep = gameState.getCurrentTurnStep();
        String currentPlayerNickname = gameState.getCurrentPlayerNickname();

        boolean isYourTurn = Objects.equals(currentPlayerNickname, cli.getNickname());

        if (isYourTurn && state == State.WAITING_FOR_TURN) {
            out.println(MESSAGES.IS_YOUR_TURN.getValue());
            state = State.PLACE;
        } else if (isYourTurn && state == State.WAITING_FOR_PLACE) {
            out.println(MESSAGES.CARD_SUCCESSFULLY_PLACED.getValue());
            state = State.DRAW;
        } else if (state == State.WAITING_FOR_DRAW) {
            out.println(MESSAGES.CARD_SUCCESSFULLY_DRAWN.getValue());
            state = State.WAITING_FOR_TURN;
        }

        if (!Objects.equals(currentPlayerNickname, model.currentPlayerNickname))
            out.println(MESSAGES.CURRENT_PLAYER.getValue() + currentPlayerNickname);

        if (gamePhase == GAME_PHASE.PLAY) {
            if (isYourTurn)
                playTurn(turnStep);
            else
                showTurn(turnStep);

            if (turnStep == TURN_STEP.PLACE) {

            } else if (turnStep == TURN_STEP.DRAW) {

            }
        } else if (gamePhase == GAME_PHASE.LAST_ROUND) {

        } else if (gamePhase == GAME_PHASE.END) {

        }

        model.gamePhase = gamePhase;
        model.turnStep = turnStep;
        model.currentPlayerNickname = currentPlayerNickname;
    }

    private void playTurn(TURN_STEP turnStep) {
        if (turnStep == TURN_STEP.PLACE && state == State.PLACE) {
            runPlaceCard();
        } else if (turnStep == TURN_STEP.DRAW && state == State.DRAW) {
            runDrawCard();
        }
    }

    private void showTurn(TURN_STEP turnStep) {
        if (turnStep == TURN_STEP.PLACE) {
            showPlaceCard();
        } else if (turnStep == TURN_STEP.DRAW) {
            showDrawCard();
        }
    }

    private void runPlaceCard() {
        // Print board
        Board board = model.playerBoards.get(model.currentPlayerNickname);

        // Print player hand
        PlayerHand hand = model.hand;

        // Get card choice
        in.nextLine();
        // Get coords choice
        in.nextLine();

        try {
            // TODO put real args
            cli.getClient().placeCard(cli.getNickname(), model.hand.get(0), new Coordinates(0, 0), true);
        } catch (IOException e) {
            parseError(ERROR_MESSAGES.UNABLE_TO_PLACE_CARD);
        }
        state = State.WAITING_FOR_PLACE;
    }

    private void runDrawCard() {
        // Print public board
        PlaceInPublicBoard<ResourceCard> resourceCards = model.resourceCards;
        PlaceInPublicBoard<GoldCard> goldCards = model.goldCards;

        // Print player hand
        PlayerHand hand = model.hand;

        // Get card choice
        in.nextLine();

        try {
            // TODO put real args
            cli.getClient().drawCard(cli.getNickname(), TYPE_HAND_CARD.RESOURCE, PlaceInPublicBoard.Slots.SLOT_A);
        } catch (IOException e) {
            parseError(ERROR_MESSAGES.UNABLE_TO_DRAW_CARD);
        }
        state = State.WAITING_FOR_DRAW;
    }

    private void showPlaceCard() {
        out.println(model.currentPlayerNickname + MESSAGES.OTHER_PLAYER_PERFORMING_PLACE.getValue());
    }

    private void showDrawCard() {
        out.println(model.currentPlayerNickname + MESSAGES.OTHER_PLAYER_PERFORMING_DRAW.getValue());
    }

    @Override
    public void parseError(ERROR_MESSAGES error) {
        // TODO Maybe remove this one
        out.println("UNEXPECTED ERROR: " + error.getValue());
    }
}
