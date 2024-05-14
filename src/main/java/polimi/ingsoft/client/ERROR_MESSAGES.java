package polimi.ingsoft.client;

public enum ERROR_MESSAGES {
    PLAYERS_OUT_OF_BOUND("Number of players not accepted. Type a number between 2 and 4"),
    PROTOCOL_NUMBER_OUT_OF_BOUND("Protocol does not exist"),
    MATCH_NUMBER_OUT_OF_BOUND("Match does not exist"),
    NICKNAME_NOT_AVAILABLE("Nickname is not available"),
    UNKNOWN_COMMAND("Unknown command"),
    UNABLE_TO_JOIN_MATCH("Unable to join match"),
    UNABLE_TO_CREATE_MATCH("Unable to create match");

    private final String value;

    ERROR_MESSAGES(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
