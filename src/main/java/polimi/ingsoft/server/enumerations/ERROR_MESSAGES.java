package polimi.ingsoft.server.enumerations;

public enum ERROR_MESSAGES {
    PLAYERS_OUT_OF_BOUND("Number of players not accepted. Type a number between 2 and 4"),
    PROTOCOL_NUMBER_OUT_OF_BOUND("Protocol does not exist"),
    MATCH_NUMBER_OUT_OF_BOUND("Match does not exist"),
    NICKNAME_NOT_AVAILABLE("Nickname is not available"),
    UNKNOWN_COMMAND("Unknown command"),
    INVALID_COMMAND("This command cannot be run in this situation"),
    UNKNOWN_ERROR("Unexpected error"),
    UNABLE_TO_LIST_MATCHES("Unable to list matches"),
    UNABLE_TO_JOIN_MATCH("Unable to join match"),
    MATCH_IS_ALREADY_FULL("Match is already full"),
    MATCH_DOES_NOT_EXIST("Match does not exist"),
    UNABLE_TO_CREATE_MATCH("Unable to create match"),
    WRONG_GAME_PHASE("Wrong phase of the game"),
    WRONG_STEP("Wrong step"),
    COORDINATE_NOT_VALID("The coordinate you selected is not valid"),
    NOT_ENOUGH_RESOURCES("You do not have enough resources to place that card"),
    COLOR_ALREADY_PICKED("The color you have selected has been already picked"),
    WRONG_PLAYER_TURN("It is not your turn"),
    INITIAL_SETTING_ALREADY_SET("Initial setting already set"),
    PLAYER_IS_NOT_IN_A_MATCH("Player is currently not in a match"),
    COLOR_ID_OUT_OF_BOUND("Color id is out of bounds"),
    UNABLE_TO_SET_COLOR("Unable to set player color"),
    INVALID_FACE("The face you provided is invalid"),
    UNABLE_TO_SET_FACE("Unable to set initial card face"),
    INVALID_QUEST_CARD("The quest card number you provided is invalid"),
    UNABLE_TO_SET_QUEST_CARD("Unable to set quest card"),
    PLAYER_NOT_FOUND("Unable to find recipient player");

    private final String value;

    ERROR_MESSAGES(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
