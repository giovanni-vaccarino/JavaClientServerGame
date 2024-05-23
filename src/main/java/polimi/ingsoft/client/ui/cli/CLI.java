package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CLI extends UI {
    enum CLICommand {
        exit,
        unknown_command,
        list_matches,
        create_match,
        join_match,
        print_board,
        place_card
    }
    private final Scanner in;
    private final PrintStream out;
    private final Client client;
    private List<Integer> matchIds;
    private Integer matchId;
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
        for (Integer match : matchIds)
            out.printf("%d. Match number %d%n", ++i, match);
    }

    public void updateNickname() {
        out.println(MESSAGES.NICKNAME_UPDATED.getValue());
        this.run();
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
                out.println(ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);

        try {
            client.createMatch(nickname, requestedNumPlayers);
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
            if (matchIds.contains(matchId)) {
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
            } catch (IOException ignored) { }
        }).start();
    }

    public void updateMatchesList(List<Integer> matches) {
        this.matchIds = matches;
    }

    @Override
    public void showUpdateMatchJoin() {
        out.println(MESSAGES.JOINED_MATCH.getValue());
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {
        out.println(MESSAGES.PLAYERS_IN_LOBBY.getValue());
        for (var nickname : nicknames)
            out.println(nickname);
    }

    @Override
    public void showMatchCreate(Integer matchId) {
        this.matchId = matchId;
        this.matchIds.add(matchId);
        out.println(MESSAGES.CREATED_MATCH.getValue());
        joinMatch(matchId);
    }

    private void joinMatch(Integer matchId) {
        out.println(MESSAGES.JOINING_MATCH.getValue());
        try {
            client.joinMatch(nickname, matchId);
        } catch (IOException e) {
            out.println(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH.getValue());
        }
    }
}
