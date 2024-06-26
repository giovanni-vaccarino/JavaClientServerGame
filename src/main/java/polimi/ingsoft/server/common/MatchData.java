package polimi.ingsoft.server.common;

import java.io.Serializable;

/**
 * Stores numeric information about a match
 *
 * @param matchId id of the match
 * @param joinedPlayers players that joined the match
 * @param requiredNumPlayers number of players specified at creation
 */
public record MatchData(Integer matchId, Integer joinedPlayers, Integer requiredNumPlayers) implements Serializable { }
