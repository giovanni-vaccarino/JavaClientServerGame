package src.main.java.polimi.ingsoft;

import java.io.PrintStream;
import java.util.Iterator;

public class Main {
    public final static PrintStream console = System.out;
    public final static MainController controller = new MainController();

    private static boolean isLastRound() {
        return false;
    }

    public static void main(String[] args) {
        // ...Initiate new match
        MatchController matchController = controller.createMatch();
        boolean lastRound = false;

        do {
            if (isLastRound()) {
                lastRound = true;
            }
            // Play
            matchController.playTurn();
        } while (!lastRound);
    }
}

class Player {}
abstract class MatchController {
    Player currentPlayer;
    TabelloneController tabelloneController;
    protected abstract Iterator<Player> getPlayers();
    private Player getCurrentPlayer() {
        return new Player();
    }

    public void playTurn() {
        Iterator<Player> players = getPlayers();
        while (players.hasNext()) {
            currentPlayer = players.next();
            // Gioca carta
            // Pesca
        }
    }
}

class MainController {
    public MatchController createMatch() {
        return new MatchController();
    }
}


public int mandaRichiesta(posizione) {
    clientsocket(sdaasdas, posizione)
}

metodorichiestro-parametri-mittente
        do
getMessaggi(string messaggio)(graffa)
while parametro!=posizionegiocabile

do


    while parametro!= posizionepescabile







class TabelloneController {

}