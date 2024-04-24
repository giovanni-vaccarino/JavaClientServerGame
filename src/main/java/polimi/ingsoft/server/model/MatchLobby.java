package polimi.ingsoft.server.model;

import java.util.ArrayList;
import java.util.List;

public class MatchLobby {
    private final int lobbyNumber;
    private List<String> players;
    private final int numPlayersRequested;

    public MatchLobby(int lobbyNumber, int requestedPlayers) {
        this.lobbyNumber = lobbyNumber;
        this.numPlayersRequested = requestedPlayers;
        this.players = new ArrayList<>();
    }

    public boolean addPlayer(String playerName) {
        players.add(playerName);

        if (players.size() == numPlayersRequested) {
            players.add(playerName);
            return true;
        }
        return false;
    }

    public int getLobbyNumber() {
        return lobbyNumber;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getNumPlayersRequested() {
        return numPlayersRequested;
    }
}

