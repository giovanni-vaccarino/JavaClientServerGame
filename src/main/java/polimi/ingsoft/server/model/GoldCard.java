package polimi.ingsoft.server.model;

public class GoldCard extends MixedCard implements ConditionalPointsCard {
    private final Pattern pattern;
    public GoldCard(Face front, Face back, Pattern pattern,int score) {
        super(front, back,score);
        this.pattern = pattern;
    }
    @Override
    public Pattern getPattern() {
        return this.pattern;
    }
}
