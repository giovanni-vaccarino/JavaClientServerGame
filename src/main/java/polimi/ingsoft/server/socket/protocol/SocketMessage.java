package polimi.ingsoft.server.socket.protocol;

import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;

import java.io.Serializable;

public class SocketMessage implements Serializable {
    public record DrawCardPayload(String deckType, PlaceInPublicBoard.Slots slot) implements Serializable { }
    public record PlaceCardPayload(MixedCard card, Coordinates coordinates, Boolean isFacingUp) implements Serializable { }
    public record NicknameAndBoard(String nickname, Board board) implements Serializable { }
    public record InitialSettings(PlayerColor color, Boolean isInitialCardFacingUp, QuestCard questCard) implements Serializable { }

    public SocketMessage(MessageCodes type, Serializable payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageCodes type;
    public Serializable payload;
}
