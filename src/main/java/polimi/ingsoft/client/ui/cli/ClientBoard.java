package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;
//EACH CARD IS 34 CHARACTERS WIDE AND 9 TALL
import javax.swing.*;
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
    public static final String WHITETEXT = "\u001B[37m";

    public ClientBoard(PlayedCard initial) {
        this.board = new HashMap<>();
        this.actualCoordinates = new Coordinates(0, 0);
        board.put(new Coordinates(0, 0), initial);
    }

    public void put(Coordinates coordinates, PlayedCard playedCard) {
        board.put(coordinates, playedCard);
    }

    public void printBoard() {
        PlayedCard card;
        int count = 0, row = 0;
        String color = "";
        System.out.print(RESET);
        if (getCardAtRespective(-2, 2) != null ||
                getCardAtRespective(-3, 3) != null ||
                getCardAtRespective(-1, 3) != null ||
                getCardAtRespective(-3, 1) != null) System.out.print(UPLEFTARROW);
        else System.out.print(" ");
        if (getCardAtRespective(-2, 2) != null) System.out.print("         |");
        else System.out.print("         ");
        for (int i = 0; i < 14; i++) System.out.print(" ");
        if (getCardAtRespective(0, 2) != null) System.out.print("|                ");
        else System.out.print("                 ");
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
                getCardAtRespective(3, 3) != null ||
                getCardAtRespective(1, 3) != null ||
                getCardAtRespective(3, 1) != null) System.out.print(UPRIGHTARROW);
        else System.out.print(" ");
        System.out.print(RESET + "\n");


        do {
            card = getCardAtRespective(-1, 1);
            if (card != null || getCardAtRespective(-2, 2) != null || getCardAtRespective(0, 2) != null) {
                if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
                else color = defineColor(card);
                System.out.print(printLeftUpLeftCorner(card, count));
                System.out.print(printCenter(row, card, color));
                System.out.print(printLeftUpRightCorner(card, count));
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 35; i++) System.out.print(" ");
            }
            System.out.print(RESET);
            for (int i = 0; i < 14; i++) System.out.print(" ");

            card = getCardAtRespective(1, 1);
            if (card != null || getCardAtRespective(2, 2) != null || getCardAtRespective(0, 2) != null) {
                if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
                else color = defineColor(card);
                System.out.print(printRightUpLeftCorner(card, count));
                System.out.print(printCenter(row, card, color));
                System.out.print(printRightUpRightCorner(card, count));
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 33; i++) System.out.print(" ");
            }
            if (count == 2 && getCardAtRespective(2, 2) != null) System.out.print(RESET + "_");
            else System.out.print(RESET + " ");
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);


        count = 0;
        do {
            card = getCardAtRespective(-1, 1);
            if (card != null || getCardAtRespective(-2, 2) != null || getCardAtRespective(0, 2) != null) {
                printCentralRows(card, row);
                System.out.print(RESET);
                for (int i = 0; i < 13; i++) System.out.print(" ");
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 48; i++) System.out.print(" ");
            }

            card = getCardAtRespective(1, 1);
            if (card != null || getCardAtRespective(2, 2) != null || getCardAtRespective(0, 2) != null) {
                printCentralRows(card, row);
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 34; i++) System.out.print(" ");
            }
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);


        count = 0;
        do {
            card = getCardAtRespective(-1, 1);
            color = defineColor(card);
            if (getCardAtRespective(-2, 0) != null && count == 0) System.out.print(RESET + "‾");
            else System.out.print(" ");
            System.out.print(printLeftBottomLeftCorner(card, count));
            System.out.print(printCenter(row, card, color));
            System.out.print(printLeftBottomRightCorner(card, count));
            card = getCardAtRespective(0, 0);
            color = defineColor(card);
            System.out.print(printCenter(row - 6, card, color));
            card = getCardAtRespective(1, 1);
            color = defineColor(card);
            System.out.print(printRightBottomLeftCorner(card, count));
            System.out.print(printCenter(row, card, color));
            System.out.print(printRightBottomRightCorner(card, count));
            if (count == 0 && getCardAtRespective(2, 0) != null) System.out.print(RESET + "‾");
            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
        row = 3;
        card = getCardAtRespective(0, 0);
        do {
            if (row == 4 && (getCardAtRespective(-2, 0) != null || getCardAtRespective(-3, 1) != null || getCardAtRespective(-3, -1) != null))
                System.out.print(LEFTARROW);
            else System.out.print(" ");
            System.out.print(RESET + "                       ");
            printCentralRows(card, row);
            if (row == 4 && getCardAtRespective(2, 0) != null || getCardAtRespective(3, 1) != null || getCardAtRespective(3, -1) != null)
                System.out.print(RESET + "                        " + RIGHTARROW);
            System.out.print(RESET + "\n");
            row++;
        } while (row < 6);
        count = 0;
        do {
            card = getCardAtRespective(-1, -1);
            if (count == 2 && getCardAtRespective(-2, 0) != null) System.out.print("_");
            else System.out.print(" ");
            System.out.print(printBottomLeftUpLeftCorner(card, count));
            System.out.print(printCenter(row - 6, card, defineColor(card)));
            System.out.print(printBottomLeftUpRightCorner(card, count));
            card = getCardAtRespective(0, 0);
            System.out.print(printCenter(row, card, defineColor(card)));
            card = getCardAtRespective(1, -1);

            System.out.print(printBottomRightUpLeftCorner(card, count));
            System.out.print(printCenter(row - 6, card, defineColor(card)));
            System.out.print(printBottomRightUpRightCorner(card, count));
            if (count == 2 && getCardAtRespective(2, 0) != null) System.out.print(RESET + "_");

            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
        row = row - 6;
        count = 0;
        do {
            card = getCardAtRespective(-1, -1);
            printCentralRows(card, row);
            System.out.print(RESET + "             ");
            card = getCardAtRespective(1, -1);
            printCentralRows(card, row);
            row++;
            count++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        count = 0;
        do {
            card = getCardAtRespective(-1, -1);
            color=defineColor(card);
            if(count==0&&getCardAtRespective(-2,-2)!=null)System.out.print("‾");
            else System.out.print(" ");
            System.out.print(printBottomLeftBottomLeftCorner(card, count));
            printCenter(row,card,color);
            System.out.print(row);
            System.out.print(printBottomLeftBottomRightCorner(card, count));
            System.out.print(RESET+"             ");
            card = getCardAtRespective(1, -1);
            color=defineColor(card);
            System.out.print(printBottomRightBottomLeftCorner(card, count));
            printCenter(row,card,color);
            System.out.print(printBottomRightBottomRightCorner(card, count));
            if(count==0&&getCardAtRespective(2,-2)!=null)System.out.print(RESET+"‾");
            else System.out.print(RESET+" ");
            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
    }

    private String printBottomLeftBottomLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(-2, -2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(-2, -2) != null && card != null &&
                getCardAtRespective(-2, -2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(-2, -2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(-2, -2).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(-2, -2)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomLeftBottomRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, -2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, -2) != null && card != null &&
                getCardAtRespective(0, -2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, -2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, -2).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, -2)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomRightBottomRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(2, -2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(2, -2) != null && card != null &&
                getCardAtRespective(2, -2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(2, -2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(2, -2).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(2, -2)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomRightBottomLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, -2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, -2) != null && card != null &&
                getCardAtRespective(0, -2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, -2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, -2).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, -2)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private void printCentralRows(PlayedCard card, int row) {
        String color;
        if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
        else color = defineColor(card);
        System.out.print(" " + color + "          ");
        System.out.print(printCenter(row, card, color));
        System.out.print(color + "          ");
    }

    private PlayedCard getCardAtRespective(int x, int y) {
        return board.get(actualCoordinates.sum(new Coordinates(x, y)));
    }

    private String printBottomRightUpRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(2, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(2, 0) != null && card != null &&
                getCardAtRespective(2, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(2, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(2, 0).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(2, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomRightUpLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 0) != null && card != null &&
                getCardAtRespective(0, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 0).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomLeftUpRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 0) != null && card != null &&
                getCardAtRespective(0, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 0).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printBottomLeftUpLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(-2, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(-2, 0) != null && card != null &&
                getCardAtRespective(-2, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(-2, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(-2, 0).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(-2, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printRightBottomLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 0) != null && card != null &&
                getCardAtRespective(0, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 0).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printRightBottomRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(2, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(2, 0) != null && card != null &&
                getCardAtRespective(2, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(2, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(2, 0).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(2, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printLeftBottomRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 0) != null && card != null &&
                getCardAtRespective(0, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 0).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printLeftBottomLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(-2, 0) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(-2, 0) != null && card != null &&
                getCardAtRespective(-2, 0).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(-2, 0) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(-2, 0).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(-2, 0)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printRightUpLeftCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 2) != null && card != null &&
                getCardAtRespective(0, 2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 2).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 2)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private String printRightUpRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(2, 2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(2, 2) != null && card != null &&
                getCardAtRespective(2, 2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(2, 2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(2, 2).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(2, 2)) + "          ";
        }
        return printCorner("", corner, count);
    }


    private String printLeftUpRightCorner(PlayedCard card, int count) {
        CornerSpace corner;
        if (getCardAtRespective(0, 2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(0, 2) != null && card != null &&
                getCardAtRespective(0, 2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(0, 2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(0, 2).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(0, 2)) + "          ";
        }
        return printCorner("", corner, count);
    }


    private String printLeftUpLeftCorner(PlayedCard card, int count) {
        String pre = "";
        CornerSpace corner;
        switch (count) {
            case 0, 1:
                pre = " ";
                break;
            case 2:
                if (getCardAtRespective(-2, 2) == null) pre = " ";
                else pre = "_";
                break;
        }
        //carta 22si carta 11si   //carta 22si carta11 no
        if (getCardAtRespective(-2, 2) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(-2, 2) != null && card != null &&
                getCardAtRespective(-2, 2).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return pre + defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(-2, 2) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(-2, 2).getBottomRightCorner();
            if (corner == null) return pre + defineColor(getCardAtRespective(-2, 2)) + "          ";
        }
        return printCorner(pre, corner, count);
    }

    private String printCorner(String pre, CornerSpace corner, int count) {
        String color = "";
        if (corner.isEmpty()) return pre + YELLOW + "          ";
        else {
            color = switch (corner.getItems().getFirst()) {
                case Resource.LEAF -> GREEN;
                case Resource.WOLF -> BLUE;
                case Resource.BUTTERFLY -> PURPLE;
                case Resource.MUSHROOM -> RED;
                default -> YELLOW;
            };
            pre = pre + color;
            switch (count) {
                case 0:
                    return pre + BLACKTEXT + "¡‾‾‾‾‾‾‾‾¡";
                case 1:
                    return pre + printObject(corner.getItems().getFirst());
                case 2:
                    return pre + BLACKTEXT + "!________!";
            }

        }
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

    private String printCenter(int row, PlayedCard card, String actualColor) {
        String pre = "";
        Resource resource = null;
        if (card == null) return RESET + "              ";
        else if (row == 0) {
            if (card.getScore() == 0 || card.isFacingUp()) return actualColor + "              ";
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
                } catch (NullPointerException f) {
                    return actualColor + BLACKTEXT + "     | " + card.getScore() + "|     ";
                }
            } catch (ClassCastException e) {
                return actualColor + "              ";
            }
        } else if (row != 8) {
            if (!card.isFacingUp()) return actualColor + "              ";
            switch (card.getCenter().getItems().size()) {
                case 1:
                    return switch (row) {
                        case 3 -> actualColor + BLACKTEXT + "  ¡‾‾‾‾‾‾‾‾¡  ";
                        case 4 -> actualColor + BLACKTEXT + "  |        |  ";
                        case 5 -> actualColor + BLACKTEXT + "  !________!  ";
                        default -> actualColor + "              ";
                    };
                case 2:
                    switch (row) {
                        case 1, 4 -> pre = BLACKTEXT + "¡‾‾‾‾‾‾‾‾¡" + actualColor + "  ";
                        case 2, 5 -> pre = BLACKTEXT + "|        |" + actualColor + "  ";
                        case 3, 6 -> pre = BLACKTEXT + "!________!" + actualColor + "  ";
                        default-> pre=actualColor + "              ";
                    }
                    if (row < 4) resource = card.getCenter().getItems().getFirst();
                    else resource = card.getCenter().getItems().get(1);
                    break;
                case 3:
                    switch (row) {
                        case 1, 3, 5 -> pre = BLACKTEXT + "¡‾‾‾‾‾‾‾‾¡" + actualColor + "  ";
                        case 2, 4 -> pre = BLACKTEXT + "|        |" + actualColor + "  ";
                        case 6 -> pre = BLACKTEXT + "!________!" + actualColor + "  ";
                        case 7 -> pre = actualColor + "            ";
                    }
                    if (row < 3) resource = card.getCenter().getItems().getFirst();
                    else if (row < 5) resource = card.getCenter().getItems().get(1);
                    else resource = card.getCenter().getItems().get(2);
                    break;
            }
            return switch (resource) {
                case Resource.LEAF -> actualColor + "  " + GREEN + pre;
                case Resource.BUTTERFLY -> actualColor + "  " + PURPLE + pre;
                case Resource.MUSHROOM -> actualColor + "  " + RED + pre;
                case Resource.WOLF -> actualColor + "  " + BLUE + pre;
                case null -> actualColor + "              ";
            };
        } else return printLastRow(card, actualColor);
        return RESET + "              ";
    }

    private String defineColor(PlayedCard card) {
        if (card != null && card.getCenter().getItems().size() != 1) return YELLOW;
        return card == null ? "" : switch (card.getColor()) {
            case Resource.BUTTERFLY -> PURPLE;
            case Resource.LEAF -> GREEN;
            case Resource.MUSHROOM -> RED;
            case Resource.WOLF -> BLUE;
        };
    }

    private String printObject(Item object) {
        return switch (object) {
            case SCROLL -> BLACKTEXT + "| SCROLL |";
            case POTION -> BLACKTEXT + "| POTION |";
            case FEATHER -> BLACKTEXT + "|FEATHER |";
            default -> BLACKTEXT + "|        |";
        };
    }


    private String printLastRow(PlayedCard card, String actualColor) {
        MixedCard newCard;
        try {
            newCard = (MixedCard) card.getCard();
        } catch (ClassCastException e) {
            return defineColor(card) + "              ";
        }
        int counter = 0;
        String pre, post, output = "";
        if (newCard.getPlayPattern() != null && !card.isFacingUp()) {
            counter += newCard.getPlayPattern().getCost().get(Resource.MUSHROOM);
            counter += newCard.getPlayPattern().getCost().get(Resource.WOLF);
            counter += newCard.getPlayPattern().getCost().get(Resource.LEAF);
            counter += newCard.getPlayPattern().getCost().get(Resource.BUTTERFLY);
            switch (counter) {
                case 1:
                    pre = actualColor + BLACKTEXT + "     |";
                    post = actualColor + BLACKTEXT + "      ";
                    break;
                case 2:
                    pre = actualColor + BLACKTEXT + "    |";
                    post = actualColor + "     ";
                    break;
                case 3:
                    pre = actualColor + BLACKTEXT + "   |";
                    post = actualColor + BLACKTEXT + "    ";
                    break;
                case 4:
                    pre = actualColor + BLACKTEXT + "  |";
                    post = actualColor + BLACKTEXT + "   ";
                    break;
                case 5:
                    pre = actualColor + BLACKTEXT + " |";
                    post = actualColor + BLACKTEXT + "  ";
                    break;
                default:
                    return actualColor + "              ";
            }
            pre = actualColor + pre;
            post = actualColor + post;
            int c = 0;
            while (c < newCard.getPlayPattern().getCost().get(Resource.MUSHROOM)) {
                output = output + RED + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < newCard.getPlayPattern().getCost().get(Resource.LEAF)) {
                output = output + GREEN + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < newCard.getPlayPattern().getCost().get(Resource.WOLF)) {
                output = output + BLUE + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < newCard.getPlayPattern().getCost().get(Resource.BUTTERFLY)) {
                output = output + PURPLE + " " + actualColor + "|";
                c++;
            }
            return pre + output + post;
        } else return actualColor + "              ";

    }

}
