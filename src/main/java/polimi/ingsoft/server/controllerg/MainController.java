package polimi.ingsoft.server.controllerg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    private Map<Integer, MatchController> matches;

    public MainController() {
        this.matches = new HashMap<>();
    }

    private Boolean matchExists(Integer matchId){
        return this.matches.containsKey(matchId);
    }

    private Boolean isJoinable(MatchController match){
        //TODO
        return true;
    }

    public void createMatch(String nickname, Integer requiredNumPlayers){
        int idMatch = matches.keySet().size() + 1;

        MatchController match = new MatchController();
        matches.put(idMatch, match);
    }

    public void joinMatch(Integer matchId, String nickname) {
        if(!matchExists(matchId)){
            //Lobby not found
            return;
        }

        MatchController match = getMatch(matchId);
        if(!isJoinable(match)){
            //Lobby already full
            return;
        }
        match.addPlayer(nickname);
    }

    public void reJoinMatch(Integer lobbyId, String nickname){

    }

    public MatchController getMatch(int matchId){
        return this.matches.get(matchId);
    }
}
