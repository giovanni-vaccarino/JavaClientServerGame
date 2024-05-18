package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.NotValidNumPlayersException;
import polimi.ingsoft.server.factories.MatchFactory;

import java.io.PrintStream;
import java.util.*;

public class MainController {

    private final Map<Integer, MatchController> matches;

    private Integer idMatch = 1;
    private final PrintStream logger;

    public MainController(PrintStream logger) {
        this.matches = new HashMap<>();
        this.logger = logger;
    }

    private Boolean matchExists(Integer matchId){
        return this.matches.containsKey(matchId);
    }

    public List<Integer> getMatches(){
        Set<Integer> keys = this.matches.keySet();
        return new ArrayList<>(keys);
    }

    public MatchController getMatch(int matchId){
        return this.matches.get(matchId);
    }

    public Integer createMatch(Integer requiredNumPlayers) throws NotValidNumPlayersException{
        if(requiredNumPlayers < 2 || requiredNumPlayers > 4){
            throw new NotValidNumPlayersException();
        }
        int matchId = this.idMatch;
        idMatch += 1;

        MatchController match = MatchFactory.createMatch(logger, matchId, requiredNumPlayers);
        matches.put(matchId, match);

        return matchId;
    }

    public void joinMatch(Integer matchId, String nickname) throws MatchAlreadyFullException, MatchNotFoundException {
        if(!matchExists(matchId)){
            throw new MatchNotFoundException();
        }

        MatchController match = getMatch(matchId);

        match.addPlayer(nickname);
    }

    //TODO add only if deciding to implement the AF
    public void reJoinMatch(Integer lobbyId, String nickname){

    }
}
