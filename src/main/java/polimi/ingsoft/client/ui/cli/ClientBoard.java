package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;
//EACH CARD IS 34 CHARACTERS WIDE AND 9 TALL
import java.util.HashMap;

import static polimi.ingsoft.server.enumerations.Object.*;

public class ClientBoard {
    HashMap<Coordinates, PlayedCard> board;
    private Coordinates actualCoordinates;
    private final static String UPLEFTARROW = "↖";
    private final static String UPRIGHTARROW = "↗";
    private final static String DOWNLEFTARROW = "↙";
    private final static String DOWNRIGHTARROW = "↘";

    private final static String UPARROW = "↑";
    private final static String RIGHTARROW = "→";
    private final static String LEFTARROW = "←";
    private final static String DOWNARROW = "↓";

    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW = "\u001B[43m";
    public static final String BLACKTEXT = "\u001B[30m";
    public static final String RESET = "\u001B[0m";

    public ClientBoard(PlayedCard initial) {
        this.board = new HashMap<>();
        this.actualCoordinates = new Coordinates(0, 0);
        board.put(new Coordinates(0, 0), initial);
    }

    public void put(Coordinates coordinates, PlayedCard playedCard) {
        board.put(coordinates, playedCard);
    }

    public void printBoard() {
        int count = 0;
        String color = "";
        System.out.print(RESET);
        if ((getCardAtRespective(-2, -2) != null ||
                getCardAtRespective(0, 2) != null ||
                getCardAtRespective(-2, 0) != null)) System.out.print(UPLEFTARROW);
        else System.out.print(" ");
        if (getCardAtRespective(-1, -1) != null &&
                getCardAtRespective(-2, -2) != null) System.out.print("        |");
        else System.out.print("         ");
        for (int i = 0; i < 14; i++) System.out.print(" ");
        if (getCardAtRespective(0, 2) != null) System.out.print("|");
        else System.out.print(" ");
        System.out.print("                ");
        if (getCardAtRespective(0, 2) != null ||
                getCardAtRespective(-1, 3) != null ||
                getCardAtRespective(1, 3) != null) System.out.print(UPARROW);
        else System.out.print(" ");
        for (int i = 0; i < 15; i++) System.out.print(" ");
        if (getCardAtRespective(0, 2) != null) System.out.print("|");
        else System.out.print(" ");
        for (int i = 0; i < 14; i++) System.out.print(" ");

        if (getCardAtRespective(1, 1) != null
                && getCardAtRespective(2, 2) != null) System.out.print("|        ");
        else System.out.print("         ");
        if (getCardAtRespective(2, 2) != null ||
                getCardAtRespective(0, 2) != null ||
                getCardAtRespective(2, 0) != null) System.out.print(UPRIGHTARROW);
        else System.out.print(" ");
        System.out.print("\n");


        do {
            PlayedCard card = getCardAtRespective(-1, -1);
            if (card != null) {
                color = defineColor(card);
                System.out.print(printUpLeftCorner(card, count));
                System.out.print(printFirstCenterRow(card, color, actualCoordinates.sum(new Coordinates(-1, -1))));
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 34; i++) System.out.print(" ");
            }
            System.out.print(RESET);
            for (int i = 0; i < 14; i++) System.out.print(" ");

//            card = getCardAtRespective(1, 1);
//            if (card != null) {
//                color = defineColor(card);
////                if (count != 2 || getCardAtRespective(2, 2) == null)
////                    printUpperResource(1, card.getUpRightCorner(), color, count + 4);
////                else printUpperResource(0, card.getUpLeftCorner(), color, count + 5);
//                System.out.print(printFirstCenterRow(card, color, actualCoordinates.sum(new Coordinates(1, 1))));
//            } else {
//                System.out.print(RESET);
//                for (int i = 0; i < 34; i++) System.out.print(" ");
//            }
            count++;
            System.out.print("\n");
        } while (count < 3);

    }

    public PlayedCard getCardAtRespective(int x, int y) {
        return board.get(actualCoordinates.sum(new Coordinates(x, y)));
    }

    public String printUpLeftCorner(PlayedCard card, int count) {
        String pre="",color;
        if (getCardAtRespective(-2, -2) == null ||
                getCardAtRespective(-2, -2).getOrder() < card.getOrder()) {//caso in cui l'angolo in alto a sx non sia coperto
                switch(count){
                    case 0,1:
                        pre=" ";
                        break;
                    case 2:
                        if(getCardAtRespective(-2, -2) == null)pre=" ";
                        else pre="_";
                        break;
                }
                if(card.getUpLeftCorner()==null)System.out.print(defineColor(card)+"          ");
                else if(card.getUpLeftCorner().isEmpty())System.out.print(YELLOW+"          ");
                else {
                    color=switch(card.getUpLeftCorner().getItems().getFirst()){
                        case Resource.LEAF ->GREEN;
                        case Resource.WOLF -> BLUE;
                        case Resource.BUTTERFLY -> PURPLE;
                        case Resource.MUSHROOM -> RED;
                        default->YELLOW;
                    };
                    pre=pre+color;
                    switch (count) {
                        case 0:
                            return pre+BLACKTEXT+"¡‾‾‾‾‾‾‾‾¡";
                        case 1:
                            switch(card.getUpLeftCorner().getItems().getFirst()){
                                    case FEATHER -> pre=pre+BLACKTEXT+"|FEATHER |";
                                    case SCROLL -> pre=pre+BLACKTEXT+"| SCROLL |";
                                    case POTION -> pre=pre+BLACKTEXT+"| POTION |";
                                    default -> pre=pre+BLACKTEXT+"|        |";
                                }
                                return pre;
                        case 2:
                            return pre+BLACKTEXT+"!________!";
                    }

                }
        }else return "";
        return "";
    }


//    public void printUpperResource(int place, CornerSpace corner, String actualColor, int pos) {
//        String color = "";
//        System.out.print(RESET + "          " + color + "          ");
//        System.out.print(RESET + "__________" + color + "          ");
//        System.out.print(color + "          " + RESET + "          ");
//        System.out.print(color + "          " + RESET + "__________");
//        System.out.print(color + "          ");
//        System.out.print(RESET + "          " + color + "¡‾‾‾‾‾‾‾‾¡");
//        System.out.print(RESET + "          " + color + printObject(corner.getItems().getFirst()));
//        System.out.print(RESET + "          " + color + "!________!");
//        System.out.print(RESET + "__________" + color + "!________!");
//        System.out.print(color + "¡‾‾‾‾‾‾‾‾¡" + RESET + "          ");
//        System.out.print(color + printObject(corner.getItems().getFirst()) + RESET + "          ");
//        System.out.print(color + "!________!" + RESET + "          ");
//        System.out.print(color + "!________!" + RESET + "__________");
//    }

    public String printFirstCenterRow(PlayedCard card, String actualColor, Coordinates actualCoordinates) {
        if (card.isFacingUp() || card.getScore() == 0 || actualCoordinates.equals(new Coordinates(0, 0))) {
            return actualColor + "              ";
        } else {
            try {
                MixedCard newCard = (MixedCard) card.getCard();
                try {
                    HashMap<Item, Integer> cost = ((ItemPattern) newCard.getPointPattern()).getCost();
                    if (cost.get(FEATHER) != 0)
                        return actualColor + BLACKTEXT + " |" + card.getScore() + "x Feather| ";
                    else if (cost.get(POTION) != 0)
                        return actualColor + BLACKTEXT + " |" + card.getScore() + "x Potion|  ";
                    else if (cost.get(Object.SCROLL) != 0)
                        return actualColor + BLACKTEXT + " |" + card.getScore() + "x Scroll|  ";
                } catch (ClassCastException e) {
                    return actualColor + BLACKTEXT + " |" + card.getScore() + "x Corner|  ";
                }
            } catch (ClassCastException e) {
                return actualColor + "              ";
            }
        }
        return actualColor + "              ";
    }

    public String defineColor(PlayedCard card) {
        return switch (card.getColor()) {
            case Resource.BUTTERFLY -> PURPLE;
            case Resource.LEAF -> GREEN;
            case Resource.MUSHROOM -> RED;
            case Resource.WOLF -> BLUE;

        };
    }

    public String printObject(Item object) {
        return switch (object) {
            case SCROLL -> BLACKTEXT + "| SCROLL |";
            case POTION -> BLACKTEXT + "| POTION |";
            case FEATHER -> BLACKTEXT + "|FEATHER |";
            default -> "|        |";
        };
    }

}
