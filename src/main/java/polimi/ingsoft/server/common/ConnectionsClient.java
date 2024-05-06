package polimi.ingsoft.server.common;

import polimi.ingsoft.client.rmi.VirtualView;

import java.util.ArrayList;
import java.util.List;

public interface ConnectionsClient {
    static final List<VirtualView> clients = new ArrayList<>();
}
