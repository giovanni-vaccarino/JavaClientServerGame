package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConnectionsClient {
    static final Map<String, VirtualView> clients = new HashMap<>();

    static final Map<Integer, List<VirtualView>> matchNotificationList = new HashMap<>();
}
