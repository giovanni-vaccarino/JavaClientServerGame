package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.ClientProxy;
import polimi.ingsoft.server.common.command.ClientCommand;

import java.io.IOException;

public class RmiClientProxy extends ClientProxy {
    private final VirtualView client;

    public RmiClientProxy(VirtualView client) {
        this.client = client;
    }

    @Override
    public void setMatchServer(VirtualMatchServer server) throws IOException {
        client.setMatchServer(server);
    }

    @Override
    public void sendMessage(ClientCommand command) throws IOException {
        client.sendMessage(command);
    }
}
