package polimi.ingsoft.client;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.common.VirtualServer;

public interface Client extends AutoCloseable, VirtualView, VirtualServer {
    void run();
    UI getUI();
}
