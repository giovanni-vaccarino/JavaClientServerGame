package polimi.ingsoft;

import polimi.ingsoft.exceptions.MatchNotFoundException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public final static PrintStream console = System.out;
    public final static MainController controller = new MainController();

    private static boolean isLastRound() {
        return false;
    }

    public static void main(String[] args) throws IOException, MatchNotFoundException {
        // ...Initiate new match
        MatchController matchController = controller.createMatch();
        matchController.play();
        controller.endMatch(matchController.getMatchId());
    }
}

