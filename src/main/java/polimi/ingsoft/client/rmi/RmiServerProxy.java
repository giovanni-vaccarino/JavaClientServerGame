package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.client.common.ServerProxy;
import polimi.ingsoft.server.common.VirtualServer;

import java.io.IOException;

public class RmiServerProxy extends ServerProxy {
    private final VirtualServer server;

    public RmiServerProxy(VirtualServer server) {
        this.server = server;
    }

    @Override
    public void sendMessage(ServerCommand command) throws IOException {
        server.sendMessage(command);
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        server.connect(client);
    }
}
