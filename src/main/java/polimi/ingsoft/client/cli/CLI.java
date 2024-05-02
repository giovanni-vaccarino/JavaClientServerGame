package polimi.ingsoft.client.cli;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ERROR_MESSAGES;
import polimi.ingsoft.client.VirtualServer;
import polimi.ingsoft.server.Player;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Scanner in;
    private final PrintStream out;
    private final VirtualServer virtualServer;
    private final Client client;
    public CLI(Scanner in, PrintStream out, VirtualServer virtualServer, Client client) {
        this.virtualServer = virtualServer;
        this.in = in;
        this.out = out;
        this.client = client;
    }

    public Boolean runJoinMatchRoutine() {
        Player result;
        Boolean resultBool;

        do {
            int matchId = this.showWelcomeScreen();
            String nickname = this.showChoosePlayerNicknameScreen(matchId);

            //if (matchId == VirtualServer.NEW_MATCH_ID){
            //    matchId = virtualServer.createNewMatch();
            //}

            // CREATING A NEW MATCH IF IT'S NEW MATCH CASE
            if (matchId == 0){
                Integer requestedNumPlayers = this.showChooseNumberPlayersScreen();
                matchId = client.createMatch(requestedNumPlayers);
            }

            resultBool = this.joinMatch(nickname, matchId);
        } while (resultBool == null);

        return resultBool;
    }

    private int showWelcomeScreen() {
        //String[] matches = virtualServer.getAvailableMatches();
        List<Integer> matches = client.getAvailableMatches();
        int i = 0;

        out.println(MESSAGES.WELCOME.getValue());
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
            if (matchId < matches.size()){
                isValid = true;
            }else{
                out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);

        return matchId;
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
                if (virtualServer.isAvailableNickname(nickname, matchId)){
                    isValid = true;
                }
                else {
                    out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
                }
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
                out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
            }
        } while (!isValid);

        return requestedNumPlayers;
    }

    private Boolean joinMatch(String nickname, int matchId) {
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

        return client.joinMatch(matchId, nickname);
    }
}
