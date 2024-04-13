package polimi.ingsoft.client.cli;

import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.VirtualServer;
import polimi.ingsoft.server.Player;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI {
    private final Scanner in;
    private final PrintStream out;
    private final VirtualServer virtualServer;
    public CLI(Scanner in, PrintStream out, VirtualServer virtualServer) {
        this.virtualServer = virtualServer;
        this.in = in;
        this.out = out;
    }

    public Player runJoinMatchRoutine() {
        Player result;

        do {
            int matchId = this.showWelcomeScreen();
            String nickname = this.showChoosePlayerNicknameScreen(matchId);
            if (matchId == VirtualServer.NEW_MATCH_ID) matchId = virtualServer.createNewMatch();
            result = this.joinMatch(nickname, matchId);
        } while (result == null);

        return result;
    }

    private int showWelcomeScreen() {
        String[] matches = virtualServer.getAvailableMatches();
        int i = 0;

        out.println(MESSAGES.WELCOME.getValue());
        out.println("0. CREATE A NEW MATCH");
        for (String match : matches) {
            out.printf("{}. {}%n", ++i, match);
        }

        int matchId;
        boolean isValid = false;
        do {
            out.print(MESSAGES.CHOOSE_MATCH.getValue());
            matchId = in.nextInt();
            if (matchId < matches.length) isValid = true;
            else out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
        } while (!isValid);

        return matchId;
    }

    private String showChoosePlayerNicknameScreen(int matchId) {
        String nickname;
        boolean isValid = false;

        do {
            out.println(MESSAGES.CHOOSE_NICKNAME.getValue());
            nickname = in.nextLine();
            if (virtualServer.isAvailableNickname(nickname, matchId)) isValid = true;
            else out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
        } while (!isValid);

        return nickname;
    }

    private Player joinMatch(String nickname, int matchId) {
        out.println(MESSAGES.JOINING_MATCH.getValue());
        Player result = virtualServer.joinMatch(nickname, matchId);
        if (result != null) {
            out.println(MESSAGES.JOINED_MATCH.getValue());
            return result;
        } else {
            out.println(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH.getValue());
            return null;
        }
    }
}
