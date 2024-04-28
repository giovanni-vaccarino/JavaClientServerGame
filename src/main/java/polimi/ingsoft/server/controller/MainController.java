package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.factories.MatchFactory;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    private final Map<Integer, MatchController> matches;
    private final PrintStream logger;

    public MainController(PrintStream logger) {
        this.matches = new HashMap<>();
        this.logger = logger;
    }

    private Boolean matchExists(Integer matchId){
        return this.matches.containsKey(matchId);
    }

    private Boolean isJoinable(MatchController match){
        //TODO
        return true;
    }

    public void createMatch(String nickname, Integer requiredNumPlayers){
        int matchId = matches.keySet().size() + 1;

        MatchController match = MatchFactory.createMatch(logger, matchId);
        matches.put(matchId, match);
        match.addPlayer(nickname);
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
