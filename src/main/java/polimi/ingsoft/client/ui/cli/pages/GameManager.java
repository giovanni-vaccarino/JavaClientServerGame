package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.*;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.*;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

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

    private final GameModel model = new GameModel();
    private final TemporaryModel temporaryModel = new TemporaryModel();

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
        out.println("Init model: " + state);
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

        if (gamePhase == GAME_PHASE.PLAY || gamePhase == GAME_PHASE.LAST_ROUND || turnStep == TURN_STEP.PLACE) {
            if (isYourTurn) {
                if (gamePhase == GAME_PHASE.LAST_ROUND) out.println(MESSAGES.LAST_ROUND);
                playTurn(turnStep);
            } else
                out.println("Wait for your turn!");
        } else if (turnStep == TURN_STEP.DRAW) {
            runDrawCard();
        } else if (gamePhase == GAME_PHASE.END) {
            out.println(MESSAGES.GAME_END);
            out.println("IL VINCITORE E' ANCORA DA CODIFICARE, GRAZIE PER AVER GIOCATO !");
            //TODO FINISH THIS PART
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
        BoardArgument argument=null;
        int card, x = 0, y = 0, side;
        boolean facingUp = true,alreadyprinted=false;
        MixedCard chosenCard;
            // Print board
            Board board = model.playerBoards.get(model.currentPlayerNickname);
            String choice, secondChoice;
            PlayerHand hand = model.hand;
            // Get card choice
            do {
                if(!alreadyprinted)printer.printFromBoard(board,hand,null,questCard);
                alreadyprinted=false;
//                ClientBoard.printBoard(board);
//               ClientHand.print(hand, questCard);
                out.println(MESSAGES.PLAY_CARD_HELP_1.getValue());
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("chat")) runChatIter();
                else if (choice.equalsIgnoreCase("board")) runBoardIter();
                else if (choice.equalsIgnoreCase("move")) {
                    runMoveIter(board);
                    alreadyprinted=true;
                }
                else if (choice.equalsIgnoreCase("flip")) {
                    do {
                        System.out.println(MESSAGES.GET_FLIP_CHOICE.getValue());
                        secondChoice = in.nextLine();
                        if (!(secondChoice.equals("1") || secondChoice.equals("2") || secondChoice.equals("3")))
                            out.println(MESSAGES.ERROR.getValue());
                    } while (!(secondChoice.equals("1") || secondChoice.equals("2") || secondChoice.equals("3")));
                    hand.flip(Integer.parseInt(secondChoice) - 1);
                    ClientHand.print(hand, questCard);
                } else if (!(choice.equals("1") || choice.equals("2") || choice.equals("3")))
                    out.print(MESSAGES.ERROR.getValue());
            } while (!(choice.equals("1") || choice.equals("2") || choice.equals("3")));
            card = Integer.parseInt((choice));
            // Get coords choice
            out.println(MESSAGES.PLAY_CARD_HELP_2.getValue() + " " + board.getPrintingCoordinates().getX() + "," + board.getPrintingCoordinates().getY());
            do {
                out.println(MESSAGES.PLAY_CARD_HELP_X.getValue());
                choice = in.nextLine();
                try {
                    x = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    out.println(MESSAGES.ERROR.getValue());
                    choice = "err";
                }
            } while (choice.equals("err"));
            do {
                out.println(MESSAGES.PLAY_CARD_HELP_Y.getValue());
                choice = in.nextLine();
                try {
                    y = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    out.println(MESSAGES.ERROR.getValue());
                    choice = "err";
                }
            } while (choice.equals("err"));
            chosenCard = hand.get(card - 1);
            // Get side choice
            do {
                out.println(MESSAGES.PLAY_CARD_HELP_ORIENTATION.getValue());
                choice = in.nextLine();
                if (!(choice.equals(("1")) || choice.equals("2"))) out.println(MESSAGES.ERROR.getValue());
            } while (!(choice.equals(("1")) || choice.equals("2")));
            side = Integer.parseInt(choice);
            if (side == 2) facingUp = false;
            try {
                cli.getMatchServer().placeCard(cli.getNickname(), chosenCard, new Coordinates(x, y), facingUp);
            } catch (IOException e) {
                parseError(ERROR_MESSAGES.UNABLE_TO_PLACE_CARD);
            }
        state = State.WAITING_FOR_PLACE;
    }

    private void runDrawCard() {
        // Print public board
        PlaceInPublicBoard<ResourceCard> resourceCards = model.resourceCards;
        PlaceInPublicBoard<GoldCard> goldCards = model.goldCards;
        PlaceInPublicBoard<QuestCard> questCards = model.questCards;
        PlaceInPublicBoard.Slots slot;
        TYPE_HAND_CARD type;
        String choice, choice2;
        PlayerHand hand = model.hand;
        // Get card choice
        do {
            choice = "getcard";
            do {
                printer.printFromPublicBoard(resourceCards, goldCards, questCards, hand, choice, model.personalQuestCard);
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("chat")) {
                    runChatIter();
                    choice = "getcard";
                }
                if (choice.equalsIgnoreCase("board")) {
                    runBoardIter();
                    choice = "getcard";
                }
                if (!choice.equalsIgnoreCase("resource") && !choice.equalsIgnoreCase("gold") && !choice.equalsIgnoreCase("chat") && !choice.equalsIgnoreCase("board")) {
                    choice = "getcard";
                    out.println(MESSAGES.ERROR.getValue());
                }
            } while (!choice.equalsIgnoreCase("resource") && !choice.equalsIgnoreCase("gold"));
            choice2 = choice;
            do {
                printer.printFromPublicBoard(resourceCards, goldCards, questCards, hand, choice2, model.personalQuestCard);
                choice2 = in.nextLine();
                if (!choice2.equalsIgnoreCase("back") && !choice2.equalsIgnoreCase("center") && !choice2.equalsIgnoreCase("right") && !choice2.equalsIgnoreCase("deck")) {
                    choice2 = "getcard";
                    out.println(MESSAGES.ERROR.getValue());
                }
            } while (!choice2.equalsIgnoreCase("back") && !choice2.equalsIgnoreCase("center") && !choice2.equalsIgnoreCase("right") && !choice2.equalsIgnoreCase("deck"));
        } while (choice2.equalsIgnoreCase("back"));
        if (choice.equalsIgnoreCase("gold")) type = TYPE_HAND_CARD.GOLD;
        else type = TYPE_HAND_CARD.RESOURCE;
        if (choice2.equalsIgnoreCase("center")) slot = PlaceInPublicBoard.Slots.SLOT_A;
        else if (choice2.equalsIgnoreCase("deck")) slot = PlaceInPublicBoard.Slots.DECK;
        else slot = PlaceInPublicBoard.Slots.SLOT_B;

        try {
            cli.getMatchServer().drawCard(cli.getNickname(), type, slot);
        } catch (IOException e) {
            parseError(ERROR_MESSAGES.UNABLE_TO_DRAW_CARD);
        }
        state = State.WAITING_FOR_DRAW;
    }

    private void runChatIter() {
        String choice, receiver = null;
        int type = -1;
        do {
            out.println(MESSAGES.HELP_GET_MESSAGE_TYPE.getValue());
            choice = in.nextLine();
            try {
                type = Integer.parseInt(choice);
            } catch (NumberFormatException exception) {
                choice = "err";
            }
        } while (choice.equalsIgnoreCase("err"));
        if (type == 2) {
            do {
                out.println(MESSAGES.HELP_GET_PLAYER_NAME.getValue());
                out.println("PLAYERS:");
                for(String player :model.nicknames){
                    if(!player.equalsIgnoreCase(cli.getNickname()))out.print(player+"\t");
                }
                out.print("\n");
                receiver = in.nextLine();
                if (!model.nicknames.contains(receiver)) out.println(MESSAGES.CHAT_RECEIVER_ERROR.getValue());
                if (receiver.equalsIgnoreCase(cli.getNickname()))
                    out.println(MESSAGES.CHAT_RECEIVER_IS_SENDER_ERROR.getValue());
            } while (!model.nicknames.contains(receiver) || receiver.equalsIgnoreCase(cli.getNickname()));
        }
        out.println(MESSAGES.HELP_GET_MESSAGE.getValue());
        choice = in.nextLine();
        Message message = new Message(cli.getNickname(), choice);
        try {
            if (receiver == null) cli.updateBroadcastChat(message);
            else cli.updatePrivateChat(receiver, message);
        } catch (Exception/*IOException*/ e) {
            out.println(MESSAGES.ERROR.getValue());
        }
    }

    public void runBoardIter() {
        BoardArgument argument=null;
        String observed,input;
        out.println(MESSAGES.HELP_OTHER_PLAYERS_BOARD.getValue());
        do {
            out.println(MESSAGES.HELP_GET_PLAYER_NAME.getValue());
            out.println("PLAYERS:");
            for(String player :model.nicknames){
                if(!player.equalsIgnoreCase(cli.getNickname()))out.print(player+"\t");
            }
            out.print("\n");
            observed = in.nextLine();
            if (!model.nicknames.contains(observed)) out.println(MESSAGES.BOARD_OBSERVED_ERROR.getValue());
            if (observed.equalsIgnoreCase(cli.getNickname()))
                out.println(MESSAGES.BOARD_OBSERVED_IS_OBSERVED_ERROR.getValue());
        } while (!model.nicknames.contains(observed) || observed.equalsIgnoreCase(cli.getNickname()));
        Board temp=model.playerBoards.get(observed);
        do{
            ClientBoard.printBoard(temp,argument);
            do {
                out.println(MESSAGES.HELP_BOARD_NAVIGATE.getValue());
                input = in.nextLine();
                if (checkInputForBoardNavigation(input)) out.println(MESSAGES.ERROR.getValue());
            }while(checkInputForBoardNavigation(input));
            if(!input.equalsIgnoreCase("back")){
                argument = getBoardArgument(input);
            }
        }while(!input.equalsIgnoreCase("back"));
    }
    public boolean checkInputForBoardNavigation(String input){
        return (!input.equalsIgnoreCase(BoardArgument.UP.getValue()) && !input.equalsIgnoreCase(BoardArgument.DOWN.getValue()) &&
                !input.equalsIgnoreCase(BoardArgument.LEFT.getValue()) && !input.equalsIgnoreCase(BoardArgument.RIGHT.getValue()) &&
                        !input.equalsIgnoreCase(BoardArgument.UPRIGHT.getValue()) && !input.equalsIgnoreCase(BoardArgument.UPLEFT.getValue()) &&
                        !input.equalsIgnoreCase(BoardArgument.DOWNLEFT.getValue()) && !input.equalsIgnoreCase(BoardArgument.DOWNRIGHT.getValue()) &&
                        !input.equalsIgnoreCase("back"));
    }
    public void runMoveIter(Board board){
        BoardArgument argument=null;
        String input;
        do {
            out.println(MESSAGES.HELP_BOARD_NAVIGATE.getValue());
            input = in.nextLine();
            if (checkInputForBoardNavigation(input)) out.println(MESSAGES.ERROR.getValue());
        }while(checkInputForBoardNavigation(input));
        if(!input.equalsIgnoreCase("back")){
            argument = getBoardArgument(input);
        }
        printer.printFromBoard(board,model.hand,argument,model.personalQuestCard);

    }

    private BoardArgument getBoardArgument(String input) {
        BoardArgument argument;
        if(input.equalsIgnoreCase(BoardArgument.UP.getValue()))argument=BoardArgument.UP;
        else if(input.equalsIgnoreCase(BoardArgument.DOWN.getValue()))argument=BoardArgument.DOWN;
        else if(input.equalsIgnoreCase(BoardArgument.LEFT.getValue()))argument=BoardArgument.LEFT;
        else if(input.equalsIgnoreCase(BoardArgument.RIGHT.getValue()))argument=BoardArgument.RIGHT;
        else if(input.equalsIgnoreCase(BoardArgument.UPLEFT.getValue()))argument=BoardArgument.UPLEFT;
        else if(input.equalsIgnoreCase(BoardArgument.UPRIGHT.getValue()))argument=BoardArgument.UPRIGHT;
        else if(input.equalsIgnoreCase(BoardArgument.DOWNLEFT.getValue()))argument=BoardArgument.DOWNRIGHT;
        else argument=BoardArgument.DOWNLEFT;
        return argument;
    }

    private void showPlaceCard() {
        out.println(model.currentPlayerNickname + MESSAGES.OTHER_PLAYER_PERFORMING_PLACE.getValue());
    }

    private void showDrawCard() {
        out.println(model.currentPlayerNickname + MESSAGES.OTHER_PLAYER_PERFORMING_DRAW.getValue());
    }

    public void updatePlayerHand(PlayerHand hand) {
        model.hand = hand;
    }

    public void updatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) {
        switch (deckType) {
            case RESOURCE -> model.resourceCards = (PlaceInPublicBoard<ResourceCard>) placeInPublicBoard;
            case GOLD -> model.goldCards = (PlaceInPublicBoard<GoldCard>) placeInPublicBoard;
        }
    }

    public void updateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        Board board = model.playerBoards.get(nickname);
        board.add(coordinates, playedCard.getCard(), playedCard.isFacingUp());
    }

    @Override
    public void parseError(ERROR_MESSAGES error) {
        // TODO Maybe remove this one
        out.println("UNEXPECTED ERROR: " + error.getValue());
    }
}
