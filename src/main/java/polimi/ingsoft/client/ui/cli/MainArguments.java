package polimi.ingsoft.client.ui.cli;

/**
 * Enumeration that lists all the possible main arguments
 */
public enum MainArguments implements Arguments {
    BOARD("board"),PUBLICBOARD("publicboard"),CHAT("chat"),MESSAGE("message");

    private final String value;

    /**
     * Return a MainArguments' corresponding string value
     * @return a MainArguments' corresponding string value
     */
    public String getValue() {
        return this.value;
    }
    MainArguments(String value) {
        this.value = value;
    }
}
