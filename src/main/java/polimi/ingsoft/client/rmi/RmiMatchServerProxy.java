package polimi.ingsoft.client.rmi;

import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.client.common.MatchServerProxy;
import polimi.ingsoft.server.common.VirtualMatchServer;

import java.io.IOException;

public class RmiMatchServerProxy extends MatchServerProxy {
    private final VirtualMatchServer server;

    public RmiMatchServerProxy(VirtualMatchServer server) {
        this.server = server;
    }

    @Override
    public void sendMessage(MatchServerCommand command) throws IOException {
        server.sendMessage(command);
    }
}
