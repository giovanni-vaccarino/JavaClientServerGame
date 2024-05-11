package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class CLI extends UI {
    enum CurrentPage {
        WELCOME, CHOOSE_MATCH, INPUT_PLAYER_ATTRIBUTES, LOBBY, GAME;
    }
    private CurrentPage currentPage = CurrentPage.WELCOME;
    private final SafeScanner in;
    private final PrintStream out;
    private final Client client;
    public CLI(Scanner in, PrintStream out, Client client) throws RemoteException {
        this.in = new SafeScanner(in, out);
        this.out = out;
        this.client = client;
        client.run();
    }

    public void showWelcomeScreen() throws IOException {
        if (currentPage == CurrentPage.WELCOME) {
            client.getMatches(this.client);
            currentPage = CurrentPage.CHOOSE_MATCH;
        }
    }

    // TODO remove exceptions
    public void showMatchesList(List<Integer> matches) {
        if (currentPage == CurrentPage.CHOOSE_MATCH) {
            int i = 0;
            out.println("0. CREATE A NEW MATCH");

            for (Integer match : matches) {
                out.printf("%d. %d%n", ++i, match);
            }

            in.readInt(
                    MESSAGES.CHOOSE_MATCH.getValue(),
                    ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue(),
                    integer -> integer <= matches.size(),
                    integer -> {
                        currentPage = CurrentPage.INPUT_PLAYER_ATTRIBUTES;
                        int matchId = integer;
                        in.readLine(MESSAGES.CHOOSE_NICKNAME.getValue(), nickname -> {
                            if (matchId == 0) {
                                in.readInt(
                                        MESSAGES.CHOOSE_NUMPLAYERS.getValue(),
                                        ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue(),
                                        requestedNumPlayers -> requestedNumPlayers <= 4 && requestedNumPlayers >= 2,
                                        requestedNumPlayers -> {
                                            try {
                                                client.createMatch(requestedNumPlayers);
                                            } catch (IOException ignored) { }
                                            currentPage = CurrentPage.LOBBY;
                                        }
                                );
                            } else {
                                currentPage = CurrentPage.LOBBY;
                            }
                        });
                    }
            );
        }
    }

    private Boolean joinMatch(String nickname, int matchId) throws IOException {
        out.println(MESSAGES.JOINING_MATCH.getValue());
        /*
        Player result = virtualServer.joinMatch(nickname, matchId);
        if (result != null) {
            out.println(MESSAGES.JOINED_MATCH.getValue());
            return result;
        } else {
            out.println(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH.getValue());
            return null;
        }
        */
        // THE CHECKS ARE ALREADY SERVER SIDE -> IMPLEMENTING ALSO CLIENT VERIFICATION?

        //client.joinMatch(matchId, nickname);

        //TODO Edit fixed return value
        return true;
    }

}
