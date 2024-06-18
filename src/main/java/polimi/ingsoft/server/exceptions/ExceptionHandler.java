package polimi.ingsoft.server.exceptions;

import polimi.ingsoft.client.common.VirtualView;

public interface ExceptionHandler {
    void handle(VirtualView client, Exception exception);
}
