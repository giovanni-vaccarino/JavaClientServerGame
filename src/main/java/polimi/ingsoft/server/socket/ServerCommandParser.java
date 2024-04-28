package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.socket.SocketCommand;

import java.util.Optional;

public class ServerCommandParser {
    public static Optional<SocketCommand> parse(String command) {
        if (SocketCommand.PING.isEqual(command))
            return Optional.of(SocketCommand.PONG);
        else if (SocketCommand.PONG.isEqual(command))
            return Optional.of(SocketCommand.PING);
        else if (SocketCommand.AVAILABLE_MATCHES.isEqual(command))
            return Optional.of(SocketCommand.MATCHES_LIST);
        else if (SocketCommand.QUIT.isEqual(command))
            return Optional.of(SocketCommand.QUIT);
        return Optional.empty();
    }
}
