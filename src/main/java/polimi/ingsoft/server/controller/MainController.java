package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.factories.MatchFactory;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;

public class MainController {

    private final Map<Integer, MatchController> matches;
    private final PrintStream logger;

    public MainController(PrintStream logger) {
        this.matches = new HashMap<>();
        this.matches.put(1, MatchFactory.createMatch(logger, 1, 3));
        this.logger = logger;
    }

    private Boolean matchExists(Integer matchId){
        return this.matches.containsKey(matchId);
    }

    private Boolean isJoinable(MatchController match){
        //TODO
        return true;
    }

    public Integer createMatch(Integer requiredNumPlayers){
        int matchId = matches.keySet().size() + 1;

        MatchController match = MatchFactory.createMatch(logger, matchId, requiredNumPlayers);
        matches.put(matchId, match);

        return matchId;
    }

    public Boolean joinMatch(Integer matchId, String nickname) {
        if(!matchExists(matchId)){
            //Lobby not found
            return false;
        }

        MatchController match = getMatch(matchId);
        if(!isJoinable(match)){
            //Lobby already full
            return false;
        }
        match.addPlayer(nickname);
        return true;
    }

    public void reJoinMatch(Integer lobbyId, String nickname){

    }

    public List<Integer> getMatches(){
        Set<Integer> keys = this.matches.keySet();
        return new ArrayList<>(keys);
    }

    public MatchController getMatch(int matchId){
        return this.matches.get(matchId);
    }
}
