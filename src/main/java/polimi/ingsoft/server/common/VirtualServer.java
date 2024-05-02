package polimi.ingsoft.server.common;

import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.util.List;

public interface VirtualServer {
    List<Integer> getMatches() throws IOException;

    Integer createMatch(Integer requiredNumPlayers) throws IOException;

    Boolean joinMatch(Integer matchId, String nickname) throws IOException;

    void reJoinMatch(Integer matchId, String nickname) throws IOException;

    void addMessage(int matchId, String message) throws IOException;

    void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException;

    void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException;
}
