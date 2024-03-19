package polimi.ingsoft;

public class Table {
    private final Deck<GoldCard> goldDeck;
    private final Deck<ResourceCard> resourceDeck;
    private final Deck<QuestCard> questDeck;

    private GoldCard goldCardLeft;
    private GoldCard goldCardRight;

    private GoldCard resourceCardLeft;
    private GoldCard resourceCardRight;

    private final QuestCard questCardLeft;
    private final QuestCard questCardRight;

    public Table(Deck<GoldCard> goldDeck, Deck<ResourceCard> resourceDeck, Deck<QuestCard> questDeck){
        this.goldCardLeft = goldDeck.pop();
        this.goldCardRight = goldDeck.pop();

        this.goldDeck = goldDeck;

        this.resourceCardLeft = resourceDeck.pop();
        this.resourceCardRight = resourceDeck.pop();

        this.resourceDeck = resourceDeck;

        this.questCardLeft = questDeck.pop();
        this.questCardRight = questDeck.pop();

        this.questDeck = questDeck;

    }

    public ResourceCard getResourceLeft() {
        ResourceCard result = slotA;
        slotA = deck.pop();
        return result;
    }
}
