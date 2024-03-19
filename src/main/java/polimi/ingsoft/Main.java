package polimi.ingsoft;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    public final static PrintStream console = System.out;
    public final static MainController controller = new MainController();

    private static boolean isLastRound() {
        return false;
    }

    public static void main(String[] args) {
        // ...Initiate new match
        MatchController matchController = controller.createMatch();
        matchController.play();
        controller.endMatch(matchController.getMatchId());
    }
}
class Player {}
abstract class MatchController {
    Player currentPlayer;
    protected abstract Iterator<Player> getPlayers();

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

    private void draw(src.main.java.polimi.ingsoft.DrawableDeck<?> deck) {

    }

    private void place(MixedCard card) {

    }
}

class MainController {
    private final ArrayList<Socket> activeSockets = new ArrayList<>();
    private int nextPort = 8001;
    public MatchController createMatch() throws IOException {
        Socket socket = new Socket("127.0.0.1", getNextPort());
        activeSockets.add(socket);
        Scanner scanner = new Scanner(socket.getInputStream());
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return new MatchController(scanner, printWriter, nextPort);
    }

    public void endMatch(int id) throws MatchNotFoundException, IOException {
        Optional<Socket> sockets = activeSockets.stream().filter(socket -> socket.getPort() == id).findFirst();
        if (sockets.isPresent()) {
            sockets.get().close();
        } else {
            throw new MatchNotFoundException();
        }
    }

    private int getNextPort() {
        return nextPort++;
    }
}

class MatchNotFoundException extends Exception {

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






class Tabellone {
    DrawableDeck<ResourceCard> deck;
    ResourceCard slotA, slotB;

    public ResourceCard getSlotA() {
        ResourceCard result = slotA;
        slotA = deck.pop();
        return result;
    }
}

class TabelloneController {

}