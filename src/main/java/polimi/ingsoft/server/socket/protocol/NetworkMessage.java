package polimi.ingsoft.server.socket.protocol;

import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    public record DrawCardPayload(String deckType, PlaceInPublicBoard.Slots slot) implements Serializable { }
    public record PlaceCardPayload(MixedCard card, Coordinates coordinates, Boolean isFacingUp) implements Serializable { }
    public record BoardUpdatePayload(String nickname, Coordinates coordinates, PlayedCard playedCard) implements Serializable { }
    public record InitialSettings(PlayerInitialSetting playerInitialSetting) implements Serializable { }
    public record PrivateMessagePayload(String sender, String receiver, String message) implements Serializable { }
    public record BroadcastMessagePayload(String sender, String message) implements Serializable { }

    public NetworkMessage(MessageCodes type, Serializable payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageCodes type;
    public Serializable payload;
}
