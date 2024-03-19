package polimi.ingsoft;

public class GoldCard extends GameCard implements MixedCard, ConditionalPointsCard {
    private final Pattern pattern;
    public GoldCard(Pattern pattern) {
        this.pattern = pattern;
    }
    @Override
    public Pattern getPattern() {
        return this.pattern;
    }
}
