package polimi.ingsoft.server.model;

public class PublicBoard {

    private final PlaceInPublicBoard<ResourceCard> placeResource;
    private final PlaceInPublicBoard<GoldCard> placeGold;
    private final PlaceInPublicBoard<QuestCard> placeQuest;

    public PublicBoard(
            Deck<ResourceCard> resourceCardsDeck,
            Deck<GoldCard> goldCardsDeck,
            Deck<QuestCard> QuestCardDeck
    ) {
        placeResource = new PlaceInPublicBoard<>(resourceCardsDeck);
        placeGold = new PlaceInPublicBoard<>(goldCardsDeck);
        placeQuest = new PlaceInPublicBoard<>(QuestCardDeck);
    }

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
