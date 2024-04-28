package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.server.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class SocketClient implements Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public SocketClient(String hostName, int port) throws IOException {
        socket = new Socket(hostName, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!verifyConnection()) throw new IOException();
    }

    private boolean verifyConnection() throws IOException {
        out.println(SocketCommand.PING);
        return SocketCommand.PONG.isEqual(in.readLine());
    }

    @Override
    public HashMap<Integer, String> getAvailableMatches() {
        out.println(SocketCommand.AVAILABLE_MATCHES);
        return null;
    }

    @Override
    public Player createMatch(String nickname) {
        return null;
    }

    @Override
    public Player joinMatch(int matchId, String nickname) {
        return null;
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
