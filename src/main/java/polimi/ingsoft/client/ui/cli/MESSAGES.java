package polimi.ingsoft.client.ui.cli;

public enum MESSAGES {
    CHOOSE_PROTOCOL_LIST("Before starting, choose which communication protocol you want to use: "),
    CHOOSE_PROTOCOL("Choose your protocol: "),
    WELCOME("Welcome to CODEX, choose your nickname: \n"),
    NICKNAME_UPDATED("Your nickname was successfully set"),
    UNABLE_TO_UPDATE_NICKNAME("This nickname has already been used"),
    CHOOSE_MATCH("Choose your match: "),
    CHOOSE_NICKNAME("Choose your nickname: "),
    CHOOSE_NUMPLAYERS("Choose how many players should the game have: "),
    JOINING_MATCH("Joining match..."),
    JOINED_MATCH("Succesfully joined match"),
    CREATED_MATCH("Successfully create match");

    private final String value;

    MESSAGES(String value) {
            this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
