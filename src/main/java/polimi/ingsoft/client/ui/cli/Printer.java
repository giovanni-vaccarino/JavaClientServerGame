package polimi.ingsoft.client.ui.cli;

import java.io.PrintStream;

public class Printer {
    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }
    public void printFromMain(String argument){
        if (argument.toLowerCase().equals(Arguments.Argument.HELP.getValue())) {
            out.println(MESSAGES.HELPMAIN.getValue());
        }else out.println(MESSAGES.ERROR.getValue());
    }
    public void printFromBoard(ClientBoard board, ClientHand hand, String argument){
        out.print(MESSAGES.CLS.getValue());
        if(argument.equals(BoardArgument.UP.getValue()))board.printBoard(BoardArgument.UP);
        else if(argument.equals(BoardArgument.DOWN.getValue()))board.printBoard(BoardArgument.DOWN);
        else if(argument.equals(BoardArgument.LEFT.getValue()))board.printBoard(BoardArgument.LEFT);
        else if(argument.equals(BoardArgument.RIGHT.getValue()))board.printBoard(BoardArgument.RIGHT);
        else if(argument.equals(BoardArgument.UPRIGHT.getValue()))board.printBoard(BoardArgument.UPRIGHT);
        else if(argument.equals(BoardArgument.UPLEFT.getValue()))board.printBoard(BoardArgument.UPLEFT);
        else if(argument.equals(BoardArgument.DOWNLEFT.getValue()))board.printBoard(BoardArgument.DOWNLEFT);
        else if(argument.equals(BoardArgument.DOWNRIGHT.getValue()))board.printBoard(BoardArgument.DOWNRIGHT);
        else board.printBoard();
        if(hand!=null)hand.print();
        if(argument.toLowerCase().equals(Arguments.Argument.HELP.getValue()))out.println(MESSAGES.HELPBOARD.getValue());
        if(argument.toLowerCase().equals(BoardArgument.PLAYCARD.getValue()))out.println(MESSAGES.PLAYCARDHELP.getValue()+"("+board.getActualCoordinates().getX()+","+board.getActualCoordinates().getY()+")");
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
}
