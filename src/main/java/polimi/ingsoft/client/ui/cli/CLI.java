package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class CLI extends UI {
    enum CurrentPage {
        WELCOME, CHOOSE_MATCH, LOBBY, GAME;
    }
    private CurrentPage currentPage = CurrentPage.WELCOME;
    private final Scanner in;
    private final PrintStream out;
    private final Client client;
    public CLI(Scanner in, PrintStream out, Client client) throws RemoteException {
        this.in = in;
        this.out = out;
        this.client = client;
        client.run();
    }

    public Boolean runJoinMatchRoutine() throws IOException {
        Player result;
        Boolean resultBool;

        do {
            this.showWelcomeScreen();
            int matchId = 0;
            String nickname = this.showChoosePlayerNicknameScreen(matchId);

            //if (matchId == VirtualServer.NEW_MATCH_ID){
            //    matchId = virtualServer.createNewMatch();
            //}

            // CREATING A NEW MATCH IF IT'S NEW MATCH CASE
            if (matchId == 0){
                Integer requestedNumPlayers = this.showChooseNumberPlayersScreen();
                client.createMatch(requestedNumPlayers);
            }

            resultBool = this.joinMatch(nickname, matchId);
        } while (resultBool == null);

        return resultBool;
    }

    public void showWelcomeScreen() throws IOException {
        if (currentPage == CurrentPage.WELCOME) {
            client.getMatches(this.client);
            currentPage = CurrentPage.CHOOSE_MATCH;
        }
    }

    // TODO remove exceptions
    public void showMatchesList(List<Integer> matches) throws IOException {
        if (currentPage == CurrentPage.CHOOSE_MATCH) {
            int i = 0;
            out.println("0. CREATE A NEW MATCH");

            for (Integer match : matches) {
                out.printf("%d. %d%n", ++i, match);
            }

            int matchId;
            boolean isValid = false;
            do {
                out.print(MESSAGES.CHOOSE_MATCH.getValue());
                matchId = in.nextInt();
                in.nextLine();
                if (matchId <= matches.size()) {
                    isValid = true;
                } else {
                    out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
                }
            } while (!isValid);

            String nickname = this.showChoosePlayerNicknameScreen(matchId);
            if (matchId == 0) {
                Integer requestedNumPlayers = this.showChooseNumberPlayersScreen();
                client.createMatch(requestedNumPlayers);
                //TODO get the correct matchId
                matchId = matches.size() + 1;
            }

            //TODO discuss changing
            client.clientJoinMatch(matchId, nickname);
            currentPage = CurrentPage.LOBBY;
        }
    }

    private String showChoosePlayerNicknameScreen(int matchId) {
        String nickname;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_NICKNAME.getValue());
            nickname = in.nextLine();
            if(matchId == 0){// NEW MATCH CASE
                isValid = true;
            }else{
//                if (client.isAvailableNickname(nickname, matchId)){
//                    isValid = true;
//                }
//                else {
//                    out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
//                }
                isValid = true;
            }
        } while (!isValid);

        return nickname;
    }

    private Integer showChooseNumberPlayersScreen() {
        int requestedNumPlayers;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_NUMPLAYERS.getValue());
            requestedNumPlayers = in.nextInt();

            if (requestedNumPlayers <= 4 && requestedNumPlayers >= 2){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);

        return requestedNumPlayers;
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

        client.joinMatch(matchId, nickname);

        //TODO Edit fixed return value
        return true;
    }
}
