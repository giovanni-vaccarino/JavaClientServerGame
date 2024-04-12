package polimi.ingsoft.client;

import polimi.ingsoft.server.Player;

import java.util.concurrent.TimeUnit;

public class VirtualServer {
    public static int NEW_MATCH_ID = 0;
    public String[] getAvailableMatches() {
        return new String[]{ "Match A", "Match B", "Match C" };
    }

    public boolean isAvailableNickname(String nickname, int matchId) {
        return true;
    }

    public int createNewMatch() {
        // Crea nuova partita e ritorna matchId
        return 10;
    }

    public Player joinMatch(String nickname, int matchId) {
        // Simula l'attesa nella lobby (?)
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            return null;
        }

        if (isAvailableNickname(nickname, matchId)) {
            return new Player(board, hand);
        } else {
            return null;
        }
    }
}
