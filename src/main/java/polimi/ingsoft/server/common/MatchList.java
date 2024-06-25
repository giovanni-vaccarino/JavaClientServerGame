package polimi.ingsoft.server.common;

import java.io.Serial;
import java.io.Serializable;

public class MatchList implements Serializable {
    private final Integer matchId;

    private final Integer joinedPlayers;

    private final Integer requiredNumPlayers;

    public MatchList(Integer matchId, Integer joinedPlayers, Integer requiredNumPlayers) {
        this.matchId = matchId;
        this.joinedPlayers = joinedPlayers;
        this.requiredNumPlayers = requiredNumPlayers;
    }

    public Integer getJoinedPlayers() {
        return joinedPlayers;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Integer getRequiredNumPlayers() {
        return requiredNumPlayers;
    }
}
