package polimi.ingsoft;

import polimi.ingsoft.exceptions.MatchNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

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
