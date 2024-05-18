package polimi.ingsoft.server.enumerations;

public enum PlayerColors {
    RED, YELLOW, BLUE, GREEN;
    String describe;

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
