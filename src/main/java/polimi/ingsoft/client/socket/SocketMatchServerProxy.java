package polimi.ingsoft.client.socket;

import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.client.common.MatchServerProxy;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SocketMatchServerProxy extends MatchServerProxy {
    private final ObjectOutputStream out;

    public SocketMatchServerProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void sendMessage(MatchServerCommand command) throws IOException {
        out.writeObject(command);
        out.flush();
    }
}
