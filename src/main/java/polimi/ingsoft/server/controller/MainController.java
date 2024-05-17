package polimi.ingsoft.server.controller;

import com.sun.tools.jconsole.JConsoleContext;
import polimi.ingsoft.server.exceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.NotValidNumPlayersException;
import polimi.ingsoft.server.factories.MatchFactory;

import java.io.PrintStream;
import java.io.Serializable;
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

    private Boolean isJoinable(MatchController match){
        //TODO
        return true;
    }

    public List<Integer> getMatches(){
        Set<Integer> keys = this.matches.keySet();
        return new ArrayList<>(keys);
    }

    public MatchController getMatch(int matchId){
        return this.matches.get(matchId);
    }

    public Integer createMatch(Integer requiredNumPlayers) throws NotValidNumPlayersException{
        int matchId = this.idMatch;
        idMatch += 1;

        if(requiredNumPlayers < 2 || requiredNumPlayers > 4){
            throw new NotValidNumPlayersException();
        }

        MatchController match = MatchFactory.createMatch(logger, matchId, requiredNumPlayers);
        matches.put(matchId, match);

        return matchId;
    }

    public Boolean joinMatch(Integer matchId, String nickname) {
        if(!matchExists(matchId)){
            return false;
        }

        MatchController match = getMatch(matchId);
        if(!isJoinable(match)){
            //Lobby already full
            return false;
        }

        try{
            match.addPlayer(nickname);
        } catch (MatchAlreadyFullException exception){
            //TODO Handle correctly the exception
            logger.println(exception);
            return false;
        }

        return true;
    }

    //TODO add only if deciding to implement the AF
    public void reJoinMatch(Integer lobbyId, String nickname){

    }
}
