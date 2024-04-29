package polimi.ingsoft.client;

import polimi.ingsoft.server.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Client extends AutoCloseable {
    List<Integer> getAvailableMatches();
    Integer createMatch(Integer requestedNumPlayers);
    Boolean joinMatch(int matchId, String nickname);
    Boolean isMatchJoinable(int matchId);

}
