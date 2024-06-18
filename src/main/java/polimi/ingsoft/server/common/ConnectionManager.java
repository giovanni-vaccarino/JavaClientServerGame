package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private static ConnectionManager instance;

    private final Map<String, VirtualView> clients = new HashMap<>();

    private final Map<String, VirtualView> clientsInGame = new HashMap<>();

    private final Map<Integer, List<VirtualView>> matchNotificationList = new HashMap<>();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Map<String, VirtualView> getClients(){
        return clients;
    }

    public Map<String, VirtualView> getClientsInGame(){
        return clientsInGame;
    }

    public Map<Integer, List<VirtualView>> getMatchNotificationList(){
        return matchNotificationList;
    }
}

