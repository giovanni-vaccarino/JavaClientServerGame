package polimi.ingsoft.client.ui.cli;

public enum MainArguments implements Arguments {
    BOARD("board"),PUBLICBOARD("publicboard"),CHAT("chat"),MESSAGE("message");

    private final String value;


    public String getValue() {
        return this.value;
    }
    MainArguments(String value) {
        this.value = value;
    }
}
