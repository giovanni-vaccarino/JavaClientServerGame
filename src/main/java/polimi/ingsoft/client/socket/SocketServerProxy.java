package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.client.common.ServerProxy;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SocketServerProxy extends ServerProxy {
    private final ObjectOutputStream out;

    public SocketServerProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void sendMessage(ServerCommand command) throws IOException {
        out.writeObject(command);
        out.flush();
    }
}
