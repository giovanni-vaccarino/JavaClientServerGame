package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.List;

public class PublicBoard implements Serializable {

    private final PlaceInPublicBoard<ResourceCard> placeResource;
    private final PlaceInPublicBoard<GoldCard> placeGold;
    private final PlaceInPublicBoard<QuestCard> placeQuest;
    private Deck<InitialCard> availableInitialCards;

    public PublicBoard(
            Deck<ResourceCard> resourceCardsDeck,
            Deck<GoldCard> goldCardsDeck,
            Deck<QuestCard> QuestCardDeck,
            Deck<InitialCard> initialCards
    ) {
        placeResource = new PlaceInPublicBoard<>(resourceCardsDeck);
        placeGold = new PlaceInPublicBoard<>(goldCardsDeck);
        placeQuest = new PlaceInPublicBoard<>(QuestCardDeck);
        availableInitialCards = initialCards;
    }

    public InitialCard getInitial(){return availableInitialCards.draw();}

    public ResourceCard getResource(PlaceInPublicBoard.Slots spaceSlot) {
        return placeResource.get(spaceSlot);
    }

    public GoldCard getGold(PlaceInPublicBoard.Slots spaceSlot) {
        return placeGold.get(spaceSlot);
    }

    public QuestCard getQuest(PlaceInPublicBoard.Slots spaceSlot) {
        return placeQuest.get(spaceSlot);
    }
}
