package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Class that manages some of the game's interfaces and game-player communication
 */
public class Printer {
    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }


    /**
     * Prints a PlayerBoard followed by his PlayerHand and PlayerQuest. When argument is not null it alters the board's printing
     *
     * @param board            the board that has to be printed
     * @param hand             the client's hand
     * @param boardArgument    input from player
     * @param playerQuest      player's personal QuestCard
     */
    public void printFromBoard(Board board, PlayerHand hand, BoardArgument boardArgument, QuestCard playerQuest) {
        out.print(MESSAGES.CLS.getValue());
        System.out.flush();
        ClientBoard.printBoard(board, boardArgument);
        out.println("RESOURCES: " + board.getResources().toString());
        out.println("YOUR CARDS: ");
        ClientHand.print(hand, playerQuest);
    }

    /**
     * Prints a PublicBoard followed by player's PlayerHand and PlayerQuest.
     * Helps with a player's card pick choice
     *
     * @param resourceCards the PublicBoard's ResourceCards
     * @param goldCards     the PublicBoard's GoldCards
     * @param questCards    the PublicBoard's QuestCards
     * @param hand          player's hand
     * @param argument      player's input
     * @param playerQuest   player's personal quest card
     */
    public void printFromPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards, PlayerHand hand, String argument, QuestCard playerQuest) {
        out.print(MESSAGES.CLS.getValue());
        System.out.flush();
        ClientPublicBoard.printPublicBoard(resourceCards, goldCards, questCards);
        out.println("YOUR CARDS: ");
        ClientHand.print(hand, playerQuest);
        if (argument != null && argument.toLowerCase().equals(PublicBoardArguments.GETCARD.getValue()))
            out.println(MESSAGES.HELP_GET_CARD_TYPE.getValue());
        else if (argument != null && (argument.toLowerCase().equals(PublicBoardArguments.GOLD.getValue()) ||
                argument.toLowerCase().equals(PublicBoardArguments.RESOURCE.getValue())))
            out.println(MESSAGES.HELP_GET_CARD_PLACE.getValue());
        else out.println(MESSAGES.ERROR.getValue());

    }

    /**
     * Prints initial InitialCard's side choice
     *
     * @param initialCard the InitialCard that has to be printed
     */
    public void printInitialCardChoice(InitialCard initialCard) {
        out.print(MESSAGES.CLS.getValue());
        System.out.flush();
        out.print(MESSAGES.HELP_INITIAL_CARD_CHOICE.getValue());
        ClientHand.initialPrint(initialCard);
    }

    /**
     * Prints initial QuestCard choice
     *
     * @param quest1 the first QuestCard
     * @param quest2 the second QuestCard
     */
    public void printQuestCardChoice(QuestCard quest1, QuestCard quest2) {
        out.print(MESSAGES.CLS.getValue());
        System.out.flush();
        out.print(MESSAGES.HELP_QUEST_CARD_CHOICE.getValue());
        ArrayList<QuestCard> quests = new ArrayList<>();
        quests.add(quest1);
        quests.add(quest2);
        ClientPublicBoard.printInitialQuests(quests);
    }
}
