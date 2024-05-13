package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.List;

public class PublicBoard implements Serializable {

    private final PlaceInPublicBoard<ResourceCard> placeResource;
    private final PlaceInPublicBoard<GoldCard> placeGold;
    private final PlaceInPublicBoard<QuestCard> placeQuest;
    private List<InitialCard> availableInitialCards;

    public PublicBoard(
            Deck<ResourceCard> resourceCardsDeck,
            Deck<GoldCard> goldCardsDeck,
            Deck<QuestCard> QuestCardDeck,
            List<InitialCard> initialCards
    ) {
        placeResource = new PlaceInPublicBoard<>(resourceCardsDeck);
        placeGold = new PlaceInPublicBoard<>(goldCardsDeck);
        placeQuest = new PlaceInPublicBoard<>(QuestCardDeck);
        //TODO add this when you can load the cards: availableInitialCards = initialCards;
    }

    public InitialCard getInitial(){return availableInitialCards.removeFirst();}

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
