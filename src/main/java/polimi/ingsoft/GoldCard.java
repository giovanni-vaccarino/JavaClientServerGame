package src.main.java.polimi.ingsoft;

public class GoldCard extends GameCard implements Drawable, ConditionalPointsCard {
    private final Pattern pattern;
    public GoldCard(Pattern pattern) {
        this.pattern = pattern;
    }
    @Override
    public Pattern getPattern() {
        return this.pattern;
    }
}
