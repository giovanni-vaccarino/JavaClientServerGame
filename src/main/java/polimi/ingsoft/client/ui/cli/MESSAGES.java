package polimi.ingsoft.client.ui.cli;

/**
 * Enumeration that lists all possible game messages
 */
public enum MESSAGES {
    CHOOSE_PROTOCOL_LIST("Before starting, choose which communication protocol you want to use: "),
    CHOOSE_PROTOCOL("Choose your protocol: "),
    WELCOME("Welcome to CODEX\n"),
    NICKNAME_UPDATED("Your nickname was successfully set"),
    NO_MATCHES_TO_SHOW("There are currently no matches being run"),
    CHOOSE_MATCH("""
    
    Choose your match, create a new one by typing \"0\" or update the matches list by typing \"update\": """),
    CHOOSE_NICKNAME("Choose your nickname: "),
    CHOOSE_NUMBER_OF_PLAYERS("Choose how many players should the game have: "),
    JOINING_MATCH("Joining match..."),
    JOINED_MATCH("Successfully joined match"),
    PLAYERS_IN_LOBBY("Players waiting in lobby: "),
    CREATED_MATCH("Successfully created match"),
    CHOOSE_COLOR("Select your color id: "),
    COLOR_SUCCESSFULLY_SET("Your color was successfully set"),
    CHOOSE_INITIAL_CARD_FACE("Select the initial card face you would like to use [B/F]: "),
    INITIAL_CARD_FACE_SUCCESSFULLY_SET("The initial card face was successfully set"),
    CHOOSE_QUEST_CARD("Select the quest card you would like to use [1/2]: "),
    QUEST_CARD_SUCCESSFULLY_SET("The quest card was successfully set"),
    GAME_START("The game will start now"),
    IS_YOUR_TURN("It's now your turn to play"),
    CURRENT_PLAYER("Current player: "),
    CARD_SUCCESSFULLY_PLACED("Card was placed successfully"),
    CARD_SUCCESSFULLY_DRAWN("Card was drawn successfully"),
    OTHER_PLAYER_PERFORMING_PLACE(" has to place her/his card"),
    OTHER_PLAYER_PERFORMING_DRAW(" has to draw her/his card"),
    HELP_MAIN("""
            HERE'S WHAT YOU CAN DO !
            ->BOARD:ENTER THE BOARD MENU
            ->PUBLICBOARD: ENTER THE PUBLICBOARD MENU
            ->CHAT: ACCESS CHAT MENU!
            ->MESSAGE: CHAT PRIVATELY WITH A PLAYER
            ->HELP: PRINT THIS LIST AGAIN!
            """),

    HELP_BOARD("""
            HERE'S WHAT YOU CAN DO !
            ->[UP,DOWN,LEFT,RIGHT,UPRIGHT,UPLEFT,DOWNRIGHT,DOWNLEFT]: SHOW THE BOARD FROM ONE OF THESE POSITIONS !
            ->PLAYCARD: PLAY A CARD !
            ->BACK: GO BACK TO THE MAIN MENU !
            ->HELP: PRINT THIS LIST AGAIN!
            """),

    HELP_INITIAL_CARD_CHOICE("""
            SELECT WHICH SIDE OF THE CARD TO KEEP FACE UP
            -> B: PICK THE BOTTOM (on the left)
            -> F: PICK THE FRONT (on the right)
            """),

    HELP_QUEST_CARD_CHOICE("""
            SELECT WHICH QUEST UP TO KEEP\s
            THINK CAREFULLY, AS YOU WON'T BE ABLE TO CHANGE IT LATER\s
            -> 1: Pick the one on the left\s
            -> 2: Pick the one on the right\s
            """),

    PLAY_CARD_HELP_1("""
            WHICH CARD WOULD YOU LIKE TO PLAY ? YOU CAN FLIP ANY CARD BY TYPING "FLIP",
            CHAT WITH ANOTHER PLAYER BY TYPING "CHAT OR EXPLORE YOUR BOARD BY TYPING "MOVE""
            1->LEFT
            2->CENTER
            3->RIGHT
            FLIP -> FLIP ONE OF YOUR HAND'S CARDS
            CHAT -> CHAT WITH ANOTHER PLAYER
            MOVE -> NAVIGATE YOUR BOARD
            BOARD -> SEE ANOTHER PLAYER'S BOARD
            """),

    PLAY_CARD_HELP_2("""
            A CARD THAT'S SHOWN LEFT TO THE CENTRAL SPACE ALWAYS HAS AN X COORDINATE DECREASED BY 1!
            A CARD THAT'S SHOWN RIGHT TO THE CENTRAL SPACE ALWAYS HAS AN X COORDINATE INCREASED BY 1!
            A CARD THAT'S SHOWN ABOVE THE CENTRAL SPACE ALWAYS HAS A Y COORDINATE INCREASED BY 1!
            A CARD THAT'S SHOWN BELOW THE CENTRAL SPACE ALWAYS HAS A Y COORDINATE DECREASED BY 1!
            TELL ME WHERE YOU WOULD LIKE TO PLACE YOUR CARD !
            CENTRAL CARD COORDINATES ARE:
            """),

    PLAY_CARD_HELP_X("COORDINATE X:"),

    PLAY_CARD_HELP_Y("COORDINATE Y:"),

    PLAY_CARD_HELP_ORIENTATION("""
            REMEMBER: THE FRONT SIDE OF A CARD IS THE ONE THAT SHOWS ITS CENTRAL SPACE!
            WHICH SIDE OF THE CARD SHOULD BE FACING UP ?
            1-> FRONT
            2-> BACK
            """),
    HELP_CLIENT_BOARD("""
    HERE'S WHAT YOU CAN DO !
    ->GETCARD:TELL ME YOU WANT TO PICK A CARD !
    ->BACK: GO BACK TO THE MAIN MENU !
    ->HELP: PRINT THIS LIST AGAIN!
    """),
    HELP_GET_CARD_TYPE("""
        TELL ME WHERE YOU WANT TO PICK YOUR CAR FROM, TYPE CHAT TO ENTER CHAT MENU, OR TYPE BOARD TO SEE ANOTHER PLAYER'S BOARD !
        ->RESOURCE: PICK ONE OF THE RESOUCE CARDS !
        ->GOLD: PICK ONE OF THE GOLD CARDS !
        ->CHAT: ENTER CHAT MENU  !
        ->BOARD: SEE ANOTHER PLAYER'S BOARD !
        """),

    HELP_GET_CARD_PLACE("""
    TELL ME WHICH CARD YOU WANT TO PICK !
    ->CENTER: PICK THE CENTRAL CARD !
    ->RIGHT: PICK THE CARD ON THE RIGHT !
    ->DECK: PICK THE HIDDEN CARD FROM THE DECK !
    ->BACK: GO BACK TO CHOOSING THE CARD TYPE !
    """),

    HELPCHAT(""),

    CLS("\033[H\033[2J"),

    ERROR("SOMETHING WENT WRONG, PLEASE TRY WITH ANOTHER COMMAND !\n"),

    GET_FLIP_CHOICE("""
            WHICH CARD WOULD YOU LIKE TO FLIP ?
            1->LEFT
            2->CENTER
            3->RIGHT
            BACK -> GO BACK TO PLAYING YOUR CARD 
            """),

    LAST_ROUND("""
            LAST ROUND !
            """),

    GAME_END("""
            THE MATCH IS FINISHED !
            """),

    HELP_GET_MESSAGE_TYPE("""
            WOULD YOU LIKE YOUR MESSAGE TO BE BROADCASTED OR SENT TO A SPECIFIED PLAYER ?
            1-> BROADCAST MESSAGE
            2-> PRIVATE MESSAGE
            3-> SEE BROADCAST CHAT
            4-> SEE PRIVATE MESSAGES
            """),
    END_CHAT_ITER_MESSAGE("""
            
            PRESS "ENTER" TO GO BACK TO YOUR BOARD'S MENU
            """),

    HELP_GET_PLAYER_NAME("TYPE THE RECEIVING PLAYER'S NAME:"),

    HELP_GET_MESSAGE("WHAT WOULD YOU LIKE TO SAY ?"),

    CHAT_RECEIVER_ERROR("RECEIVER DOES NOT EXIST !"),

    CHAT_RECEIVER_IS_SENDER_ERROR("YOU CAN'T TEXT YOURSELF !"),

    HELP_OTHER_PLAYERS_BOARD("""
            WHOSE BOARD WOULD YOU LIKE TO SEE ?
            """),
    BOARD_OBSERVED_IS_OBSERVED_ERROR("YOU CAN ALREADY SEE YOUR BOARD !"),

    BOARD_OBSERVED_ERROR("PLAYER DOES NOT EXIST !"),

    HELP_BOARD_NAVIGATE("""
    HERE'S WHAT YOU CAN DO: WHEN AN ARROW IS SHOWN IN EITHER DIRECTION, YOU CAN TYPE THAT DIRECTION AND SEE THE BOARD FROM THAT PERSPECTIVE !
    DIRECTIONS:
    ->UP
    ->DOWN
    ->LEFT
    ->RIGHT
    ->UPRIGHT
    ->UPLEFT
    ->DOWNRIGHT
    ->DOWNLEFT
    ->BACK: GO BACK TO YOUR BOARD !
    """),

    GO_BACK_TO_MATCH_MENU("Press enter to go back to the match choice menu..."),
    PRESS_NEXT_LINE("Press enter to try again...");

    private final String value;

    MESSAGES(String value) {
            this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
