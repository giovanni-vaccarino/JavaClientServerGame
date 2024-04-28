package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.socket.SocketCommand;
import polimi.ingsoft.server.controller.MainController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final MainController controller;

    public ConnectionHandler(Socket socket, MainController controller) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            Optional<SocketCommand> response;

            while (true) {
                response = ServerCommandParser.parse(scanner.nextLine());
                if (response.isPresent()) {
                    printWriter.println(response);
                    if (response.get() == SocketCommand.QUIT)
                        break;
                } else {
                    printWriter.println(SocketCommand.UNKNOWN);
                }
            }

            // Shutdown socket
            scanner.close();
            printWriter.close();
            socket.close();
        } catch (IOException ignored) { }
    }
}
