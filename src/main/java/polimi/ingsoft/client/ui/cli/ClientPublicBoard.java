package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.Pattern;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class that implements printing of PublicBoard objects
 */
public class ClientPublicBoard {
    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW = "\u001B[43m";
    public static final String BLACKTEXT = "\u001B[30m";
    public static final String WHITE = "\u001B[47m";
    public static final String RESET = "\u001B[0m";

    /**
     * PublicBoard's print method
     *
     * @param resources the ResourceCard place
     * @param gold      the GoldCard place
     * @param quests    the QuestCard place
     */
    public static void printPublicBoard(PlaceInPublicBoard<ResourceCard> resources, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quests) {
        ArrayList<MixedCard> resourceCards, goldCards;
        ArrayList<QuestCard> questCards;

        resourceCards = new ArrayList<>();
        resourceCards.add(resources.get(PlaceInPublicBoard.Slots.DECK));
        resourceCards.add(resources.get(PlaceInPublicBoard.Slots.SLOT_A));
        resourceCards.add(resources.get(PlaceInPublicBoard.Slots.SLOT_B));
        goldCards = new ArrayList<>();
        goldCards.add(gold.get(PlaceInPublicBoard.Slots.DECK));
        goldCards.add(gold.get(PlaceInPublicBoard.Slots.SLOT_A));
        goldCards.add(gold.get(PlaceInPublicBoard.Slots.SLOT_B));
        printRows(resourceCards);
        printRows(goldCards);
        questCards = new ArrayList<>();
        questCards.add(quests.get(PlaceInPublicBoard.Slots.SLOT_A));
        questCards.add(quests.get(PlaceInPublicBoard.Slots.SLOT_B));
        printQuests(questCards);
        System.out.print("\n\n");
    }

    /**
     * Prints a Place's rows
     *
     * @param mixed the list of cards present in a PublicBoard's place's slots. NOTE: this list can contain just one element
     */
    private static void printRows(ArrayList<MixedCard> mixed) {
        int row = 0, count = 0;
        do {
            if(mixed.getFirst()!=null)System.out.print(YELLOW + "          " + ClientHand.defineColor(null, mixed.getFirst()) + "              " + YELLOW + "          " + RESET + "     ");
            else System.out.print(WHITE + "                                  " + RESET + "     ");
            System.out.print(printResource(mixed, 1, 1, row));
            System.out.print(printCenter(mixed.get(1), row, ClientHand.defineColor(null, mixed.get(1))));
            System.out.print(printResource(mixed, 1, 2, row) + RESET + "   ");

            System.out.print(printResource(mixed, 2, 1, row));
            System.out.print(printCenter(mixed.get(2), row, ClientHand.defineColor(null, mixed.get(2))));
            System.out.print(printResource(mixed, 2, 2, row));
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        if(mixed.getFirst()!=null)System.out.print(ClientHand.defineColor(null, mixed.getFirst()) + BLACKTEXT + "            ¡‾‾‾‾‾‾‾‾¡            ");
        else System.out.print(WHITE + "                                  ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(null, mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(null, mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        if(mixed.getFirst()!=null)System.out.print(ClientHand.defineColor(null, mixed.getFirst()) + BLACKTEXT + "            |        |            ");
        else System.out.print(WHITE + BLACKTEXT + "           EMPTY DECK !           ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(null, mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(null, mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        if(mixed.getFirst()!=null)System.out.print(ClientHand.defineColor(null, mixed.getFirst()) + BLACKTEXT + "            !________!            ");
        else System.out.print(WHITE + "                                  ");
        System.out.print(RESET + "     ");
        System.out.print(ClientHand.defineColor(null, mixed.get(1)) + "                                  ");
        System.out.print(RESET + "   ");
        System.out.print(ClientHand.defineColor(null, mixed.get(2)) + "                                  ");
        System.out.print(RESET + "\n");
        row = 6;
        count = 0;
        do {
            if(mixed.getFirst()!=null)System.out.print(YELLOW + "          " + ClientHand.defineColor(null, mixed.getFirst()) + "              " + YELLOW + "          " + RESET + "     ");
            else System.out.print(WHITE + "                                  " + RESET + "     ");
            System.out.print(printResource(mixed, 1, 3, row));
            System.out.print(printCenter(mixed.get(1), row, ClientHand.defineColor(null, mixed.get(1))));
            System.out.print(printResource(mixed, 1, 4, row) + RESET + "   ");

            System.out.print(printResource(mixed, 2, 3, row));
            System.out.print(printCenter(mixed.get(2), row, ClientHand.defineColor(null, mixed.get(2))));
            System.out.print(printResource(mixed, 2, 4, row));
            count++;
            row++;
            System.out.print(RESET + "\n");
        } while (count < 3);
        System.out.print("\n");
    }

    /**
     * Returns a string containing all information to print a card's CornerSpace
     *
     * @param mixed  the list of cards present in a PublicBoard's place
     * @param index  the number of card that's to be printed
     * @param corner the reference to the corner that's being printed
     * @param row    the number of row that's to be printed
     * @return a string containing all information to print a card's corner
     */
    private static String printResource(ArrayList<MixedCard> mixed, int index, int corner, int row) {
        Item color;
        Face back = mixed.get(index).getBack();
        String outColor;
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
            return ClientHand.defineColor(null, mixed.get(index)) + "          ";
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

    /**
     * Returns a string containing all information to print a card's CenterSpace
     *
     * @param card        the card that's being printed
     * @param row         the row that's being printed
     * @param actualColor the card's default color
     * @return a string containing all information to print a card's CenterSpace
     */
    private static String printCenter(MixedCard card, int row, String actualColor) {
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

    /**
     * Return a string containing all information to print a card's last row
     *
     * @param card        the card that's being printed
     * @param actualColor the card's default color
     * @return a string containing all information to print a card's last row
     */
    private static String printLastRow(MixedCard card, String actualColor) {
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

    /**
     * Return a string containing all information to print a card's first row
     *
     * @param card        the card that's being printed
     * @param actualColor the card's default color
     * @return a string containing all information to print a card's first row
     */
    private static String printFirstRow(MixedCard card, String actualColor) {
        if (card.getPointPattern() == null && card.getScore() != 0)
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

    /**
     * Prints PublicBoards' QuestCards
     *
     * @param questCards an ArrayList containing the QuestCards that have to be printed
     */
    private static void printQuests(ArrayList<QuestCard> questCards) {
        int row = 1;
        QuestCard card;
        System.out.print(YELLOW + "                                  " + RESET + "     ");
        printQuestFirstRow(questCards);
        do {
            System.out.print(YELLOW + "                                  " + RESET + "     ");
            card = questCards.getFirst();
            System.out.print(printQuestCard(row, card) + RESET + "   ");
            card = questCards.get(1);
            System.out.print(printQuestCard(row, card) + RESET + "\n");
            row++;
        } while (row < 9);
    }

    /**
     * Prints CLI's interface for QuestCards' initial choice
     *
     * @param questCards an ArrayList containing the QuestCards that have to be printed
     */
    public static void printInitialQuests(ArrayList<QuestCard> questCards) {
        int row = 1;
        QuestCard card;
        printQuestFirstRow(questCards);
        do {
            card = questCards.getFirst();
            System.out.print(printQuestCard(row, card) + RESET + "   ");
            card = questCards.get(1);
            System.out.print(printQuestCard(row, card) + RESET + "\n");
            row++;
        } while (row < 9);
    }

    /**
     * Prints QuestCards' first row
     *
     * @param questCards an ArrayList containing the QuestCards that have to be printed
     */
    private static void printQuestFirstRow(ArrayList<QuestCard> questCards) {
        QuestCard card;
        card = questCards.getFirst();
        System.out.print(YELLOW + BLACKTEXT + "               | " + card.getScore() + "|               " + RESET + "   ");
        card = questCards.get(1);
        System.out.print(YELLOW + BLACKTEXT + "               | " + card.getScore() + "|               ");
        System.out.print(RESET + "\n");
    }

    /**
     * Return a string containing all information to print the body of a questCard
     *
     * @param row  the row that's being printed
     * @param card the card that's being printed
     * @return a string containing all information to print the body of a questCard
     */
    public static String printQuestCard(int row, QuestCard card) {
        Pattern pattern;
        pattern = card.getPointPattern();
        try {
            HashMap<Item, Integer> cost = ((ItemPattern) pattern).getCost();
            if (row == 4) {
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
            if (row == 1 || row == 8) return YELLOW + "                                  ";
            return switch (card.getID()) {
                case "QuestCard(1)" -> printRightToLeftDiagonalQuestCards(row, RED);
                case "QuestCard(2)" -> printLeftToRightDiagonalQuestCards(row, GREEN);
                case "QuestCard(3)" -> printRightToLeftDiagonalQuestCards(row, BLUE);
                case "QuestCard(4)" -> printLeftToRightDiagonalQuestCards(row, PURPLE);
                case "QuestCard(5)" -> switch (row) {
                    case 2, 3, 4, 5 -> YELLOW + "             " + RED + "        " + YELLOW + "             ";
                    case 6, 7 -> YELLOW + "                     " + GREEN + "        " + YELLOW + "     ";
                    default -> YELLOW + "                                  ";
                };
                case "QuestCard(6)" -> switch (row) {
                    case 2, 3, 4, 5 -> YELLOW + "             " + GREEN + "        " + YELLOW + "             ";
                    case 6, 7 -> YELLOW + "     " + PURPLE + "        " + YELLOW + "                     ";
                    default -> YELLOW + "                                  ";
                };
                case "QuestCard(7)" -> switch (row) {
                    case 2, 3 -> YELLOW + "                     " + RED + "        " + YELLOW + "     ";
                    case 4, 5, 6, 7 -> YELLOW + "             " + BLUE + "        " + YELLOW + "             ";
                    default -> YELLOW + "                                  ";
                };
                case "QuestCard(8)" -> switch (row) {
                    case 2, 3 -> YELLOW + "                     " + BLUE + "        " + YELLOW + "     ";
                    case 4, 5, 6, 7 -> YELLOW + "             " + PURPLE + "        " + YELLOW + "             ";
                    default -> YELLOW + "                                  ";
                };
                default -> YELLOW + "                                  ";
            };
        }
    }

    private static String printLeftToRightDiagonalQuestCards(int row, String color) {
        return switch (row) {
            case 2, 3 -> YELLOW + "     " + color + "        " + YELLOW + "                     ";
            case 4, 5 -> YELLOW + "             " + color + "        " + YELLOW + "             ";
            case 6, 7 -> YELLOW + "                     " + color + "        " + YELLOW + "     ";
            default -> YELLOW + "                                  ";
        };
    }
    private static String printRightToLeftDiagonalQuestCards(int row,String color){
        return switch (row) {
            case 2,3 -> YELLOW + "                     " + color + "        " + YELLOW + "     ";
            case 4,5 -> YELLOW + "             " + color + "        " + YELLOW + "             ";
            case 6,7 -> YELLOW + "     " + color + "        " + YELLOW + "                     ";
            default -> YELLOW + "                                  ";
        };
    }
}
