package polimi.ingsoft.server.enumerations;

public enum ERROR_MESSAGES {
    PLAYERS_OUT_OF_BOUND("Number of players not accepted. Type a number between 2 and 4"),
    PROTOCOL_NUMBER_OUT_OF_BOUND("Protocol does not exist"),
    MATCH_NUMBER_OUT_OF_BOUND("Match does not exist"),
    NICKNAME_NOT_AVAILABLE("Nickname is not available"),
    UNKNOWN_COMMAND("Unknown command"),
    UNKNOWN_ERROR("Unexpected error"),
    UNABLE_TO_JOIN_MATCH("Unable to join match"),
    MATCH_IS_ALREADY_FULL("Match is already full"),
    MATCH_DOES_NOT_EXIST("Match does not exist"),
    UNABLE_TO_CREATE_MATCH("Unable to create match"),
    WRONG_GAME_PHASE("Wrong phase of the game"),
    WRONG_STEP("Wrong step"),
    COLOR_ALREADY_PICKED("The color you have selected has been already picked"),
    WRONG_PLAYER_TURN("It is not your turn"),
    INITIAL_SETTING_ALREADY_SET("Initial setting already set"),
    PLAYER_IS_NOT_IN_A_MATCH("Player is currently not in a match");

    private final String value;

    ERROR_MESSAGES(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
