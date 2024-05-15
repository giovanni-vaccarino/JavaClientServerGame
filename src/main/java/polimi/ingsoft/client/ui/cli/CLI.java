package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.controller.MatchController;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class CLI extends UI {
    enum CLICommand {
        exit, unknown_command, list_matches, create_match, join_match
    }
    private final Scanner in;
    private final PrintStream out;
    private final Client client;
    private List<Integer> matches;
    private MatchController match;
    private String nickname;

    public CLI(Scanner in, PrintStream out, Client client) {
        this.in = in;
        this.out = out;
        this.client = client;
    }

    private void run() {
        new Thread(() -> {
            CLICommand command = CLICommand.unknown_command;
            do {
                try {
                    command = CLICommand.valueOf(in.nextLine());
                    switch (command) {
                        case list_matches -> showMatchesList();
                        case create_match -> runCreateMatch();
                        case join_match -> runJoinMatch();
                    }
                } catch (IllegalArgumentException e) {
                    out.println(ERROR_MESSAGES.UNKNOWN_COMMAND.getValue());
                }
            } while (command != CLICommand.exit);
        }).start();
    }

    private void showMatchesList() {
        int i = 0;
        for (Integer match : matches)
            out.printf("%d. Match number %d%n", ++i, match);
    }

    public void updateNickname(boolean result) {
        if (result) {
            out.println(MESSAGES.NICKNAME_UPDATED.getValue());
            this.run();
        }else {
            out.println(MESSAGES.UNABLE_TO_UPDATE_NICKNAME.getValue());
            runSetNickname();
        }
    }

    private void runCreateMatch() {
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
                out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
            }
        } while (!isValid);

        try {
            client.createMatch(requestedNumPlayers);
        } catch (IOException e) {
            out.println(ERROR_MESSAGES.UNABLE_TO_CREATE_MATCH.getValue());
        }
    }

    private boolean isValidNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers <= 4 && numberOfPlayers >= 2;
    }

    private void runJoinMatch() {
        int matchId;
        boolean isValid = false;
        do {
            out.print(MESSAGES.CHOOSE_MATCH.getValue());
            matchId = in.nextInt();
            in.nextLine();
            if (matches.contains(matchId)) {
                isValid = true;
            } else {
                out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);
        joinMatch(matchId);
    }

    public void showWelcomeScreen() throws IOException {
        out.println(MESSAGES.WELCOME.getValue());
        client.getMatches(this.client);
        runSetNickname();
    }

    public void runSetNickname() {
        new Thread(() -> {
            try {
                String candidateNickname;
                out.print(MESSAGES.CHOOSE_NICKNAME.getValue());
                candidateNickname = in.nextLine();

                nickname = candidateNickname;
                client.setNickname(nickname);
            } catch (IOException e) { }
        }).start();
    }

    public void updateMatchesList(List<Integer> matches) {
        this.matches = matches;
    }

    @Override
    public void showMatchCreate(MatchController match) {
        this.match = match;
        this.matches.add(match.getMatchId());
        out.println(MESSAGES.CREATED_MATCH.getValue());
        joinMatch(match.getMatchId());
    }

    private void joinMatch(Integer matchId) {
        out.println(MESSAGES.JOINING_MATCH.getValue());
        try {
            client.joinMatch(client, matchId, nickname);
        } catch (IOException e) {
            out.println(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH.getValue());
        }
    }
}
