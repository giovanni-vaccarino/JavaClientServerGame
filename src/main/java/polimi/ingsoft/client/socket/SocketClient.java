package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.server.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

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
    public List<Integer> getAvailableMatches() {
        out.println(SocketCommand.AVAILABLE_MATCHES);
        return null;
    }

    @Override
    public Integer createMatch(Integer requestedNumPlayers) {
        return null;
    }

    @Override
    public Boolean joinMatch(int matchId, String nickname) {
        return true;
    }

    @Override
    public Boolean isMatchJoinable(int matchId) {
        return true;
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
