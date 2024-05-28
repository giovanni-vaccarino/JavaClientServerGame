package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.cli.Printer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.PlayerColor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class MatchInitializationManager {
    private final Scanner in;
    private final PrintStream out;
    private final CLI cli;
    private INITIAL_STEP currentStep = INITIAL_STEP.COLOR;
    private PlayerInitialSetting playerInitialSetting;
    private final Printer printer;

    public MatchInitializationManager(Scanner in, PrintStream out, CLI cli) {
        this.in = in;
        this.out = out;
        this.cli = cli;
        this.printer = new Printer(out);
    }

    public boolean isValidColor(int colorId) {
        return colorId < PlayerColor.values().length  && colorId >= 0;
    }

    public void selectColor() {
        int i = 1;
        for (PlayerColor color : PlayerColor.values())
            out.println((i++) + ". "  + color.toString());

        int colorId;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_COLOR.getValue());
            colorId = in.nextInt();
            in.nextLine();

            if (isValidColor(colorId - 1)){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.COLOR_ID_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);
        cli.selectColor(PlayerColor.values()[colorId - 1]);
    }

    public void selectInitialCardFace() {
        printer.printInitialCardChoice(playerInitialSetting.getInitialCard());

        boolean isValid = false;
        String face;

        do {
            out.print(MESSAGES.CHOOSE_INITIAL_CARD_FACE.getValue());
            face = in.nextLine();

            if (Objects.equals(face, "F") || Objects.equals(face, "B")){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.INVALID_FACE.getValue());
            }
        } while (!isValid);
        cli.selectInitialCardFace(face.equals("F"));
    }

    public void selectQuestCard() {
        printer.printQuestCardChoice(playerInitialSetting.getFirstChoosableQuestCard(), playerInitialSetting.getSecondChoosableQuestCard());

        int questCard;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_COLOR.getValue());
            questCard = in.nextInt();
            in.nextLine();

            if (questCard == 1 || questCard == 2){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.INVALID_QUEST_CARD.getValue());
            }
        } while (!isValid);
        cli.selectQuestCard(questCard == 1 ?
                playerInitialSetting.getFirstChoosableQuestCard() :
                playerInitialSetting.getSecondChoosableQuestCard());
    }

    public void setPlayerInitialSetting(PlayerInitialSetting playerInitialSetting) {
        this.playerInitialSetting = playerInitialSetting;
    }
}
