package polimi.ingsoft.client.ui.cli;

public enum PublicBoardArguments implements Arguments {
    RESOURCE("resource"),GOLD("gold"),LEFT("left"),RIGHT("right"),DECK("deck"),GETCARD("getcard");
    private final String value;


    public String getValue() {
        return this.value;
    }
    PublicBoardArguments(String value) {
        this.value = value;
    }
}
