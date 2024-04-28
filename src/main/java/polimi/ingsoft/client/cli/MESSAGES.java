package polimi.ingsoft.client.cli;

public enum MESSAGES {
    CHOOSE_PROTOCOL_LIST("Before starting, choose which communication protocol you want to use: "),
    CHOOSE_PROTOCOL("Choose your protocol: "),
    WELCOME("Welcome to CODEX, choose your match between the ones available: \n"),
    CHOOSE_MATCH("Choose your match: "),
    CHOOSE_NICKNAME("Choose your nickname: "),
    JOINING_MATCH("Joining match..."),
    JOINED_MATCH("Succesfully joined match");

    private final String value;

    MESSAGES(String value) {
            this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
