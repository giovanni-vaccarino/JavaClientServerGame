package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.common.ClientProxy;
import polimi.ingsoft.server.common.Dispatcher;
import polimi.ingsoft.server.common.command.ClientCommand;
import polimi.ingsoft.server.common.VirtualMatchServer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SocketClientProxy extends ClientProxy {
    private final ObjectOutputStream out;
    private final Dispatcher dispatcher;

    public SocketClientProxy(ObjectOutputStream out, Dispatcher dispatcher) {
        this.out = out;
        this.dispatcher = dispatcher;
    }

    @Override
    public void setMatchServer(VirtualMatchServer matchServer) {
        dispatcher.setMatchServer(matchServer);
    }

    @Override
    public void sendMessage(ClientCommand command) throws IOException {
        out.writeObject(command);
        out.flush();
    }
}
