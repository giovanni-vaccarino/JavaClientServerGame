package polimi.ingsoft.server.common.protocol;

import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;

import java.io.Serializable;
import java.util.Map;

public class NetworkMessage implements Serializable {
    public record DrawCardPayload(TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) implements Serializable { }
    public record PlaceCardPayload(MixedCard card, Coordinates coordinates, Boolean isFacingUp) implements Serializable { }
    public record BoardUpdatePayload(String nickname, Coordinates coordinates, PlayedCard playedCard) implements Serializable { }
    public record InitialSettings(PlayerInitialSetting playerInitialSetting) implements Serializable { }
    public record PrivateMessagePayload(String sender, String receiver, String message) implements Serializable { }
    public record BroadcastMessagePayload(String sender, String message) implements Serializable { }
    public record GameStatePayload(GameState gameState) implements Serializable { }
    public record GameStartUpdate(
            PlaceInPublicBoard<ResourceCard> resource,
            PlaceInPublicBoard<GoldCard> gold,
            PlaceInPublicBoard<QuestCard> quest,
            Map<String, Board> boards
            ) implements Serializable { }

    public NetworkMessage(MessageCodes type, Serializable payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageCodes type;
    public Serializable payload;
}
