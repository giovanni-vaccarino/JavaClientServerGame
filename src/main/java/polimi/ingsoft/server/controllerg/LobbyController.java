package polimi.ingsoft.server.controllerg;

import polimi.ingsoft.server.model.MatchLobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyController {
    private List<MatchLobby> lobbies;

    public LobbyController() {
        this.lobbies = new ArrayList<>();
    }

    public List<MatchLobby> getLobbies() {
        return lobbies;
    }

    public MatchLobby createLobby(int requestedNumPlayers) {
        int numLobby = lobbies.size() + 1;
        MatchLobby lobby = new MatchLobby(numLobby, requestedNumPlayers);
        lobbies.add(lobby);
        return lobby;
    }

    public void joinLobby(String nickPlayer, int lobbyNumber){
        int indexLobby = getLobby(lobbyNumber);

        if(indexLobby != -1){
            MatchLobby lobby = lobbies.get(indexLobby);
            lobby.addPlayer(nickPlayer);
        }else{
            //lobby not found
        }
    }

    public int getLobby(int lobbyNumber){
        for(var lobby : lobbies){
            if (lobby.getLobbyNumber() == lobbyNumber){
                return lobbies.indexOf(lobby);
            }
        }

        return -1;
    }
}
