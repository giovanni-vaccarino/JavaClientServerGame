package polimi.ingsoft.server.model;

import java.util.HashMap;

public class GoldCard extends MixedCard {
    ItemPattern pattern;
    public GoldCard(Face front, Face back, ItemPattern pattern,int score) {
        super(front, back,score);
        this.pattern=pattern;
    }

}
