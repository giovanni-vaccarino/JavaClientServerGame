package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.common.MatchData;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NotValidNumPlayersException;
import polimi.ingsoft.server.factories.MatchFactory;

import java.io.PrintStream;
import java.util.*;

/**
 * Controller class that manages the creation and management of matches.
 */
public class MainController {
    /**
     * A map that associates each match ID with its corresponding MatchController.
     */
    private final Map<Integer, MatchController> matches;

    private Integer idMatch = 1;

    private final PrintStream logger;


    /**
     * Constructs a MainController with the specified logger.
     *
     * @param logger the logger to be used for logging information
     */
    public MainController(PrintStream logger) {
        this.matches = new HashMap<>();
        this.logger = logger;
    }


    /**
     * Checks if a match exists with the given ID.
     *
     * @param matchId the ID of the match to check
     * @return true if the match exists, false otherwise
     */
    private Boolean matchExists(Integer matchId){
        return this.matches.containsKey(matchId);
    }


    /**
     * Returns a list of match IDs.
     *
     * @return a list of match IDs
     */
    public synchronized List<MatchData> getMatches(){
        Set<Integer> keys = this.matches.keySet();

        return keys.stream()
                .map(match -> new MatchData(match,
                        getMatch(match).getLobbyPlayers().size(),
                        getMatch(match).getRequestedNumPlayers()))
                .toList();
    }


    /**
     * Returns the MatchController for the specified match ID.
     *
     * @param matchId the ID of the match
     * @return the MatchController for the specified match ID
     */
    public synchronized MatchController getMatch(int matchId){
        return this.matches.get(matchId);
    }


    /**
     * Creates a new match with the specified number of players.
     *
     * @param requiredNumPlayers the number of players required for the match
     * @return the ID of the newly created match
     * @throws NotValidNumPlayersException if the number of players is not valid (< 2 or > 4)
     */
    public synchronized Integer createMatch(Integer requiredNumPlayers) throws NotValidNumPlayersException{
        if(requiredNumPlayers < 2 || requiredNumPlayers > 4){
            throw new NotValidNumPlayersException();
        }
        int matchId = this.idMatch;
        idMatch += 1;

        MatchController match = MatchFactory.createMatch(logger, matchId, requiredNumPlayers);
        matches.put(matchId, match);

        return matchId;
    }


    /**
     * Delete the MatchController for the specified match ID.
     *
     * @param matchId the ID of the match
     * @throws MatchNotFoundException    if the match is not found
     */
    public synchronized void deleteMatch(int matchId){
        MatchController matchController = matches.get(matchId);
        matchController.turnPingerOff();
        matches.remove(matchId);
    }


    /**
     * Allows a player to join an existing match.
     *
     * @param matchId  the ID of the match to join
     * @param nickname the nickname of the player joining the match
     * @throws MatchAlreadyFullException if the match is already full
     * @throws MatchNotFoundException    if the match is not found
     */
    public synchronized void joinMatch(Integer matchId, String nickname) throws MatchAlreadyFullException, MatchNotFoundException {
        System.out.println("CONTROLLER: match exists result with id " + matchId + " " + matchExists(matchId));
        if(!matchExists(matchId)){
            throw new MatchNotFoundException();
        }

        MatchController match = getMatch(matchId);

        System.out.println("CONTROLLER: match " + match);

        match.addPlayer(nickname);
    }
}
