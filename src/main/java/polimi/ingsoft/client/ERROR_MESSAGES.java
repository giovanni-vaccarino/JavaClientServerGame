package polimi.ingsoft.client;

public enum ERROR_MESSAGES {
    PROTOCOL_NUMBER_OUT_OF_BOUND("Protocol does not exist"),
    MATCH_NUMBER_OUT_OF_BOUND("Match does not exist"),
    NICKNAME_NOT_AVAILABLE("Nickname is not available"),
    UNABLE_TO_JOIN_MATCH("Unable to join match");

    private final String value;

    ERROR_MESSAGES(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
