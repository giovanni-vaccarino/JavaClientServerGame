package polimi.ingsoft.server.enumerations;
/**
 * Enumerations that lists all possible error messages
 */
public enum ERROR_MESSAGES {
    // General errors
    SERVER_IP_NOT_VALID("The server ip entered is not valid"),
    PROTOCOL_NUMBER_OUT_OF_BOUND("Protocol does not exist"),
    UNKNOWN_COMMAND("Unknown command"),
    UNKNOWN_ERROR("Unexpected error"),
    BAD_INPUT("The input you provided is not valid"),

    // Nickname errors
    NICKNAME_NOT_AVAILABLE("Nickname is not available"),

    // Matches management errors
    UNABLE_TO_LIST_MATCHES("Unable to list matches"),
    MATCH_NUMBER_OUT_OF_BOUND("Match does not exist"),
    UNABLE_TO_JOIN_MATCH("Unable to join match"),
    MATCH_IS_ALREADY_FULL("Match is already full"),
    MATCH_DOES_NOT_EXIST("Match does not exist"),
    UNABLE_TO_CREATE_MATCH("Unable to create match"),
    PLAYERS_OUT_OF_BOUND("Number of players not accepted. Type a number between 2 and 4"),

    // Game initialization errors
    COLOR_ALREADY_PICKED("The color you have selected has been already picked"),
    INITIAL_SETTING_ALREADY_SET("Initial setting already set"),
    PLAYER_IS_NOT_IN_A_MATCH("Player is currently not in a match"),
    COLOR_ID_OUT_OF_BOUND("Color id is out of bounds"),
    UNABLE_TO_SET_COLOR("Unable to set player color"),
    INVALID_FACE("The face you provided is invalid"),
    UNABLE_TO_SET_FACE("Unable to set initial card face"),
    INVALID_QUEST_CARD("The quest card number you provided is invalid"),
    UNABLE_TO_SET_QUEST_CARD("Unable to set quest card"),

    // Game errors
    WRONG_GAME_PHASE("Wrong phase of the game"),
    MATCH_IS_BLOCKED("The match is currently blocked"),
    WRONG_STEP("Wrong step"),
    WRONG_PLAYER_TURN("It is not your turn"),
    UNABLE_TO_DRAW_CARD("Unable to draw card"),
    UNABLE_TO_PLACE_CARD("Unable to place card"),
    COORDINATE_NOT_VALID("The coordinate you selected is not valid"),
    NOT_ENOUGH_RESOURCES("You do not have enough resources to place that card"),

    // Chat errors
    PLAYER_NOT_FOUND("Unable to find recipient player");

    private final String value;

    ERROR_MESSAGES(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
