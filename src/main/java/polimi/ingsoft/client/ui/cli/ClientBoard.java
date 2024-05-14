package polimi.ingsoft.client.ui.cli;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.CornerSpace;
import polimi.ingsoft.server.model.InitialCard;
import polimi.ingsoft.server.model.PlayedCard;

import java.util.HashMap;

public class ClientBoard {
    HashMap<Coordinates, PlayedCard> board;
    private Coordinates actualCoordinates;
    private final static String UPLEFTARROW = "\u2196";
    private final static String UPRIGHTARROW = "\u2197";
    private final static String DOWNLEFTARROW = "\u2199";
    private final static String DOWNRIGHTARROW = "\u2198";
    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW="\u001B[43m";
    public static final String BLACKTEXT="\u001B[30m";
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
        int count=0;
        String color="";
        do {
            if (count==0&&board.get(actualCoordinates.sum(new Coordinates(-2, -2))) != null ||
                    board.get(actualCoordinates.sum(new Coordinates(0, 2))) != null ||
                    board.get(actualCoordinates.sum(new Coordinates(-2, 0))) != null) System.out.print(UPLEFTARROW);
            else System.out.println(" ");
            if (board.get(actualCoordinates.sum(new Coordinates(-1, -1))) != null) System.out.print("        |");
            else System.out.print("         ");
            for (int i = 0; i < 82; i++) System.out.print(" ");
            if (board.get(actualCoordinates.sum(new Coordinates(1, 1))) != null) System.out.print("|        ");
            else System.out.print("         ");
            if (count==0&&board.get(actualCoordinates.sum(new Coordinates(2, 2))) != null ||
                    board.get(actualCoordinates.sum(new Coordinates(0, 2))) != null ||
                    board.get(actualCoordinates.sum(new Coordinates(2, 0))) != null) System.out.print(UPRIGHTARROW);
            else System.out.println(" ");
            System.out.println("\n");
            count++;
        }while(count<2);
        count=0;
        do{
            PlayedCard card=board.get(actualCoordinates.sum(new Coordinates(-1, -1)));
            if(card!= null){
                    switch (card.getColor()){
                        case Resource.BUTTERFLY:
                            color = PURPLE;
                            break;
                        case Resource.LEAF:
                            color = GREEN;
                            break;
                        case Resource.MUSHROOM:
                            color = RED;
                            break;
                        case Resource.WOLF:
                            color = BLUE;
                            break;
                        default:
                            color=YELLOW;
                    }
                    printResource(card.getUpLeftCorner(),color,0);
            }
            count++;
        }while(count<3);
    }
    public void printResource(CornerSpace corner,String actualColor,int pos){
        String color = switch (corner.getItems().getFirst()) {
            case Resource.BUTTERFLY -> PURPLE;
            case Resource.LEAF -> GREEN;
            case Resource.MUSHROOM -> RED;
            case Resource.WOLF -> BLUE;
            case null->actualColor;
            default -> YELLOW;
        };
        switch (pos){
            case 0:System.out.print("          "+color+"¡‾‾‾‾‾‾‾‾¡");
            case 1:System.out.print("          "+color+"|        |");
            case 2:System.out.print("__________"+color+"!________!");
        }
    }
}
