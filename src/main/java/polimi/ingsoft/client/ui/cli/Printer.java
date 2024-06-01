package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.model.Board;
import polimi.ingsoft.server.model.InitialCard;
import polimi.ingsoft.server.model.QuestCard;

import java.io.PrintStream;

public class Printer {
    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }
    public void printFromMain(String argument){
        //System.out.print(MESSAGES.CLS.getValue()); prova per verificare che il cls funzioni
        if (argument.toLowerCase().equals(Arguments.Argument.HELP.getValue())) {
            out.println(MESSAGES.HELPMAIN.getValue());
        }else out.println(MESSAGES.ERROR.getValue());
    }
    public void printFromBoard(Board board, ClientHand hand, String argument){
        out.print(MESSAGES.CLS.getValue());
        if(argument.equals(BoardArgument.UP.getValue()))ClientBoard.printBoard(board,BoardArgument.UP,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.DOWN.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWN,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.LEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.LEFT,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.RIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.RIGHT,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.UPRIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.UPRIGHT,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.UPLEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.UPLEFT,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.DOWNLEFT.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWNLEFT,board.getPrintingCoordinates());
        else if(argument.equals(BoardArgument.DOWNRIGHT.getValue()))ClientBoard.printBoard(board,BoardArgument.DOWNRIGHT,board.getPrintingCoordinates());
        else ClientBoard.printBoard(board,board.getPrintingCoordinates());
        if(hand!=null)hand.print();
        if(argument.toLowerCase().equals(Arguments.Argument.HELP.getValue()))out.println(MESSAGES.HELPBOARD.getValue());
        if(argument.toLowerCase().equals(BoardArgument.PLAYCARD.getValue()))out.println(MESSAGES.PLAYCARDHELP.getValue()+"("+board.getPrintingCoordinates().getX()+","+board.getPrintingCoordinates().getY()+")");
        else out.println(MESSAGES.ERROR.getValue() );
    }
    public void printFromPublicBoard(ClientPublicBoard board,ClientHand hand,String argument){
        out.print(MESSAGES.CLS.getValue());
        board.printClientPublicBoard();
        hand.print();
        if(argument.toLowerCase().equals(Arguments.Argument.HELP.getValue()))out.println(MESSAGES.HELPCLIENTBOARD.getValue());
        else if(argument.toLowerCase().equals(PublicBoardArguments.GETCARD.getValue()))out.println(MESSAGES.HELPGETCARDTYPE.getValue());
        else if(argument.toLowerCase().equals(PublicBoardArguments.GOLD.getValue())||
                argument.toLowerCase().equals(PublicBoardArguments.RESOURCE.getValue()))
            out.println(MESSAGES.HELPGETCARDPLACE.getValue());
        else out.println(MESSAGES.ERROR.getValue() );
    }
    public void printInitialCardChoice(InitialCard initialCard){
        out.print(MESSAGES.HELP_INITIAL_CARD_CHOICE.getValue());
        ClientHand hand=new ClientHand();
        hand.initialPrint(initialCard);
        hand=null;
    }
    public void printQuestCardChoice(QuestCard quest1, QuestCard quest2){
        out.print(MESSAGES.HELP_QUEST_CARD_CHOICE.getValue());
        ClientPublicBoard temp=new ClientPublicBoard(quest1,quest2,null,null,null,null,null,null);
        temp.printInitialQuests();

    }
}
