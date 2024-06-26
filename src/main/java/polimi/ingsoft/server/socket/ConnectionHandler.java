package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.common.ClientProxy;
import polimi.ingsoft.server.common.command.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final ClientProxy proxy;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Dispatcher dispatcher;

    private final PrintStream logger;

    public ConnectionHandler(Socket socket, Dispatcher dispatcher, PrintStream logger) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.proxy = new SocketClientProxy(out, dispatcher);
        this.logger = logger;
        this.dispatcher = dispatcher;
    }

    public ClientProxy getProxy() {
        return proxy;
    }

    @Override
    public void run() {
        try {
            Command<?> command;

            while ((command = (Command<?>) in.readObject()) != null) {
                try {
                    dispatcher.dispatchCommand(command);
                } catch (NullPointerException e) {
                    logger.print("SOCKET: NullPointerException raised ");
                    e.printStackTrace(logger);
                }
            }

            // Shutdown socket
            in.close();
            out.close();
            socket.close();
        } catch (EOFException | SocketException e) {

            logger.println("SOCKET: Connection closed");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
