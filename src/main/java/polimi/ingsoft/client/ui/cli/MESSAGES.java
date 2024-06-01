package polimi.ingsoft.client.ui.cli;

public enum MESSAGES {
    CHOOSE_PROTOCOL_LIST("Before starting, choose which communication protocol you want to use: "),
    CHOOSE_PROTOCOL("Choose your protocol: "),
    WELCOME("Welcome to CODEX\n"),
    NICKNAME_UPDATED("Your nickname was successfully set"),
    NO_MATCHES_TO_SHOW("There are currently no matches being run"),
    CHOOSE_MATCH("Choose your match or create a new one by typing 0: "),
    CHOOSE_NICKNAME("Choose your nickname: "),
    CHOOSE_NUMPLAYERS("Choose how many players should the game have: "),
    JOINING_MATCH("Joining match..."),
    JOINED_MATCH("Succesfully joined match"),
    PLAYERS_IN_LOBBY("Players waiting in lobby: "),
    CREATED_MATCH("Successfully created match"),
    CHOOSE_COLOR("Select your color id: "),
    COLOR_SUCCESSFULLY_SET("Your color was successfully set"),
    CHOOSE_INITIAL_CARD_FACE("Select the initial card face you would like to use [F/B]: "),
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
    HELPMAIN("HERE'S WHAT YOU CAN DO !\n" +
            "->BOARD:ENTER THE BOARD MENU\n" +
            "->PUBLICBOARD: ENTER THE PUBLICBOARD MENU\n" +
            "->CHAT: ACCESS CHAT MENU!\n" +
            "->MESSAGE: CHAT PRIVATELY WITH A PLAYER\n" +
            "->HELP: PRINT THIS LIST AGAIN!\n"),
    HELPBOARD("""
            HERE'S WHAT YOU CAN DO !
            ->[UP,DOWN,LEFT,RIGHT,UPRIGHT,UPLEFT,DOWNRIGHT,DOWNLEFT]: SHOW THE BOARD FROM ONE OF THESE POSITIONS !
            ->PLAYCARD: PLAY A CARD !
            ->BACK: GO BACK TO THE MAIN MENU !
            ->HELP: PRINT THIS LIST AGAIN!
            """),
    HELP_INITIAL_CARD_CHOICE("SELECT WHICH SIDE OF THE CARD TO KEEP FACE UP\n" +
            "-> F: PICK THE FRONT (on the left)\n" +
            "-> B: PICK THE BOTTOM (on the right)\n"),
    HELP_QUEST_CARD_CHOICE("SELECT WHICH QUEST UP TO KEEP \n" +
            "THINK CAREFULLY, AS YOU WON'T BE ABLE TO CHANGE IT LATER \n" +
            "-> 1: Pick the one on the left \n" +
            "-> 2: Pick the one on the right \n"),
    PLAYCARDHELP1("""
            WHICH CARD WOULD YOU LIKE TO PLAY ? YOU CAN FLIP ANY CARD BY WRITING "FLIP"
            1->LEFT
            2->CENTER
            3->RIGHT
            """),
    PLAYCARDHELP2("""
            A CARD THAT'S SHOWN LEFT TO THE CENTRAL SPACE ALWAYS HAS AN X COORDINATE DECREASED BY 1!
            A CARD THAT'S SHOWN RIGHT TO THE CENTRAL SPACE ALWAYS HAS AN X COORDINATE INCREASED BY 1!
            A CARD THAT'S SHOWN ABOVE THE CENTRAL SPACE ALWAYS HAS A Y COORDINATE INCREASED BY 1!
            A CARD THAT'S SHOWN BELOW THE CENTRAL SPACE ALWAYS HAS A Y COORDINATE DECREASED BY 1!
            TELL ME WHERE YOU WOULD LIKE TO PLACE YOUR CARD !
            CENTRAL CARD COORDINATES ARE:
            """),
    PLAYCARDHELPX("COORDINATE X:"),
    PLAYCARDHELPY("COORDINATE Y:"),
    PLAYCARDHELPORIENTATION("""
            REMEMBER: THE FRONT SIDE OF A CARD IS THE ONE THAT SHOWS ITS CENTRAL SPACE!
            WHICH SIDE OF THE CARD SHOULD BE FACING UP ?
            1-> FRONT
            2-> BACK
            """),
    HELPCLIENTBOARD("HERE'S WHAT YOU CAN DO !\n->GETCARD:TELL ME YOU WANT TO PICK A CARD !\n->BACK: GO BACK TO THE MAIN MENU !\n->HELP: PRINT THIS LIST AGAIN!\n"),
    HELPGETCARDTYPE("TELL ME WHERE YOU WANT TO PICK YOUR CAR FROM!\n->[RESOURCE,GOLD]: SELECT WHICH KIND OF CARD TO PICK !\n"),
    HELPGETCARDPLACE("TELL ME WHICH CARD YOU WANT TO PICK !\n->[LEFT,RIGHT,DECK]: SELECT FROM WHERE TO PICK THE CHOSEN TYPE OF CARD !\n"),
    HELPCHAT(""),
    CLS("\033[H\033[2J"),
    ERROR("SOMETHING WENT WRONG, PLEASE TRY WITH ANOTHER COMMAND !\n"),
    GETFLIPCHOICE("""
            WHICH CARD WOULD YOU LIKE TO FLIP ?" +
            1->LEFT
            2->CENTER
            3->RIGHT
            """);


    private final String value;

    MESSAGES(String value) {
            this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
