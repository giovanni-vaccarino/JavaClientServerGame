package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.cli.Printer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.cards.QuestCard;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MatchInitializationManager implements CLIPhaseManager {
    private static class MatchInitializationModel {
        PlayerColor color;
        InitialCard initialCard;
        boolean isInitialCardFaceUp;
        QuestCard selectedQuestCard;
        QuestCard firstQuestCard;
        QuestCard secondQuestCard;
        PlayerHand hand;
        GAME_PHASE gamePhase;
        INITIAL_STEP initialStep;

        public MatchInitializationModel() {
            gamePhase = GAME_PHASE.INITIALIZATION;
            initialStep = INITIAL_STEP.COLOR;
        }
    }

    private final MatchInitializationModel model = new MatchInitializationModel();

    enum State {
        NONE,
        SELECTING_COLOR,
        WAITING_FOR_COLOR,
        WAITING_FOR_OTHERS_TO_SELECT_COLOR,
        SELECTING_INITIAL_CARD_FACE,
        WAITING_FOR_INITIAL_CARD_FACE,
        WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE,
        SELECTING_QUEST_CARD,
        WAITING_FOR_QUEST_CARD,
        WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD,
    }

    private State state = State.NONE;
    private transient final Scanner in;
    private final PrintStream out;
    private transient final CLI cli;
    private transient final Printer printer;

    public MatchInitializationManager(Scanner in, PrintStream out, CLI cli) {
        this.in = in;
        this.out = out;
        this.cli = cli;
        this.printer = new Printer(out);
    }

    @Override
    public void start() {
        state = State.SELECTING_COLOR;
        selectColor();
    }

    public void updateGameState(GameState gameState) {
        GAME_PHASE gamePhase = gameState.getGamePhase();
        INITIAL_STEP initialStep = gameState.getCurrentInitialStep();

        if (gamePhase == GAME_PHASE.INITIALIZATION) {
            if (state == State.WAITING_FOR_OTHERS_TO_SELECT_COLOR && initialStep == INITIAL_STEP.FACE_INITIAL) {
                state = State.SELECTING_INITIAL_CARD_FACE;
                selectInitialCardFace();
            } else if (state == State.WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE && initialStep == INITIAL_STEP.QUEST_CARD) {
                state = State.SELECTING_QUEST_CARD;
                selectQuestCard();
            }
        } else if (gamePhase == GAME_PHASE.PLAY && state == State.WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD) {
            state = State.NONE;
            cli.returnMatchInitializationManager(
                model.hand,
                model.selectedQuestCard,
                model.initialCard,
                model.isInitialCardFaceUp,
                gameState
            );
        }
    }

    public void updatePlayerInitialSettings(PlayerInitialSetting playerInitialSetting) {
        setModel(playerInitialSetting);

        if (state == State.WAITING_FOR_COLOR
                && playerInitialSetting.getColor() != null
        ) {
            setColor(playerInitialSetting.getColor());
        } else if (state == State.WAITING_FOR_INITIAL_CARD_FACE
                && playerInitialSetting.getIsInitialFaceUp() != null
        ) {
            setInitialFaceUp(playerInitialSetting.getIsInitialFaceUp());
        } else if (state == State.WAITING_FOR_QUEST_CARD
                && playerInitialSetting.getQuestCard() != null
        ) {
            setQuestCard(playerInitialSetting.getQuestCard());
        }
    }

    private void setModel(PlayerInitialSetting playerInitialSetting) {
        model.initialCard = playerInitialSetting.getInitialCard();
        model.firstQuestCard = playerInitialSetting.getFirstChoosableQuestCard();
        model.secondQuestCard = playerInitialSetting.getSecondChoosableQuestCard();
        model.selectedQuestCard = playerInitialSetting.getQuestCard();
        model.hand = playerInitialSetting.getPlayerHand();
    }

    private void setColor(PlayerColor color) {
        model.color = color;
        out.println(MESSAGES.COLOR_SUCCESSFULLY_SET.getValue());
        state = State.WAITING_FOR_OTHERS_TO_SELECT_COLOR;
    }

    private void setInitialFaceUp(boolean initialFaceUp) {
        model.isInitialCardFaceUp = initialFaceUp;
        out.println(MESSAGES.INITIAL_CARD_FACE_SUCCESSFULLY_SET.getValue());
        state = State.WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE;
    }

    private void setQuestCard(QuestCard questCard) {
        model.selectedQuestCard = questCard;
        out.println(MESSAGES.QUEST_CARD_SUCCESSFULLY_SET.getValue());
        state = State.WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD;
    }

    private boolean isValidColor(int colorId) {
        return colorId < PlayerColor.values().length  && colorId >= 0;
    }

    private  void selectColor() {
        int i = 1;
        for (PlayerColor color : PlayerColor.values())
            out.println((i++) + ". "  + color.toString());

        int colorId = 0;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_COLOR.getValue());
            try {
                colorId = in.nextInt();
                in.nextLine();

                if (isValidColor(colorId - 1)) {
                    isValid = true;
                } else {
                    out.println(ERROR_MESSAGES.COLOR_ID_OUT_OF_BOUND.getValue());
                }
            } catch (InputMismatchException e) {
                out.println(ERROR_MESSAGES.BAD_INPUT.getValue());
            }
        } while (!isValid);

        cli.setColor(PlayerColor.values()[colorId - 1]);
        state = State.WAITING_FOR_COLOR;
    }

    private void selectInitialCardFace() {
        printer.printInitialCardChoice(model.initialCard);

        boolean isValid = false;
        String face;

        do {
            out.print(MESSAGES.CHOOSE_INITIAL_CARD_FACE.getValue());
            face = in.nextLine();

            if (face.equalsIgnoreCase("f") || face.equalsIgnoreCase("b")) {
                isValid = true;
            } else {
                out.println(ERROR_MESSAGES.INVALID_FACE.getValue());
            }
        } while (!isValid);
        cli.setIsFaceInitialCardUp(face.equalsIgnoreCase("f"));
        state = State.WAITING_FOR_INITIAL_CARD_FACE;
    }

    private void selectQuestCard() {
        printer.printQuestCardChoice(model.firstQuestCard, model.secondQuestCard);

        int questCard = -1;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_QUEST_CARD.getValue());
            try {
                questCard = in.nextInt();
                in.nextLine();
            }catch(InputMismatchException ignored){
            }

            if (questCard == 1 || questCard == 2){
                isValid = true;
            } else {
                out.println(ERROR_MESSAGES.INVALID_QUEST_CARD.getValue());
            }
        } while (!isValid);
        cli.setQuestCard(questCard == 1 ? model.firstQuestCard : model.secondQuestCard);
        state = State.WAITING_FOR_QUEST_CARD;
    }

    @Override
    public void parseError(ERROR_MESSAGES error) {
        if (state == State.WAITING_FOR_COLOR && error == ERROR_MESSAGES.UNABLE_TO_SET_COLOR || error == ERROR_MESSAGES.COLOR_ALREADY_PICKED) {
            out.println("ERROR: " + error.getValue());
            state = State.SELECTING_COLOR;
            selectColor();
        } else if (state == State.WAITING_FOR_INITIAL_CARD_FACE && error == ERROR_MESSAGES.UNABLE_TO_SET_FACE) {
            out.println("ERROR: " + error.getValue());
            state = State.SELECTING_INITIAL_CARD_FACE;
            selectInitialCardFace();
        } else if (state == State.WAITING_FOR_QUEST_CARD && error == ERROR_MESSAGES.UNABLE_TO_SET_QUEST_CARD) {
            out.println("ERROR: " + error.getValue());
            state = State.SELECTING_QUEST_CARD;
            selectQuestCard();
        } else {
            // TODO Maybe remove this one
            out.println("UNEXPECTED ERROR: " + error.getValue());
        }
    }
}
