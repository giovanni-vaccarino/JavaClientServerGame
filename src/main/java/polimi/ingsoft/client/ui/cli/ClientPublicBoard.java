package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ClientPublicBoard {
    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW = "\u001B[43m";
    public static final String BLACKTEXT = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
    //ArrayList<ResourceCard> resourceCards;
    ArrayList<MixedCard> resourceCards, goldCards;
    //ArrayList<GoldCard> goldCards;
    ArrayList<QuestCard> questCards;

    public ClientPublicBoard(QuestCard questCard1, QuestCard questCard2,
                             ResourceCard resDeck, ResourceCard res1, ResourceCard res2,
                             GoldCard goldDeck, GoldCard gold1, GoldCard gold2) {
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        questCards = new ArrayList<>();
        questCards.add(questCard1);
        questCards.add(questCard2);
        resourceCards.add(resDeck);
        resourceCards.add(res1);
        resourceCards.add(res2);
        goldCards.add(goldDeck);
        goldCards.add(gold1);
        goldCards.add(gold2);
    }

    public void printClientPublicBoard() {
        printRows(resourceCards);
        printRows(goldCards);
        printQuests();

    }

    private void printRows(ArrayList<MixedCard> mixed) {
        int row = 0, count = 0;
        do {
            System.out.print(YELLOW + "          " + ClientHand.defineColor(mixed.getFirst()) + "              " + YELLOW + "          " + RESET + "     ");
            System.out.print(printResource(mixed, 1, 1, row));
            System.out.print(printCenter(mixed.get(1), row, ClientHand.defineColor(mixed.get(1))));
            System.out.print(printResource(mixed, 1, 2, row) + RESET + "   ");

            System.out.print(printResource(mixed, 2, 1, row));
            System.out.print(printCenter(mixed.get(2), row, ClientHand.defineColor(mixed.get(2))));
            System.out.print(printResource(mixed, 2, 2, row));
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        System.out.print(ClientHand.defineColor(mixed.getFirst()) + BLACKTEXT + "            ¡‾‾‾‾‾‾‾‾¡            ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        System.out.print(ClientHand.defineColor(mixed.getFirst()) + BLACKTEXT + "            |        |            ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        System.out.print(ClientHand.defineColor(mixed.getFirst()) + BLACKTEXT + "            !________!            ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        row = 6;
        count = 0;
        do {
            System.out.print(YELLOW + "          " + ClientHand.defineColor(mixed.getFirst()) + "              " + YELLOW + "          " + RESET + "     ");
            System.out.print(printResource(mixed, 1, 3, row));
            System.out.print(printCenter(mixed.get(1), row, ClientHand.defineColor(mixed.get(1))));
            System.out.print(printResource(mixed, 1, 4, row) + RESET + "   ");

            System.out.print(printResource(mixed, 2, 3, row));
            System.out.print(printCenter(mixed.get(2), row, ClientHand.defineColor(mixed.get(2))));
            System.out.print(printResource(mixed, 2, 4, row));
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        System.out.print("\n");
    }


    private String printResource(ArrayList<MixedCard> mixed, int index, int corner, int row) {
        Item color;
        Face back = mixed.get(index).getBack();
        String outColor = "";
        try {
            color = switch (corner) {
                case 1 -> back.getUpLeft().getItems().getFirst();
                case 2 -> back.getUpRight().getItems().getFirst();
                case 3 -> back.getBottomLeft().getItems().getFirst();
                case 4 -> back.getBottomRight().getItems().getFirst();
                default -> throw new IllegalStateException("Unexpected value: " + corner);
            };
        } catch (NoSuchElementException e) {
            outColor = YELLOW + BLACKTEXT;
            if (row == 2 || row == 8) return (outColor + "!________!");
            else if (row == 6 || row == 0) return (outColor + "¡‾‾‾‾‾‾‾‾¡");
            else return (outColor + "|        |");
        } catch (NullPointerException f) {
            return ClientHand.defineColor(mixed.get(index)) + "          ";
        }
        if (color == Object.POTION && (row == 1 || row == 7)) return (YELLOW + BLACKTEXT + "| POTION |");
        else if (color == Object.SCROLL && (row == 1 || row == 7)) return (YELLOW + BLACKTEXT + "| SCROLL |");
        else if (color == Object.FEATHER && (row == 1 || row == 7)) return (YELLOW + BLACKTEXT + "|FEATHER |");
        else if (color == Resource.BUTTERFLY) outColor = PURPLE + BLACKTEXT;
        else if (color == Resource.WOLF) outColor = BLUE + BLACKTEXT;
        else if (color == Resource.MUSHROOM) outColor = RED + BLACKTEXT;
        else if (color == Resource.LEAF) outColor = GREEN + BLACKTEXT;
        else outColor = YELLOW + BLACKTEXT;
        if (row == 2 || row == 8) return (outColor + "!________!");
        else if (row == 6 || row == 0) return (outColor + "¡‾‾‾‾‾‾‾‾¡");
        else return (outColor + "|        |");
    }


    private String printCenter(MixedCard card, int row, String actualColor) {
        Resource resource;
        CenterSpace center;
        String print = "";
        if (row == 0) return printFirstRow(card, actualColor);
        else if (row == 8) return printLastRow(card, actualColor);
        else {
            center = card.getFront().getCenter();
            switch (center.getItems().size()) {
                case 1:
                    if (row < 3 || row > 5) return actualColor + "              ";
                    resource = center.getItems().getFirst();
                    print = switch (row) {
                        case 3 -> BLACKTEXT + "¡‾‾‾‾‾‾¡";
                        case 4 -> BLACKTEXT + "|      |";
                        case 5 -> BLACKTEXT + "!______!";
                        default -> print;
                    };
                    break;
                case 2:
                    if (row < 1 || row > 6) return actualColor + "              ";
                    if (row < 4) resource = center.getItems().getFirst();
                    else resource = center.getItems().get(1);
                    print = switch (row) {
                        case 1, 4 -> BLACKTEXT + "¡‾‾‾‾‾‾¡";
                        case 2, 5 -> BLACKTEXT + "|      |";
                        case 3, 6 -> BLACKTEXT + "!______!";
                        default -> print;
                    };
                    break;
                case 3:
                    if (row == 7) return actualColor + "              ";
                    else if (row < 3) resource = center.getItems().getFirst();
                    else if (row < 5) resource = center.getItems().get(1);
                    else resource = center.getItems().get(2);
                    print = switch (row) {
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

    private String printLastRow(MixedCard card, String actualColor) {
        int counter = 0;
        String pre, post, output = "";
        if (card.getPlayPattern() != null) {
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
            pre = BLACKTEXT + actualColor + pre;
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


    private String printFirstRow(MixedCard card, String actualColor) {
        if (card.getPointPattern() == null)
            return actualColor + BLACKTEXT + "     | " + card.getScore(false) + "|     ";
        else if (card.getScore(false) != 0 && card.getPointPattern() != null) {
            try {
                HashMap<Item, Integer> cost = ((ItemPattern) card.getPointPattern()).getCost();
                if (cost.get(Object.FEATHER) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(false) + "x Feather| ";
                else if (cost.get(Object.POTION) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(false) + "x Potion|  ";
                else if (cost.get(Object.SCROLL) != 0)
                    return actualColor + BLACKTEXT + " |" + card.getScore(false) + "x Scroll|  ";
            } catch (ClassCastException e) {
                return actualColor + BLACKTEXT + " |" + card.getScore(false) + "x Corner|  ";
            }
        } else return actualColor + "              ";
        return actualColor + "              ";
    }

    private void printQuests() {
        int count = 0;
        int row = 1;
        QuestCard card;
        System.out.print(YELLOW + "                                  " + RESET + "     ");
        card = questCards.getFirst();
        System.out.print(YELLOW + BLACKTEXT + "               | " + card.getScore() + "|               " + RESET + "   ");
        card = questCards.get(1);
        System.out.print(YELLOW + BLACKTEXT + "               | " + card.getScore() + "|               ");
        System.out.print(RESET + "\n");
        do {
            System.out.print(YELLOW + "                                  " + RESET + "     ");
            card = questCards.getFirst();
            System.out.print(printQuestCard(row, card) + RESET + "   ");
            card = questCards.get(1);
            System.out.print(printQuestCard(row, card) + RESET + "\n");
            row++;
        } while (row < 9);
    }

    private String printQuestCard(int row, QuestCard card) {
        Pattern pattern;
        pattern = card.getPointPattern();
        try {
            if (row == 4) {
                HashMap<Item, Integer> cost = ((ItemPattern) pattern).getCost();
                if (cost.get(Object.FEATHER) == 2) return YELLOW + BLACKTEXT + "         EACH 2 FEATHERS          ";
                else if (cost.get(Object.SCROLL) == 2) return YELLOW + BLACKTEXT + "          EACH 2 SCROLLS          ";
                else if (cost.get(Object.POTION) == 2) return YELLOW + BLACKTEXT + "          EACH 2 POTIONS          ";
                else if (cost.get(Resource.MUSHROOM) == 3)
                    return YELLOW + BLACKTEXT + "         EACH 3 MUSHROOMS         ";
                else if (cost.get(Resource.BUTTERFLY) == 3)
                    return YELLOW + BLACKTEXT + "        EACH 3 BUTTERFLIES        ";
                else if (cost.get(Resource.WOLF) == 3) return YELLOW + BLACKTEXT + "          EACH 3 WOLVES           ";
                else if (cost.get(Resource.LEAF) == 3) return YELLOW + BLACKTEXT + "          EACH 3 LEAVES           ";
                else return (YELLOW + BLACKTEXT + "EACH SET OF SCROLL,POTION,FEATHER ");
            } else return YELLOW + "                                  ";
        } catch (ClassCastException e) {
            try {
                ArrayList<Link> links = ((SchemePattern) pattern).getOrder();
                return YELLOW + "OK                                ";
            } catch (ClassCastException f) {
                return "                   QUALCOSA NON VA";
            }
        }
    }

    public void getCard(PublicBoardArguments argument, PublicBoardArguments argument2, MixedCard changeCard) {
        switch (argument) {
            case GOLD -> {
                switch (argument2) {
                    case DECK -> {
                        goldCards.removeFirst();
                        goldCards.addFirst(changeCard);
                    }
                    case LEFT -> {
                        goldCards.remove(1);
                        goldCards.add(1, changeCard);
                    }
                    case RIGHT -> {
                        goldCards.remove(2);
                        goldCards.add(2, changeCard);
                    }
                }
            }
            case RESOURCE -> {
                switch (argument2) {
                    case DECK -> {
                        resourceCards.removeFirst();
                        resourceCards.addFirst(changeCard);
                    }
                    case LEFT -> {
                        resourceCards.remove(1);
                        resourceCards.add(1, changeCard);
                    }
                    case RIGHT -> {
                        resourceCards.remove(2);
                        resourceCards.add(2, changeCard);
                    }
                }
            }
        }
        ;
    }
}
