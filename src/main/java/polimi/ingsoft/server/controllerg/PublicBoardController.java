package polimi.ingsoft.server.controllerg;

import polimi.ingsoft.server.model.*;

public class PublicBoardController {
    final PublicBoard publicBoard;

    public PublicBoardController() {
        Deck<ResourceCard> resourceDeck = new Deck<>();
        Deck<GoldCard> goldDeck = new Deck<>();
        Deck<QuestCard> questDeck = new Deck<>();

        this.publicBoard = new PublicBoard(resourceDeck, goldDeck, questDeck);
    }

    public MixedCard drawCard(String deckType, PlaceInPublicBoard.Slots slot){
        switch(deckType){
            case "Resource" -> {
                return this.publicBoard.getResource(slot);
            }
            case "Gold" -> {
                return this.publicBoard.getGold(slot);
            }
        }

        return null;
    }
}
