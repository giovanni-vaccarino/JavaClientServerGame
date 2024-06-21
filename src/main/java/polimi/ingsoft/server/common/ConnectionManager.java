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

    /**
     * Maps every client disconnected to its game
     * NOTE: when the game ends, all the disconnected clients from that game are removed
     */
    private final Map<ClientConnection, Integer> disconnectedClients = new HashMap<>();


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
                    logger.println("CLIENTS: " + getClients().stream().map(ClientConnection::getNickname).toList());
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

                    logger.println("ALL THE PINGS TO THE CLIENTS HAVE BEEN SENT");
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
       return clients.stream()
               .filter(client -> Objects.equals(client.getNickname(), nickname))
               .findFirst()
               .orElse(null);
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

    public void addClientInGame(ClientConnection clientConnection) {
        clientsInGame.add(clientConnection);
    }

    public void removeClientInGame(ClientConnection clientConnection) {
        clientsInGame.remove(clientConnection);
    }

    public ClientConnection getClientInGame(VirtualView virtualView){
        return clientsInGame.stream()
                .filter(client -> Objects.equals(client.getVirtualView(), virtualView))
                .findFirst()
                .orElse(null);
    }

    public ClientConnection getClientInGame(String nickname){
        return clientsInGame.stream()
                .filter(client -> Objects.equals(client.getNickname(), nickname))
                .findFirst()
                .orElse(null);
    }

    public void addDisconnectedClient(ClientConnection connection, Integer matchId){
        disconnectedClients.put(connection, matchId);
    }

    public ClientConnection getDisconnectedClient(String nickname){
        return disconnectedClients.keySet().stream()
                .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname))
                .findFirst()
                .orElse(null);
    }

    public void removeDisconnectedClient(String nickname){
        disconnectedClients.keySet().stream()
                .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname))
                .findFirst()
                .ifPresent(disconnectedClients::remove);
    }

    public Map<ClientConnection, Integer> getDisconnectedClients(){return disconnectedClients;}

    public Boolean isPlayerDisconnected(String nickname){
        return disconnectedClients.keySet().stream()
                .anyMatch(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname));
    }
}

