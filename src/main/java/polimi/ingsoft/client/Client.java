package polimi.ingsoft.client;

import polimi.ingsoft.server.Player;

import java.util.Map;

public interface Client extends AutoCloseable {
    Map<Integer, String> getAvailableMatches();
    Player createMatch(String nickname);
    Player joinMatch(int matchId, String nickname);

}
