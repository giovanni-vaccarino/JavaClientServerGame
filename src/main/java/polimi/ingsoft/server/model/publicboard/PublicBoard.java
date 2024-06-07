package polimi.ingsoft.server.model.publicboard;

import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;

import java.io.Serializable;

/**
 * Class that represents a game's public board, where players can draw cards and look at the common QuestCards
 */
public class PublicBoard implements Serializable {

    private final PlaceInPublicBoard<ResourceCard> placeResource;
    private final PlaceInPublicBoard<GoldCard> placeGold;
    private final PlaceInPublicBoard<QuestCard> placeQuest;
    private final Deck<InitialCard> availableInitialCards;

    /**
     * Creates a PublicBoard object
     * @param resourceCardsDeck a Deck of ResourceCards
     * @param goldCardsDeck a Deck of GoldCards
     * @param QuestCardDeck a Deck of QuestCards
     * @param initialCards a Deck of InitialCards
     */
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

    /**
     * Returns the ResourceCards' associated place in public board
     * @return the ResourceCards' associated place in public board
     */
    public PlaceInPublicBoard<ResourceCard> getPublicBoardResource(){
        return this.placeResource;
    }
    /**
     * Returns the GoldCards' associated place in public board
     * @return the GoldCards' associated place in public board
     */
    public PlaceInPublicBoard<GoldCard> getPublicBoardGold(){
        return this.placeGold;
    }
    /**
     * Returns the QuestCards' associated place in public board
     * @return the QuestCards' associated place in public board
     */
    public PlaceInPublicBoard<QuestCard> getPublicBoardQuest(){
        return this.placeQuest;
    }

    /**
     * Returns a random InitialCard
     * @return a random InitialCard
     */
    public InitialCard getInitial(){return availableInitialCards.draw();}

    /**
     * Returns a ResourceCard from ResourceCards' spaceSlot
     * @param spaceSlot the slot that the card should be picked from
     * @return a ResourceCard from ResourceCards' spaceSlot
     */
    public ResourceCard getResource(PlaceInPublicBoard.Slots spaceSlot) {
        return placeResource.draw(spaceSlot);
    }
    /**
     * Returns a ResourceCard from GoldCards' spaceSlot
     * @param spaceSlot the slot that the card should be picked from
     * @return a ResourceCard from GoldCards' spaceSlot
     */
    public GoldCard getGold(PlaceInPublicBoard.Slots spaceSlot) {
        return placeGold.draw(spaceSlot);
    }
    /**
     * Returns a ResourceCard from QuestCards' spaceSlot
     * @param spaceSlot the slot that the card should be picked from
     * @return a ResourceCard from QuestCards' spaceSlot
     */
    public QuestCard getQuest(PlaceInPublicBoard.Slots spaceSlot) {
        return placeQuest.draw(spaceSlot);
    }

    /**
     * Returns TRUE when both ResourceCards' and GoldCards' deck are empty
     * @return TRUE when both ResourceCards' and GoldCards' deck are empty
     */
    public Boolean isDecksEmpty(){
        return placeResource.isPlaceInPublicBoardEmpty() && placeGold.isPlaceInPublicBoardEmpty();
    }
}
