package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * This singleton manages the connection status of every client interacting with a server in the application
 */
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
                            logger.println("The player " + client.getNickname() + " is now disconnected :(");
                            disconnectedClients.add(client);
                        }
                    }

                    for(var client: disconnectedClients){
                        removeClientFromMainServer(client);
                    }

                    for (var client : getClients()) {
                        client.setConnected(false);
                        try {
                            logger.println("PING MANDATO LATO SERVER A: " + client.getNickname());
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

    /**
     * Singleton instance getter
     *
     * @return instance
     */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager(System.out);
        }
        return instance;
    }

    /**
     *
     * @return list of all clients connected to the main server
     */
    public List<ClientConnection> getClients(){
        return clients;
    }

    /**
     *
     * @return list of all clients connected to some match server
     */
    public List<ClientConnection> getClientsInGame(){
        return clientsInGame;
    }

    /**
     *
     * @return a map of match ids and list of connected clients
     */
    public Map<Integer, List<VirtualView>> getMatchNotificationList(){
        return matchNotificationList;
    }

    /**
     *
     * @param nickname client nickname
     * @return client view or null if not present
     */
    public ClientConnection getClient(String nickname){
       return clients.stream()
               .filter(client -> Objects.equals(client.getNickname(), nickname))
               .findFirst()
               .orElse(null);
    }

    /**
     *
     * @param nickname requested nickname
     * @return a boolean telling if the nickname is available for usage
     */
    public Boolean isNicknameAvailable(String nickname){
        return clients.stream().anyMatch(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname))
                ||
                clientsInGame.stream().anyMatch(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname));
    }

    /**
     * Remove a connection to the main server
     *
     * @param clientConnection connection to remove
     */
    public void removeClientFromMainServer(ClientConnection clientConnection) {
        clients.remove(clientConnection);
    }

    /**
     * Add a connection to the main server
     *
     * @param clientConnection connection to add
     */
    public void addClientConnection(ClientConnection clientConnection) {
        clients.add(clientConnection);
    }

    /**
     * Add a new connection to a game
     *
     * @param clientConnection connection to add
     */
    public void addClientInGame(ClientConnection clientConnection) {
        clientsInGame.add(clientConnection);
    }

    /**
     * Remove a connection to a game
     *
     * @param clientConnection connection to remove
     */
    public void removeClientInGame(ClientConnection clientConnection) {
        clientsInGame.remove(clientConnection);
    }

    /**
     *
     * @param virtualView client view
     * @return client virtual view or null if not present
     */
    public ClientConnection getClientInGame(VirtualView virtualView){
        return clientsInGame.stream()
                .filter(client -> Objects.equals(client.getVirtualView(), virtualView))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param nickname client nickname
     * @return client virtual view or null if not present
     */
    public ClientConnection getClientInGame(String nickname){
        return clientsInGame.stream()
                .filter(client -> Objects.equals(client.getNickname(), nickname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a connection to the disconnected clients
     *
     * @param connection connection to add
     * @param matchId the id of the match the client disconnected from
     */
    public void addDisconnectedClient(ClientConnection connection, Integer matchId){
        disconnectedClients.put(connection, matchId);
    }

    /**
     *
     * @param nickname client nickname
     * @return client connection
     */
    public ClientConnection getDisconnectedClient(String nickname){
        return disconnectedClients.keySet().stream()
                .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Remove a client from the disconnected clients
     *
     * @param nickname client nickname
     */
    public void removeDisconnectedClient(String nickname){
        disconnectedClients.keySet().stream()
                .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname))
                .findFirst()
                .ifPresent(disconnectedClients::remove);
    }

    public Map<ClientConnection, Integer> getDisconnectedClients(){return disconnectedClients;}

    /**
     *
     * @param nickname client nickname
     * @return a boolean telling if the player is in the disconnected clients
     */
    public Boolean isPlayerDisconnected(String nickname){
        return disconnectedClients.keySet().stream()
                .anyMatch(clientConnection -> Objects.equals(clientConnection.getNickname(), nickname));
    }
}

