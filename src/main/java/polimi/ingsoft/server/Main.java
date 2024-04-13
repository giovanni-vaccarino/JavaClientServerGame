package polimi.ingsoft.server;

import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.exceptions.MatchNotFoundException;

import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public final static MainController controller = new MainController();

    public static void main(String[] args) throws IOException, MatchNotFoundException {
        // ...Initiate new match request
        MatchController matchController = controller.createMatch();
        matchController.play();
        controller.endMatch(matchController.getMatchId());
    }
}

