package polimi.ingsoft.server.common.command;

import polimi.ingsoft.client.common.VirtualView;

import java.io.IOException;
import java.io.Serializable;

public interface ClientCommand extends Serializable {
    void execute(VirtualView client) throws IOException;
}
