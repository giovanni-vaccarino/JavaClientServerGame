package polimi.ingsoft.server.model.player;

/**
 * Enumerations that lists all possible player colors
 */
public enum PlayerColor {
    RED, YELLOW, BLUE, GREEN;
    String describe;

    /**
     * Converts a player color to String
     * @return a PlayerColor's respective string
     */
    @Override
    public String toString() {
        return switch (this){
            case RED -> "RED";
            case BLUE ->"BLUE";
            case YELLOW -> "YELLOW";
            case GREEN -> "GREEN";
        };
    }
}
