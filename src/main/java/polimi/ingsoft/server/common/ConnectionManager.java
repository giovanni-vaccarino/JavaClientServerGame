package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class ConnectionManager {
    private final PrintStream logger;

    private static ConnectionManager instance;

    private final List<ClientConnection> clients = new ArrayList<>();

    private final List<ClientConnection> clientsInGame = new ArrayList<>();

    private final Map<Integer, List<VirtualView>> matchNotificationList = new HashMap<>();

    private ConnectionManager(PrintStream logger) {
        this.logger = logger;
        logger.println("patata");
        scheduleTimeoutRoutine();
    }

    private void scheduleTimeoutRoutine() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Send ping to all clients connected to main server
                synchronized (getClients()) {
                    logger.println("CLIENTS: " + getClients().toString());
                    List<ClientConnection> disconnectedClients = new ArrayList<>();
                    for (var client : getClients()) {
                        if (!client.getConnected()) {
                            // Client has disconnected :(
                            logger.println("Il bro si è disconnesso :(");
                            disconnectedClients.add(client);
                        }
                    }

                    for(var client: disconnectedClients){
                        removeClientFromMainServer(client);
                    }

                    for (var client : getClients()) {
                        client.setConnected(false);
                        try {
                            client.getVirtualView().pong();
                        } catch (IOException ignore) {
                        }
                    }

                    logger.println("aRgmgsoei");
                }
            }
        };

        new Timer().scheduleAtFixedRate(task, 0, 5000);
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager(System.out);
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
        return clients.stream().anyMatch(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname));
    }

    public void removeClientFromMainServer(ClientConnection clientConnection) {
        clients.remove(clientConnection);
    }

    public void addClientConnection(ClientConnection clientConnection) {
        clients.add(clientConnection);
    }
}

