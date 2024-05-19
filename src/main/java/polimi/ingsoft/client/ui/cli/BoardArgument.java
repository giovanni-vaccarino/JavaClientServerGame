package polimi.ingsoft.client.ui.cli;

public enum BoardArgument implements Arguments{
    UP("up"),DOWN("down"),LEFT("left"),RIGHT("right"),UPRIGHT("upright"),UPLEFT("upleft"),DOWNRIGHT("downright"),DOWNLEFT("downleft"),PLAYCARD("playcard");
    private final String value;


    public String getValue() {
        return this.value;
    }
    BoardArgument(String value) {
        this.value = value;
    }
}
