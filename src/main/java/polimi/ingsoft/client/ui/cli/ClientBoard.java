package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;
//EACH CARD IS 34 CHARACTERS WIDE AND 9 TALL
import javax.swing.*;
import java.util.HashMap;

import static polimi.ingsoft.server.enumerations.Object.*;

public class ClientBoard {
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

    public static void printBoard(Board board,Coordinates actualCoordinates) {
        PlayedCard card;
        int count = 0, row = 0;
        String color = "";
        System.out.print(RESET);
        if (getCardAtRespective(board,-2, 2,actualCoordinates) != null ||
                getCardAtRespective(board,-3, 3,actualCoordinates) != null ||
                getCardAtRespective(board,-1, 3,actualCoordinates) != null ||
                getCardAtRespective(board,-3, 1,actualCoordinates) != null) System.out.print(UPLEFTARROW+"         |              ");
        else System.out.print("                         ");
        if (getCardAtRespective(board,0, 2,actualCoordinates) != null ||
                getCardAtRespective(board,-1, 3,actualCoordinates) != null ||
                getCardAtRespective(board,1, 3,actualCoordinates) != null) System.out.print("|               "+UPARROW+"                |              ");
        else System.out.print("                                                ");
        if (getCardAtRespective(board,2, 2,actualCoordinates) != null ||
                getCardAtRespective(board,3, 3,actualCoordinates) != null ||
                getCardAtRespective(board,1, 3,actualCoordinates) != null ||
                getCardAtRespective(board,3, 1,actualCoordinates) != null) System.out.print("|         "+UPRIGHTARROW);
        else System.out.print("          ");
        System.out.print(RESET + "\n");


        do {
            card = getCardAtRespective(board,-1, 1,actualCoordinates);
            if (card != null || getCardAtRespective(board,-2,2,actualCoordinates ) != null || getCardAtRespective(board,0, 2,actualCoordinates) != null) {
                if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
                else color = defineColor(card);
                System.out.print(printLeftUpLeftCorner(board,card, count,actualCoordinates));
                System.out.print(printCenter(row, card, color));
                System.out.print(printLeftUpRightCorner(board,card, count,actualCoordinates));
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 35; i++) System.out.print(" ");
            }
            System.out.print(RESET);
            for (int i = 0; i < 14; i++) System.out.print(" ");

            card = getCardAtRespective(board,1, 1,actualCoordinates);
            if (card != null || getCardAtRespective(board,2, 2,actualCoordinates) != null || getCardAtRespective(board,0, 2,actualCoordinates) != null) {
                if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
                else color = defineColor(card);
                System.out.print(printRightUpLeftCorner(board,card, count,actualCoordinates));
                System.out.print(printCenter(row, card, color));
                System.out.print(printRightUpRightCorner(board,card, count,actualCoordinates));
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 33; i++) System.out.print(" ");
            }
            if (count == 2 && getCardAtRespective(board,2, 2,actualCoordinates) != null) System.out.print(RESET + "_");
            else System.out.print(RESET + " ");
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);


        count = 0;
        do {
            card = getCardAtRespective(board,-1, 1,actualCoordinates);
            if (card != null || getCardAtRespective(board,-2, 2,actualCoordinates) != null || getCardAtRespective(board,0, 2,actualCoordinates) != null) {
                printCentralRows(card, row);
                System.out.print(RESET);
                for (int i = 0; i < 13; i++) System.out.print(" ");
            } else {
                System.out.print(RESET);
                for (int i = 0; i < 48; i++) System.out.print(" ");
            }

            card = getCardAtRespective(board,1, 1,actualCoordinates);
            if (card != null || getCardAtRespective(board,2, 2,actualCoordinates) != null || getCardAtRespective(board,0, 2,actualCoordinates) != null) {
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
            card = getCardAtRespective(board,-1, 1,actualCoordinates);
            color = defineColor(card);
            if (getCardAtRespective(board,-2, 0,actualCoordinates) != null && count == 0) System.out.print(RESET + "‾");
            else System.out.print(" ");
            System.out.print(printLeftBottomLeftCorner(board,card, count,actualCoordinates));
            System.out.print(printCenter(row, card, color));
            System.out.print(printLeftBottomRightCorner(board,card, count,actualCoordinates));
            card = getCardAtRespective(board,0, 0,actualCoordinates);
            color = defineColor(card);
            System.out.print(printCenter(row - 6, card, color));
            card = getCardAtRespective(board,1, 1,actualCoordinates);
            color = defineColor(card);
            System.out.print(printRightBottomLeftCorner(board,card, count,actualCoordinates));
            System.out.print(printCenter(row, card, color));
            System.out.print(printRightBottomRightCorner(board,card, count,actualCoordinates));
            if (count == 0 && getCardAtRespective(board,2, 0,actualCoordinates) != null) System.out.print(RESET + "‾");
            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
        row = 3;
        card = getCardAtRespective(board,0, 0,actualCoordinates);
        do {
            if (row == 4 &&
                    (getCardAtRespective(board,-2, 0,actualCoordinates) != null ||
                    getCardAtRespective(board,-3, 1,actualCoordinates) != null ||
                    getCardAtRespective(board,-3, -1,actualCoordinates) != null))
                System.out.print(LEFTARROW);
            else System.out.print(" ");
            System.out.print(RESET + "                       ");
            printCentralRows(card, row);
            if (row == 4 &&
                    (getCardAtRespective(board,2, 0,actualCoordinates) != null ||
                    getCardAtRespective(board,3, 1,actualCoordinates) != null ||
                    getCardAtRespective(board,3, -1,actualCoordinates) != null))
                System.out.print(RESET + "                        " + RIGHTARROW);
            System.out.print(RESET + "\n");
            row++;
        } while (row < 6);
        count = 0;
        do {
            card = getCardAtRespective(board,-1, -1,actualCoordinates);
            if (count == 2 && getCardAtRespective(board,-2, 0,actualCoordinates) != null) System.out.print("_");
            else System.out.print(" ");
            System.out.print(printBottomLeftUpLeftCorner(board,card, count,actualCoordinates));
            System.out.print(printCenter(row - 6, card, defineColor(card)));
            System.out.print(printBottomLeftUpRightCorner(board,card, count,actualCoordinates));
            card = getCardAtRespective(board,0, 0,actualCoordinates);
            System.out.print(printCenter(row, card, defineColor(card)));
            card = getCardAtRespective(board,1, -1,actualCoordinates);

            System.out.print(printBottomRightUpLeftCorner(board,card, count,actualCoordinates));
            System.out.print(printCenter(row - 6, card, defineColor(card)));
            System.out.print(printBottomRightUpRightCorner(board,card, count,actualCoordinates));
            if (count == 2 && getCardAtRespective(board,2, 0,actualCoordinates) != null) System.out.print(RESET + "_");

            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
        row = row - 6;
        count = 0;
        do {
            card = getCardAtRespective(board,-1, -1,actualCoordinates);
            printCentralRows(card, row);
            System.out.print(RESET + "             ");
            card = getCardAtRespective(board,1, -1,actualCoordinates);
            printCentralRows(card, row);
            row++;
            count++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        count = 0;
        do {
            card = getCardAtRespective(board,-1, -1,actualCoordinates);
            color=defineColor(card);
            if(count==0&&getCardAtRespective(board,-2,-2,actualCoordinates)!=null)System.out.print("‾");
            else System.out.print(" ");
            System.out.print(printBottomLeftBottomLeftCorner(card, count,board,actualCoordinates));
            System.out.print(printCenter(row,card,color));
            System.out.print(printBottomLeftBottomRightCorner(card, count,board,actualCoordinates));
            System.out.print(RESET+"              ");
            card = getCardAtRespective(board,1, -1,actualCoordinates);
            color=defineColor(card);
            System.out.print(printBottomRightBottomLeftCorner(card, count,board,actualCoordinates));
            System.out.print(printCenter(row,card,color));
            System.out.print(printBottomRightBottomRightCorner(card, count,board,actualCoordinates));
            if(count==0&&getCardAtRespective(board,2,-2,actualCoordinates)!=null)System.out.print(RESET+"‾");
            else System.out.print(RESET+" ");
            System.out.print(RESET + "\n");
            row++;
            count++;
        } while (count < 3);
        if(getCardAtRespective(board,-2,-2,actualCoordinates)!=null||
                getCardAtRespective(board,-1,-3,actualCoordinates)!=null||
                getCardAtRespective(board,-3,-3,actualCoordinates)!=null||
                getCardAtRespective(board,-3,-1,actualCoordinates)!=null)System.out.print(DOWNLEFTARROW+"         |");
        else System.out.print("           ");
        System.out.print("              ");
        if(getCardAtRespective(board,0,-2,actualCoordinates)!=null||
                getCardAtRespective(board,-1,-3,actualCoordinates)!=null||
                getCardAtRespective(board,1,-3,actualCoordinates)!=null)System.out.print("|               "+DOWNARROW+"                |              ");
        else System.out.print("                                                ");
        if(getCardAtRespective(board,2,-2,actualCoordinates)!=null||
                getCardAtRespective(board,3,-1,actualCoordinates)!=null||
                getCardAtRespective(board,3,-3,actualCoordinates)!=null||
                getCardAtRespective(board,1,-3,actualCoordinates)!=null)System.out.print("|         "+DOWNRIGHTARROW);
        else System.out.print("           ");
    }

    private static String printBottomLeftBottomLeftCorner(PlayedCard card, int count, Board board,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,-2, -2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,-2, -2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,-2, -2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,-2, -2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,-2, -2,actualCoordinates).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,-2, -2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomLeftBottomRightCorner(PlayedCard card, int count,Board board, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, -2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, -2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, -2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, -2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, -2,actualCoordinates).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, -2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomRightBottomRightCorner(PlayedCard card, int count,Board board,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,2, -2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,2, -2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,2, -2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,2, -2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,2, -2,actualCoordinates).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,2, -2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomRightBottomLeftCorner(PlayedCard card, int count,Board board, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, -2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, -2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, -2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, -2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, -2,actualCoordinates).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, -2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static void printCentralRows(PlayedCard card, int row) {
        String color;
        if (card != null && card.getCenter().getItems().size() > 1) color = YELLOW;
        else color = defineColor(card);
        System.out.print(" " + color + "          ");
        System.out.print(printCenter(row, card, color));
        System.out.print(color + "          ");
    }

    private static PlayedCard getCardAtRespective(Board board,int x, int y,Coordinates actualCoordinates) {
        return board.getCard(actualCoordinates.sum(new Coordinates(x, y)));
    }

    private static String printBottomRightUpRightCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,2, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,2, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,2, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,2, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,2, 0,actualCoordinates).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,2, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomRightUpLeftCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 0,actualCoordinates).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomLeftUpRightCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 0,actualCoordinates).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printBottomLeftUpLeftCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,-2, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,-2, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,-2, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,-2, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,-2, 0,actualCoordinates).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,-2, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printRightBottomLeftCorner(Board board,PlayedCard card, int count, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 0,actualCoordinates).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printRightBottomRightCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,2, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,2, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,2, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,2, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,2, 0,actualCoordinates).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,2, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printLeftBottomRightCorner(Board board,PlayedCard card, int count, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 0,actualCoordinates).getUpLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printLeftBottomLeftCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,-2, 0,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,-2, 0,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,-2, 0,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getBottomLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,-2, 0,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,-2, 0,actualCoordinates).getUpRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,-2, 0,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printRightUpLeftCorner(Board board,PlayedCard card, int count, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 2,actualCoordinates).getBottomRightCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printRightUpRightCorner(Board board,PlayedCard card, int count, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,2, 2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,2, 2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,2, 2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,2, 2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,2, 2,actualCoordinates).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,2, 2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printLeftUpRightCorner(Board board,PlayedCard card, int count, Coordinates actualCoordinates) {
        CornerSpace corner;
        if (getCardAtRespective(board,0, 2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,0, 2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,0, 2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpRightCorner();
            if (corner == null) return defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,0, 2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,0, 2,actualCoordinates).getBottomLeftCorner();
            if (corner == null) return defineColor(getCardAtRespective(board,0, 2,actualCoordinates)) + "          ";
        }
        return printCorner("", corner, count);
    }

    private static String printLeftUpLeftCorner(Board board,PlayedCard card, int count,Coordinates actualCoordinates) {
        String pre = "";
        CornerSpace corner;
        switch (count) {
            case 0, 1:
                pre = " ";
                break;
            case 2:
                if (getCardAtRespective(board,-2, 2,actualCoordinates) == null) pre = " ";
                else pre = "_";
                break;
        }
        //carta 22si carta 11si   //carta 22si carta11 no
        if (getCardAtRespective(board,-2, 2,actualCoordinates) == null && card != null //carta 22no carta 11si //carta 22si carta 11si e 22<11
                || (getCardAtRespective(board,-2, 2,actualCoordinates) != null && card != null &&
                getCardAtRespective(board,-2, 2,actualCoordinates).getOrder() < card.getOrder())) {//caso in cui l'angolo in alto a sx non sia coperto
            corner = card.getUpLeftCorner();
            if (corner == null) return pre + defineColor(card) + "          ";
        } else if (card == null && getCardAtRespective(board,-2, 2,actualCoordinates) == null) {//carta 22 no carta 11 no
            return RESET + "          ";
        } else {//carta 22si carta 11si e 22>11 //carta 22si carta11 no
            corner = getCardAtRespective(board,-2, 2,actualCoordinates).getBottomRightCorner();
            if (corner == null) return pre + defineColor(getCardAtRespective(board,-2, 2,actualCoordinates)) + "          ";
        }
        return printCorner(pre, corner, count);
    }

    private static String printCorner(String pre, CornerSpace corner, int count) {
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

    private static String printCenter(int row, PlayedCard card, String actualColor) {
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

    private static String defineColor(PlayedCard card) {
        if (card != null && card.getCenter().getItems().size() != 1) return YELLOW;
        return card == null ? "" : switch (card.getColor()) {
            case Resource.BUTTERFLY -> PURPLE;
            case Resource.LEAF -> GREEN;
            case Resource.MUSHROOM -> RED;
            case Resource.WOLF -> BLUE;
        };
    }

    private static String printObject(Item object) {
        return switch (object) {
            case SCROLL -> BLACKTEXT + "| SCROLL |";
            case POTION -> BLACKTEXT + "| POTION |";
            case FEATHER -> BLACKTEXT + "|FEATHER |";
            default -> BLACKTEXT + "|        |";
        };
    }

    private static String printLastRow(PlayedCard card, String actualColor) {
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

    public static void printBoard(Board board,BoardArgument argument,Coordinates actualCoordinates) {
        String error="ERROR: place not avaiable!";
        switch (argument) {
            case BoardArgument.UPLEFT:
                if (getCardAtRespective(board,-2, 2,actualCoordinates) != null ||
                        getCardAtRespective(board,-3, 3,actualCoordinates) != null ||
                        getCardAtRespective(board,-1, 3,actualCoordinates) != null ||
                        getCardAtRespective(board,-3, 1,actualCoordinates) != null) board.updatePrintingCoordinates(-1,1);
                else System.out.print(error);
                break;
            case BoardArgument.UP:
                if (getCardAtRespective(board,0, 2,actualCoordinates) != null ||
                        getCardAtRespective(board,-1, 3,actualCoordinates) != null ||
                        getCardAtRespective(board,1, 3,actualCoordinates) != null)board.updatePrintingCoordinates(0,2);
                else System.out.print(error);
                break;
            case BoardArgument.UPRIGHT:
                if (getCardAtRespective(board,2, 2,actualCoordinates) != null ||
                        getCardAtRespective(board,3, 3,actualCoordinates) != null ||
                        getCardAtRespective(board,1, 3,actualCoordinates) != null ||
                        getCardAtRespective(board,3, 1,actualCoordinates) != null)board.updatePrintingCoordinates(1,1);
                else System.out.print(error);
                break;
            case BoardArgument.LEFT:
                if(getCardAtRespective(board,-2, 0,actualCoordinates) != null ||
                        getCardAtRespective(board,-3, 1,actualCoordinates) != null ||
                        getCardAtRespective(board,-3, -1,actualCoordinates) != null)board.updatePrintingCoordinates(-2,0);
                else System.out.print(error);
                break;
            case BoardArgument.RIGHT:
                if(getCardAtRespective(board,2, 0,actualCoordinates) != null ||
                        getCardAtRespective(board,3, 1,actualCoordinates) != null ||
                        getCardAtRespective(board,3, -1,actualCoordinates) != null)board.updatePrintingCoordinates(2,0);
                else System.out.print(error);
                break;
            case BoardArgument.DOWNLEFT:
                if(getCardAtRespective(board,-2,-2,actualCoordinates)!=null||
                        getCardAtRespective(board,-1,-3,actualCoordinates)!=null||
                        getCardAtRespective(board,-3,-3,actualCoordinates)!=null||
                        getCardAtRespective(board,-3,-1,actualCoordinates)!=null)board.updatePrintingCoordinates(-2, -2);
                else System.out.print(error);
                break;
            case BoardArgument.DOWN:
                if(getCardAtRespective(board,0,-2,actualCoordinates)!=null||
                        getCardAtRespective(board,-1,-3,actualCoordinates)!=null||
                        getCardAtRespective(board,1,-3,actualCoordinates)!=null)board.updatePrintingCoordinates(0,-2);
                else System.out.print(error);
                break;
            case DOWNRIGHT:
                if(getCardAtRespective(board,2,-2,actualCoordinates)!=null||
                        getCardAtRespective(board,3,-1,actualCoordinates)!=null||
                        getCardAtRespective(board,3,-3,actualCoordinates)!=null||
                        getCardAtRespective(board,1,-3,actualCoordinates)!=null)board.updatePrintingCoordinates(2,-2);
                else System.out.print(error);
                break;
        }
        printBoard(board,board.getPrintingCoordinates());
    }
    
}
