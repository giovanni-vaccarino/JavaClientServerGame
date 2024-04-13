package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.exceptions.MatchNotFoundException;
import polimi.ingsoft.server.factories.MatchFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class MainController {
    // Cambiare in modo che sia in grado di tenere mapping socket - partita
    private final ArrayList<Socket> activeSockets = new ArrayList<>();
    private final int BASE_PORT = 8000;
    private int nextPort = BASE_PORT + 1;

    public MatchController createMatch() throws IOException {
        Socket socket = new Socket("127.0.0.1", getNextPort());
        activeSockets.add(socket);
        Scanner scanner = new Scanner(socket.getInputStream());
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return MatchFactory.createMatch(scanner, printWriter, nextPort - BASE_PORT);
    }

    public MatchController getMatch(int id) throws MatchNotFoundException {
        return null;
    }

    public void endMatch(int id) throws MatchNotFoundException, IOException {
        Optional<Socket> sockets = activeSockets.stream().filter(socket -> socket.getPort() == id).findFirst();
        if (sockets.isPresent()) {
            sockets.get().close();
        } else {
            throw new MatchNotFoundException();
        }
    }

    public void addPlayerToMatch(MatchController matchController, Player player) {
        matchController.addPlayer(player);
    }

    private int getNextPort() {
        return nextPort++;
    }
}
