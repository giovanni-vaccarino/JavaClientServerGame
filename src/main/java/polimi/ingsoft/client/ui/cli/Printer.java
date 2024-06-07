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
     * Prints game's basic argument
     * @param argument a player's input
     */
    public void printFromMain(String argument){
        //System.out.print(MESSAGES.CLS.getValue()); prova per verificare che il cls funzioni
        if (argument.toLowerCase().equals(Arguments.Argument.HELP.getValue())) {
            out.println(MESSAGES.HELPMAIN.getValue());
        }else out.println(MESSAGES.ERROR.getValue());
    }

    /**
     * Prints a PlayerBoard followed by his PlayerHand and PlayerQuest. When argument is not null it alters the board's printing
     * @param board the board that has to be printed
     * @param hand the client's hand
     * @param argument input from player, this string must be equal to a BoardArgument object's value
     * @param playerQuest player's personal QuestCard
     */
    public void printFromBoard(Board board, PlayerHand hand, String argument, QuestCard playerQuest){
        out.print(MESSAGES.CLS.getValue());
        if(argument==null)ClientBoard.printBoard(board);
        else if(argument.equals(BoardArgument.UP.getValue()))ClientBoard.printBoard(board,BoardArgument.UP);
        else if(argument.equals(BoardArgument.DOWN.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWN);
        else if(argument.equals(BoardArgument.LEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.LEFT);
        else if(argument.equals(BoardArgument.RIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.RIGHT);
        else if(argument.equals(BoardArgument.UPRIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.UPRIGHT);
        else if(argument.equals(BoardArgument.UPLEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.UPLEFT);
        else if(argument.equals(BoardArgument.DOWNLEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWNLEFT);
        else if(argument.equals(BoardArgument.DOWNRIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWNRIGHT);
        System.out.print("RESOURCES:"+board.getResources().toString());
        if(hand!=null)ClientHand.print(hand,playerQuest);
        if(argument!=null&&argument.toLowerCase().equals(Arguments.Argument.HELP.getValue()))out.println(MESSAGES.HELPBOARD.getValue());
        else out.println(MESSAGES.ERROR.getValue() );
    }

    /**
     * Prints a PublicBoard followed by player's PlayerHand and PlayerQuest.
     * Helps with a player's card pick choice
     * @param resourceCards the PublicBoard's ResourceCards
     * @param goldCards the PublicBoard's GoldCards
     * @param questCards the PublicBoard's QuestCards
     * @param hand player's hand
     * @param argument player's input
     * @param playerQuest player's personal quest card
     */
    public void printFromPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards, PlayerHand hand, String argument, QuestCard playerQuest){
        out.print(MESSAGES.CLS.getValue());
        ClientPublicBoard.printPublicBoard(resourceCards,goldCards,questCards);
        ClientHand.print(hand,playerQuest);
        /*if(argument.toLowerCase().equals(Arguments.Argument.HELP.getValue()))out.println(MESSAGES.HELPCLIENTBOARD.getValue());
        else */
        if(argument!=null&&argument.toLowerCase().equals(PublicBoardArguments.GETCARD.getValue()))out.println(MESSAGES.HELPGETCARDTYPE.getValue());
        else if(argument!=null&&(argument.toLowerCase().equals(PublicBoardArguments.GOLD.getValue())||
                argument.toLowerCase().equals(PublicBoardArguments.RESOURCE.getValue())))
            out.println(MESSAGES.HELPGETCARDPLACE.getValue());
        else out.println(MESSAGES.ERROR.getValue() );

    }

    /**
     * Prints initial InitialCard's side choice
     * @param initialCard the InitialCard that has to be printed
     */
    public void printInitialCardChoice(InitialCard initialCard){
        out.print(MESSAGES.HELP_INITIAL_CARD_CHOICE.getValue());
        ClientHand.initialPrint(initialCard);
    }

    /**
     * Prints initial QuestCard choice
     * @param quest1 the first QuestCard
     * @param quest2 the second QuestCard
     */
    public void printQuestCardChoice(QuestCard quest1, QuestCard quest2){
        out.print(MESSAGES.HELP_QUEST_CARD_CHOICE.getValue());
        ArrayList<QuestCard> quests=new ArrayList<>();
        quests.add(quest1);
        quests.add(quest2);
        ClientPublicBoard.printInitialQuests(quests);
    }
}
