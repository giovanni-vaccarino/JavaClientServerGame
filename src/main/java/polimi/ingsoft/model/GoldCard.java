package polimi.ingsoft.model;

public class GoldCard extends MixedCard implements ConditionalPointsCard {
    private final Pattern pattern;
    public GoldCard(Face front, Face back, Pattern pattern) {
        super(front, back);
        this.pattern = pattern;
    }
    @Override
    public Pattern getPattern() {
        return this.pattern;
    }
}
