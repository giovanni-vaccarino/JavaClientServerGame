package polimi.ingsoft.model;

public class GoldCard extends MixedCard {
    ItemPattern pattern;
    public GoldCard(Face front, Face back, ItemPattern pattern,int score) {
        super(front, back,score);
        this.pattern=pattern;
    }
}
