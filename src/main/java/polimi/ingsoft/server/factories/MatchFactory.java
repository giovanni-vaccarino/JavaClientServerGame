package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.PublicBoard;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MatchFactory {
    public static MatchController createMatch(PrintStream out, int matchId) {
        PublicBoard publicBoard = PublicBoardFactory.createPublicBoard();
        return new MatchController(out, matchId, publicBoard);
    }
}
