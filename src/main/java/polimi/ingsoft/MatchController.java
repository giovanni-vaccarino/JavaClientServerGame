package polimi.ingsoft;

import polimi.ingsoft.model.MixedCard;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

public class MatchController {
    Player currentPlayer;

    protected Iterator<Player> getPlayers(){
        return null;
    }

    protected final Scanner in;
    protected final PrintWriter out;

    protected final int matchId;

    public MatchController(Scanner in, PrintWriter out, int matchId) {
        this.in = in;
        this.out = out;
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }

    private void playTurn() {
        Iterator<Player> players = getPlayers();
        while (players.hasNext()) {
            currentPlayer = players.next();

        }
    }

    public void play() {
        boolean lastRound = false;

        do {
            if (isLastRound()) lastRound = true;
            playTurn();
        } while (!lastRound);
    }

    private boolean isLastRound() {
        return false;
    }

    private void draw() {

    }

    private void place(MixedCard card) {

    }
}
