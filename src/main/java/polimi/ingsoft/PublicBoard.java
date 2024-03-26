package polimi.ingsoft;

public class PublicBoard {

    private final PlaceInPublicBoard<ResourceCard> placeResource;
    private final PlaceInPublicBoard<GoldCard> placeGold;
    private final PlaceInPublicBoard<MixedCard> placeMixed;

    public PublicBoard(
            Deck<ResourceCard> resourceCardsDeck,
            Deck<GoldCard> goldCardsDeck,
            Deck<MixedCard> mixedCardDeck
    ) {
        placeResource = new PlaceInPublicBoard<>(resourceCardsDeck);
        placeGold = new PlaceInPublicBoard<>(goldCardsDeck);
        placeMixed = new PlaceInPublicBoard<>(mixedCardDeck);
    }

    public ResourceCard getResource(PlaceInPublicBoard.Slots spaceSlot) {
        return placeResource.get(spaceSlot);
    }

    public GoldCard getGold(PlaceInPublicBoard.Slots spaceSlot) {
        return placeGold.get(spaceSlot);
    }

    public MixedCard getMixed(PlaceInPublicBoard.Slots spaceSlot) {
        return placeMixed.get(spaceSlot);
    }
}
