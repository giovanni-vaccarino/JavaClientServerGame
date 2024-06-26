package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.command.Command;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.common.command.ServerCommand;

import java.io.IOException;

/**
 * Used by socket server connection threads to dispatch the commands they receive to the right server object,
 * either the main server or a match server
 */
public class Dispatcher {

    private final VirtualServer server;
    private VirtualMatchServer matchServer;

    public Dispatcher(VirtualServer server) {
        this.server = server;
    }

    public void dispatchCommand(Command<?> command) throws IOException {
        if (command instanceof ServerCommand serverCommand) {
            serverCommand.execute(server);
        } else if (command instanceof MatchServerCommand matchServerCommand) {
            matchServerCommand.execute(matchServer);
        }
    }

    public void setMatchServer(VirtualMatchServer matchServer) {
        this.matchServer = matchServer;
    }
}
