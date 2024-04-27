package polimi.ingsoft.server.model;

import java.util.HashMap;

public class GoldCard extends MixedCard {
    ItemPattern pattern;
    HashMap<Item,Integer> cost;
    public GoldCard(Face front, Face back, ItemPattern pattern,int score,HashMap<Item,Integer> cost) {
        super(front, back,score);
        this.cost=cost;
        this.pattern=pattern;
    }

    public HashMap<Item, Integer> getCost() {
        return cost;
    }
}
