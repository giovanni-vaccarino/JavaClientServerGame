package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private static ConnectionManager instance;

    private Map<String, VirtualView> clients = new HashMap<>();

    private Map<String, VirtualView> clientsInGame = new HashMap<>();

    private Map<Integer, List<VirtualView>> matchNotificationList = new HashMap<>();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
}

