package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class MatchManager {
    private final Scanner in;
    private final PrintStream out;
    private final CLI cli;
    private List<Integer> matchIds;

    public MatchManager(Scanner in, PrintStream out, CLI cli) {
        this.in = in;
        this.out = out;
        this.cli = cli;
    }

    private boolean isValidNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers <= 4 && numberOfPlayers >= 2;
    }

    public void runCreateMatch() {
        int requestedNumPlayers;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_NUMPLAYERS.getValue());
            requestedNumPlayers = in.nextInt();
            in.nextLine();

            if (isValidNumberOfPlayers(requestedNumPlayers)){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);
        cli.createMatch(requestedNumPlayers);
    }

    public void runJoinMatch() {
        int matchId;
        boolean isValid = false;
        do {
            out.print(MESSAGES.CHOOSE_MATCH.getValue());
            matchId = in.nextInt();
            in.nextLine();
            if (matchIds.contains(matchId)) {
                isValid = true;
            } else {
                out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);
        cli.joinMatch(matchId);
    }

    public void runMatchRoutine() {
        int matchId;
        boolean isValid = false;
        do {
            out.print(MESSAGES.CHOOSE_MATCH.getValue());
            matchId = in.nextInt();
            in.nextLine();
            if (matchIds.contains(matchId) || matchId == 0) {
                isValid = true;
            } else {
                out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);

        if (matchId == 0) this.runCreateMatch();
        else cli.joinMatch(matchId);
    }

    public void showMatchesList() {
        if (matchIds.isEmpty()) out.println(MESSAGES.NO_MATCHES_TO_SHOW.getValue());
        int i = 0;
        for (Integer match : matchIds)
            out.printf("%d. Match number %d%n", ++i, match);
    }

    public void updateMatchesList(List<Integer> matches) {
        this.matchIds = matches;
    }
    public void updateMatchesList(Integer matchId) {
        this.matchIds.add(matchId);
    }
}
