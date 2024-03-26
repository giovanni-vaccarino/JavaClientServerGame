package polimi.ingsoft;

public class PublicBoard {
    public enum Slots {
        RESOURCE, GOLD, MIXED
    }

    private final PlaceInPublicBoard<ResourceCard> placeA;
    private final PlaceInPublicBoard<GoldCard> placeB;
    private final PlaceInPublicBoard<MixedCard> placeC;

    public PublicBoard(Deck<ResourceCard> resourceCardsDeck, Deck<GoldCard> goldCardsDeck, Deck<MixedCard> mixedCardDeck) {
        placeA = new PlaceInPublicBoard<>(resourceCardsDeck);
        placeB = new PlaceInPublicBoard<>(goldCardsDeck);
        placeC = new PlaceInPublicBoard<>(mixedCardDeck);
    }

    public GameCard getCard(PublicBoard.Slots boardSlot, PlaceInPublicBoard.Slots spaceSlot) {
        switch (boardSlot) {
            case RESOURCE -> {
                return placeA.get(spaceSlot);
            }
            case GOLD -> {
                return placeB.get(spaceSlot);
            }
            case MIXED -> {
                return placeC.get(spaceSlot);
            }
        }
        return null;
    }
}
