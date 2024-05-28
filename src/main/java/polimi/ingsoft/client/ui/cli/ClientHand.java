package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.*;

import static polimi.ingsoft.client.ui.cli.ClientPublicBoard.*;

public class ClientHand {

    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW = "\u001B[43m";
    public static final String BLACKTEXT = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
    private String color;
    private ArrayList<MixedCard> cards;
    private ArrayList<Boolean> isFlipped; //true: visualizzo back false:visualizzo front
    private QuestCard questCard;

    public ClientHand() {
        this.cards = new ArrayList<>();
        this.isFlipped = new ArrayList<>();
    }


    public void addCard(MixedCard card) {
        if (cards.size() < 3) {
            this.cards.add(card);
            this.isFlipped.add(true);
        }
    }
    public void addQuestCard(QuestCard questCard){
        this.questCard=questCard;
    }

    public static String defineColor(MixedCard card) {
        if (card.getFront().getCenter().getItems().size() != 1) return YELLOW;
        return switch (card.getFront().getCenter().getItems().getFirst()) {
            case Resource.BUTTERFLY -> PURPLE;
            case Resource.LEAF -> GREEN;
            case Resource.MUSHROOM -> RED;
            case Resource.WOLF -> BLUE;
        };
    }
    public void initialPrint(InitialCard initial){
        cards.add(new ResourceCard(initial.getID(),initial.getFront(),initial.getBack(),0));
        cards.add(new ResourceCard(initial.getID(),initial.getFront(),initial.getBack(),0));
        isFlipped.add(true);
        isFlipped.add(false);
        print();
        this.cards=new ArrayList<>();
    }
    public void print() {
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < cards.size(); i++) {
                this.color = defineColor(cards.get(i));
                if (j < 3) {
                    System.out.print(printResource(i, j, 1));
                    System.out.print(printCenter(cards.get(i), i, j, color));
                    System.out.print(printResource(i, j, 2));
                } else if (j < 6) {
                    System.out.print(color + "          ");
                    System.out.print(printCenter(cards.get(i), i, j, color));
                    System.out.print(color + "          ");
                } else {
                    System.out.print(printResource(i, j, 3));
                    System.out.print(printCenter(cards.get(i), i, j, color));
                    System.out.print(printResource(i, j, 4));
                }
                System.out.print(RESET + "    ");
            }
            if(questCard!=null) {
                if (j == 0)
                    System.out.print(YELLOW + BLACKTEXT + "               | " + questCard.getScore() + "|               ");
                else System.out.print(printQuestCard(j, questCard));
            }
            System.out.print(RESET+"\n");
        }
    }

    private String printResource(int i, int j, int corner) {
        Item color = null;
        Face front = cards.get(i).getFront(),
                back = cards.get(i).getBack();
        String outColor = "";
        CornerSpace tempCorner = null;
        try {
            switch (corner) {
                case 1:
                    if (front.getUpLeft() != null && front.getUpLeft().getItems() != null && isFlipped.get(i)) {
                        tempCorner=front.getUpLeft();
                        color = front.getUpLeft().getItems().getFirst();
                    }
                    else if (!isFlipped.get(i) && back.getUpLeft() != null && back.getUpLeft().getItems() != null) {
                        tempCorner = back.getUpLeft();
                        color = back.getUpLeft().getItems().getFirst();
                    }
                    break;
                case 2:
                    if (front.getUpRight() != null && front.getUpRight().getItems() != null && isFlipped.get(i)) {
                        tempCorner = front.getUpRight();
                        color = front.getUpRight().getItems().getFirst();
                    }
                    else if (!isFlipped.get(i) && back.getUpRight() != null && back.getUpRight().getItems() != null) {
                        tempCorner = back.getUpRight();
                        color = back.getUpRight().getItems().getFirst();
                    }
                    break;
                case 3:
                    if (front.getBottomLeft() != null && front.getBottomLeft().getItems() != null && isFlipped.get(i)) {
                        tempCorner = front.getBottomLeft();
                        color = front.getBottomLeft().getItems().getFirst();
                    }
                    else if (!isFlipped.get(i) && back.getBottomLeft() != null && back.getBottomLeft().getItems() != null) {
                        tempCorner = back.getBottomLeft();
                        color = back.getBottomLeft().getItems().getFirst();
                    }
                    break;
                case 4:
                    if (front.getBottomRight() != null && front.getBottomRight().getItems() != null && isFlipped.get(i)) {
                        tempCorner = front.getBottomRight();
                        color = front.getBottomRight().getItems().getFirst();
                    }
                    else if (!isFlipped.get(i) && back.getBottomRight() != null && back.getBottomRight().getItems() != null) {
                        tempCorner = back.getBottomRight();
                        color = back.getBottomRight().getItems().getFirst();
                    }
                    break;
            }
        } catch (NoSuchElementException e) {
            outColor = YELLOW + BLACKTEXT;
            if (j == 2 || j == 8) return (outColor + "!________!");
            else if (j == 6 || j == 0) return (outColor + "¡‾‾‾‾‾‾‾‾¡");
            else return (outColor + "|        |");
        }
        if(tempCorner==null){
            if(front.getCenter().getItems().size()==1) {
                color = front.getCenter().getItems().getFirst();
                if (color.equals(Resource.LEAF)) return GREEN + "          ";
                else if (color.equals(Resource.MUSHROOM)) return RED + "          ";
                else if (color.equals(Resource.WOLF)) return BLUE + "          ";
                else return PURPLE + "          ";
            }else return YELLOW+"          ";
        }

        if(color == Object.POTION && (j == 1 || j == 7)) return (YELLOW + BLACKTEXT + "| POTION |");
        else if (color == Object.SCROLL && (j == 1 || j == 7)) return (YELLOW + BLACKTEXT + "| SCROLL |");
        else if (color == Object.FEATHER && (j == 1 || j == 7)) return (YELLOW + BLACKTEXT + "|FEATHER |");
        else if (color == Resource.BUTTERFLY) outColor = PURPLE + BLACKTEXT;
        else if (color == Resource.WOLF) outColor = BLUE + BLACKTEXT;
        else if (color == Resource.MUSHROOM) outColor = RED + BLACKTEXT;
        else if (color == Resource.LEAF) outColor = GREEN + BLACKTEXT;
        else outColor = YELLOW + BLACKTEXT;
        if (j == 2 || j == 8) return (outColor + "!________!");
        else if (j == 6 || j == 0) return (outColor + "¡‾‾‾‾‾‾‾‾¡");
        else return (outColor + "|        |");
    }

    public void flip(int i) {
        Boolean flip = !this.isFlipped.get(i);
        this.isFlipped.remove(i);
        this.isFlipped.add(i, flip);
    }

    private String printCenter(MixedCard card, int i, int j, String actualColor) {
        Resource resource;
        CenterSpace center;
        String print="";
        if (j == 0 && !isFlipped.get(i)) return printFirstRow(card, i, actualColor);
        else if (j == 8 && !isFlipped.get(i))return printLastRow(card,i,j,actualColor);
        else if (isFlipped.get(i) && (j == 0 || j == 8))return actualColor + "              ";
        else if(!isFlipped.get(i)&&j!=0&&j!=8)return actualColor + "              ";
        else {
            center = card.getFront().getCenter();
            switch (center.getItems().size()) {
                case 1:
                    if (j < 3 || j > 5) return actualColor + "              ";
                    resource = center.getItems().getFirst();
                    print = switch (j) {
                        case 3 -> BLACKTEXT + "¡‾‾‾‾‾‾¡";
                        case 4 -> BLACKTEXT + "|      |";
                        case 5 -> BLACKTEXT + "!______!";
                        default -> print;
                    };
                    break;
                case 2:
                    if (j < 1 || j > 6) return actualColor + "              ";
                    if (j < 4) resource = center.getItems().getFirst();
                    else resource = center.getItems().get(1);
                    print = switch (j) {
                        case 1, 4 -> BLACKTEXT + "¡‾‾‾‾‾‾¡";
                        case 2, 5 -> BLACKTEXT + "|      |";
                        case 3, 6 -> BLACKTEXT + "!______!";
                        default -> print;
                    };
                    break;
                case 3:
                    if (j == 7) return actualColor + "              ";
                    else if (j < 3) resource = center.getItems().getFirst();
                    else if (j < 5) resource = center.getItems().get(1);
                    else resource = center.getItems().get(2);
                    print = switch (j) {
                        case 1, 3, 5 -> BLACKTEXT + "¡‾‾‾‾‾‾¡";
                        case 2, 4 -> BLACKTEXT + "!      !";
                        case 6 -> BLACKTEXT + "!______!";
                        default -> print;
                    };
                    break;
                default:
                    return actualColor + "              ";
            }
            return switch (resource) {
                case Resource.LEAF -> actualColor + "   " + GREEN + print + actualColor + "   ";
                case Resource.BUTTERFLY -> actualColor + "   " + PURPLE + print + actualColor + "   ";
                case Resource.MUSHROOM -> actualColor + "   " + RED + print + actualColor + "   ";
                case Resource.WOLF -> actualColor + "   " + BLUE + print + actualColor + "   ";
            };
        }
    }

    public ArrayList<Boolean> getIsFlipped() {
        return this.isFlipped;
    }

    private String printFirstRow(MixedCard card, int i, String actualColor) {
        if (card.getScore(isFlipped.get(i)) != 0 && card.getPointPattern() == null)
            return actualColor + BLACKTEXT + "     | " + card.getScore(isFlipped.get(i)) + "|     ";
        else if (cards.get(i).getScore(isFlipped.get(i)) != 0 && card.getPointPattern() != null) {
            try {
                HashMap<Item, Integer> cost = ((ItemPattern) card.getPointPattern()).getCost();
                if (cost.get(Object.FEATHER) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(isFlipped.get(i)) + "x Feather| ";
                else if (cost.get(Object.POTION) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(isFlipped.get(i)) + "x Potion|  ";
                else if (cost.get(Object.SCROLL) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(isFlipped.get(i)) + "x Scroll|  ";
            } catch (ClassCastException e) {
                return actualColor + BLACKTEXT + " |" + card.getScore(isFlipped.get(i)) + "x Corner|  ";
            }
        } else return actualColor + "              ";
        return actualColor + "              ";
    }

    private String printLastRow(MixedCard card, int i, int j, String actualColor) {
        int counter = 0;
        String pre, post, output = "";
        if (!isFlipped.get(i) && card.getPlayPattern() != null) {
            counter += card.getPlayPattern().getCost().get(Resource.MUSHROOM);
            counter += card.getPlayPattern().getCost().get(Resource.WOLF);
            counter += card.getPlayPattern().getCost().get(Resource.LEAF);
            counter += card.getPlayPattern().getCost().get(Resource.BUTTERFLY);
            switch (counter) {
                case 1:
                    pre = "     |";
                    post = "      ";
                    break;
                case 2:
                    pre = "    |";
                    post = "     ";
                    break;
                case 3:
                    pre = "   |";
                    post = "    ";
                    break;
                case 4:
                    pre = "  |";
                    post = "   ";
                    break;
                case 5:
                    pre = " |";
                    post = "  ";
                    break;
                default:
                    return actualColor + "              ";
            }
            pre = actualColor + pre;
            post = actualColor + post;
            int c = 0;
            while (c < card.getPlayPattern().getCost().get(Resource.MUSHROOM)) {
                output = output + RED + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < card.getPlayPattern().getCost().get(Resource.LEAF)) {
                output = output + GREEN + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < card.getPlayPattern().getCost().get(Resource.WOLF)) {
                output = output + BLUE + " " + actualColor + "|";
                c++;
            }
            c = 0;
            while (c < card.getPlayPattern().getCost().get(Resource.BUTTERFLY)) {
                output = output + PURPLE + " " + actualColor + "|";
                c++;
            }
            return pre + output + post;
        } else return actualColor + "              ";

    }

    private void removeCard(int index) {
        cards.remove(index);
    }

}
