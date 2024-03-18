package src.main.java.polimi.ingsoft;

public class CartaOro implements ConditionalPointsCard {
    int getPoints() {
        return 3;
    }

    @Override
    public Pattern getPattern() {
        return null;
    }
}
