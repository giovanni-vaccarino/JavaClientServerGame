package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.*;
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
                //do {
                    runPlaceCard(model.personalQuestCard);
                    printer.printFromBoard(model.playerBoards.get(model.currentPlayerNickname), model.hand,null, model.personalQuestCard);
                    //TODO add a waiter for played card confirmation
                //}while(state==State.WAITING_FOR_PLACE);
            } else if (turnStep == TURN_STEP.DRAW) {
                runDrawCard();
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
            runPlaceCard(model.personalQuestCard);
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

    private void runPlaceCard(QuestCard questCard) {
        int card,x,y,side;
        boolean facingUp=true;
        MixedCard chosenCard;
        // Print board
        Board board = model.playerBoards.get(model.currentPlayerNickname);
//        do {
            String choice,secondChoice;
            PlayerHand hand = model.hand;

            // Get card choice
            do{
                ClientBoard.printBoard(board);
                ClientHand.print(hand, questCard);
                out.println(MESSAGES.PLAYCARDHELP1.getValue());
                choice=in.nextLine();
                if(choice.equalsIgnoreCase("flip")){
                    do {
                        System.out.println(MESSAGES.GETFLIPCHOICE.getValue());
                        secondChoice = in.nextLine();
                        if(!(secondChoice.equals("1")||secondChoice.equals("2")||secondChoice.equals("3")))out.println(MESSAGES.ERROR.getValue());
                    }while(!(secondChoice.equals("1")||secondChoice.equals("2")||secondChoice.equals("3")));
                    hand.flip(Integer.parseInt(secondChoice)-1);
                    ClientHand.print(hand,questCard);
                }
                else if(!(choice.equals("1")||choice.equals("2")||choice.equals("3")))out.print(MESSAGES.ERROR.getValue());
            }while(choice.equalsIgnoreCase("flip")||!(choice.equals("1")||choice.equals("2")||choice.equals("3")));
            card = Integer.parseInt((choice));
            // Get coords choice
            out.println(MESSAGES.PLAYCARDHELP2.getValue() + " " + board.getPrintingCoordinates().getX() + "," + board.getPrintingCoordinates().getY());
            //TODO add a catcher for wrong inputs
            out.println(MESSAGES.PLAYCARDHELPX.getValue());
            x = Integer.parseInt((in.nextLine()));
            out.println(MESSAGES.PLAYCARDHELPY.getValue());
            y = Integer.parseInt((in.nextLine()));
            //TODO until here
            chosenCard = hand.get(card-1);
            // Get side choice
            do {
                out.println(MESSAGES.PLAYCARDHELPORIENTATION.getValue());
                if(!(choice.equals(("1"))||choice.equals("2")))out.println(MESSAGES.ERROR.getValue());
            }while(!(choice.equals(("1"))||choice.equals("2")));
            side = Integer.parseInt(choice);
            if (side == 2) facingUp = false;
//            facingUp = board.add(new Coordinates(x, y), chosenCard, facingUp);
//            if(facingUp)out.println(MESSAGES.CARD_SUCCESSFULLY_PLACED);
//            else out.println(MESSAGES.ERROR);
//        }while(!facingUp);
        //in.nextLine();

        try {
            cli.getMatchServer().placeCard(cli.getNickname(),chosenCard, new Coordinates(x, y), facingUp);
        } catch (IOException e) {
            parseError(ERROR_MESSAGES.UNABLE_TO_PLACE_CARD);
        }
        state = State.WAITING_FOR_PLACE;
    }

    private void runDrawCard() {
        // Print public board
        PlaceInPublicBoard<ResourceCard> resourceCards = model.resourceCards;
        PlaceInPublicBoard<GoldCard> goldCards = model.goldCards;
        PlaceInPublicBoard<QuestCard> questCards=model.questCards;
        PlaceInPublicBoard.Slots slot;
        TYPE_HAND_CARD type;
        String choice,choice2;
        PlayerHand hand = model.hand;
        // Get card choice
        do {
            choice = "getcard";
            do {
                printer.printFromPublicBoard(resourceCards, goldCards, questCards, hand, choice, model.personalQuestCard);
                choice = in.nextLine();
                if (!choice.equalsIgnoreCase("resource") && !choice.equalsIgnoreCase("gold")) {
                    choice = "getcard";
                    out.println(MESSAGES.ERROR.getValue());
                }
            } while (!choice.equalsIgnoreCase("resource") && !choice.equalsIgnoreCase("gold"));
            choice2=choice;
            do {
                printer.printFromPublicBoard(resourceCards, goldCards, questCards, hand, choice2, model.personalQuestCard);
                choice2 = in.nextLine();
                if (!choice2.equalsIgnoreCase("back") && !choice2.equalsIgnoreCase("center") && !choice2.equalsIgnoreCase("right") && !choice2.equalsIgnoreCase("deck")) {
                    choice2 = "getcard";
                    out.println(MESSAGES.ERROR.getValue());
                }
            } while (!choice2.equalsIgnoreCase("back") && !choice2.equalsIgnoreCase("center") && !choice2.equalsIgnoreCase("right") && !choice2.equalsIgnoreCase("deck"));
        }while(choice2.equalsIgnoreCase("back"));
        if (choice.equalsIgnoreCase("gold"))type=TYPE_HAND_CARD.GOLD;
        else type=TYPE_HAND_CARD.RESOURCE;
        if(choice2.equalsIgnoreCase("center"))slot= PlaceInPublicBoard.Slots.SLOT_A;
        else if(choice2.equalsIgnoreCase("deck"))slot= PlaceInPublicBoard.Slots.DECK;
        else slot= PlaceInPublicBoard.Slots.SLOT_B;

        try {
            cli.getMatchServer().drawCard(cli.getNickname(), type, slot);
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
