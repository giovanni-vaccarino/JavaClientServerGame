package polimi.ingsoft.server.common.command;

import java.io.IOException;
import java.io.Serializable;

public interface Command<T> extends Serializable {
    void execute(T server) throws IOException;
}
