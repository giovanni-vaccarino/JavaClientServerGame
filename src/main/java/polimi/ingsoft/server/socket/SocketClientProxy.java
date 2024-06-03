package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.common.ClientProxy;
import polimi.ingsoft.server.common.command.ClientCommand;
import polimi.ingsoft.server.common.VirtualMatchServer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SocketClientProxy extends ClientProxy {
    private final ObjectOutputStream out;

    public SocketClientProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void setMatchServer(VirtualMatchServer matchServer) {
        // TODO could add client as a constructor arg and forward client.setMatchServer here
        //  to bypass ConnectionHandler proxying
    }

    @Override
    public void sendMessage(ClientCommand command) throws IOException {
        out.writeObject(command);
        out.flush();
    }
}
