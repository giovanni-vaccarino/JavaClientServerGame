package polimi.ingsoft.server.model;

import java.util.ArrayList;
import java.util.List;

public class MatchLobby {
    private boolean isStarted;
    private final int lobbyId;
    private List<String> players;
    private final int numPlayersRequested;

    public MatchLobby(int lobbyId, int requestedPlayers) {
        this.lobbyId = lobbyId;
        this.isStarted = false;
        this.numPlayersRequested = requestedPlayers;
        this.players = new ArrayList<>();
    }

    public void addPlayer(String playerName) {
        players.add(playerName);

        if (!isStarted && players.size() == numPlayersRequested) {
            isStarted = true;
        }
    }

    public int getNumberPlayer(){
        // ATTENZIONE AL CORNER CASE DEL GIOCATORE CHE SI DISCONNETTE
        return players.size();
    }

    public Boolean isStarted(){
        return isStarted;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getNumPlayersRequested() {
        return numPlayersRequested;
    }
}

