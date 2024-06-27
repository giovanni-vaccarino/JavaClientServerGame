package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.cli.Printer;
import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class MatchManager implements CLIPhaseManager {
    private transient final Scanner in;
    private transient final PrintStream out;
    private transient final CLI cli;

    enum State {
        NONE, WAITING_FOR_MATCHES, SET_MATCH_ID, WAITING_FOR_JOIN, WAITING_FOR_CREATE
    }

    private State state = State.NONE;

    private List<MatchData> matches;
    private int matchId;

    public MatchManager(Scanner in, PrintStream out, CLI cli) {
        this.in = in;
        this.out = out;
        this.cli = cli;
    }

    public void start() {
        state = State.WAITING_FOR_MATCHES;
        try {
            cli.getServer().getMatches(cli.getNickname());
        } catch (IOException e) {
            parseError(ERROR_MESSAGES.UNABLE_TO_LIST_MATCHES);
        }
    }

    public void updateMatches(List<MatchData> matches) {
        switch (state){
            case State.WAITING_FOR_MATCHES -> {
                this.matches = matches;
                state = State.SET_MATCH_ID;
                new Thread(this::setMatchId).start();
            }

            case State.SET_MATCH_ID -> {
                if(cli.getIsUpdateMatchRequested()){
                    cli.setIsUpdateMatchRequested(false);
                    this.matches = matches;
                    new Thread(this::setMatchId).start();
                }
            }
        }
    }

    private void showMatchesList() {
        if (matches.isEmpty()) out.println(MESSAGES.NO_MATCHES_TO_SHOW.getValue());
        int i = 1;
        for (MatchData match : matches) {
            out.println(i + ". Match number " + match.matchId() + ": " + match.joinedPlayers() + " / " + match.requiredNumPlayers()  + " players in lobby");
            i++;
        }

    }

    private boolean isValidNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers <= 4 && numberOfPlayers >= 2;
    }

    private void setMatchId() {
        String choice;
        if (state == State.SET_MATCH_ID) {
            showMatchesList();

            boolean isValid = false;
            do {
                out.print(MESSAGES.CHOOSE_MATCH.getValue());
                choice = in.nextLine();
                if (choice.equals("update")) {
                    try {
                        cli.setIsUpdateMatchRequested(true);
                        cli.getServer().getMatches(cli.getNickname());
                    } catch (IOException ignore){}

                    return;
                } else {
                    try {
                        matchId = Integer.parseInt(choice);
                    } catch (NumberFormatException exception) {
                        matchId = -1;
                    }
                    if (matchId == 0) isValid = true;
                    else {
                        for (MatchData match : matches) {
                            if (match.matchId().equals(matchId)) {
                                isValid = true;
                                break;
                            }
                        }
                        if (!isValid) out.println(ERROR_MESSAGES.MATCH_NUMBER_OUT_OF_BOUND.getValue());
                    }
                }
            } while (!isValid);
            if (matchId == 0) runCreateMatch();
            else runJoinMatch();
        }
    }

    private void runJoinMatch() {
        if (state == State.SET_MATCH_ID || state == State.WAITING_FOR_CREATE) {
            out.println(MESSAGES.JOINING_MATCH.getValue());
            try {
                cli.getServer().joinMatch(cli.getNickname(), matchId);
                state = State.WAITING_FOR_JOIN;
            } catch (IOException e) {
                parseError(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH);
            }
        }
    }

    private void runCreateMatch() {
        String choice;
        if (state == State.SET_MATCH_ID) {
            Printer.cleanTerminal();
            int requestedNumPlayers;
            boolean isValid = false;

            do {
                out.print(MESSAGES.CHOOSE_NUMBER_OF_PLAYERS.getValue());
                choice=in.nextLine();
                try {
                    requestedNumPlayers = Integer.parseInt(choice);
                }catch(NumberFormatException e){
                    requestedNumPlayers=-1;
                }
                if (isValidNumberOfPlayers(requestedNumPlayers)) {
                    isValid = true;
                } else {
                    out.println(ERROR_MESSAGES.PLAYERS_OUT_OF_BOUND.getValue());
                }
            } while (!isValid);

            try {
                cli.getServer().createMatch(cli.getNickname(), requestedNumPlayers);
                state = State.WAITING_FOR_CREATE;
            } catch (IOException e) {
                parseError(ERROR_MESSAGES.UNABLE_TO_CREATE_MATCH);
            }
        }
    }

    public void updateJoinMatch() {
        if (state == State.WAITING_FOR_JOIN) {
            out.println(MESSAGES.JOINED_MATCH.getValue());
            state = State.NONE;
            cli.returnMatchManager();
        }
    }

    public void updateCreateMatch(int newMatchId) {
        if (state == State.WAITING_FOR_CREATE) {
            out.println(MESSAGES.CREATED_MATCH.getValue());
            matchId = newMatchId;
            runJoinMatch();
        }
    }

    public void parseError(ERROR_MESSAGES error) {
        if (state == State.WAITING_FOR_MATCHES) {
            if (error == ERROR_MESSAGES.UNABLE_TO_LIST_MATCHES) {
                out.println("ERROR: " + error.getValue());
            }
        } else if (state == State.WAITING_FOR_JOIN) {
            if (error == ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH) {
                out.println("ERROR: " + error.getValue());
                state = State.SET_MATCH_ID;
                setMatchId();
            } else if (error == ERROR_MESSAGES.MATCH_IS_ALREADY_FULL) {
                out.println("ERROR: " + error.getValue());
                state = State.SET_MATCH_ID;
                setMatchId();
            } else if (error == ERROR_MESSAGES.MATCH_DOES_NOT_EXIST) {
                out.println("ERROR: " + error.getValue());
                state = State.SET_MATCH_ID;
                setMatchId();
            }
        } else if (state == State.WAITING_FOR_CREATE) {
            if (error == ERROR_MESSAGES.UNABLE_TO_CREATE_MATCH) {
                out.println("ERROR: " + error.getValue());
                state = State.SET_MATCH_ID;
                setMatchId();
            }
        } else {
            out.println("UNEXPECTED ERROR: " + error.getValue());
        }
    }
}
