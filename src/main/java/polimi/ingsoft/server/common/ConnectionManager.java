package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.util.*;

public class ConnectionManager {
    private static ConnectionManager instance;

    private final List<ClientConnection> clients = new ArrayList<>();

    private final List<ClientConnection> clientsInGame = new ArrayList<>();

    private final Map<Integer, List<VirtualView>> matchNotificationList = new HashMap<>();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }


    public List<ClientConnection> getClients(){
        return clients;
    }

    public List<ClientConnection> getClientsInGame(){
        return clientsInGame;
    }


    public Map<Integer, List<VirtualView>> getMatchNotificationList(){
        return matchNotificationList;
    }

    public ClientConnection getClient(String nickname){
       for(var client : clients){
           if(Objects.equals(client.getNickname(), nickname))
               return client;
       }

       return null;
    }

    public Boolean isNicknameAvailable(String nickname){
        return clients.stream().anyMatch(clientConnection -> clientConnection.getNickname() == nickname);
    }
}

