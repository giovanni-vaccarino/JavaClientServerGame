public class Controller {
    Tile tile;
    CartaOro cartaOro;
    void calculatePoints() {
        Pattern pattern = cartaOro.getPattern();
        int points = pattern.getMatches(tile) * cartaOro.getPoints();
    }
}
