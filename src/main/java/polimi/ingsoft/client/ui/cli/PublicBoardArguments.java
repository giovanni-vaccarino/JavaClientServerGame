package polimi.ingsoft.client.ui.cli;

/**
 * Enumerations that lists all possible Public Board Arguments
 */
public enum PublicBoardArguments implements Arguments {
    RESOURCE("resource"),GOLD("gold"),LEFT("left"),RIGHT("right"),DECK("deck"),GETCARD("getcard");
    private final String value;

    /**
     * Return a PublicBoardArguments' corresponding string value
     * @return a PublicBoardArguments' corresponding string value
     */
    public String getValue() {
        return this.value;
    }
    PublicBoardArguments(String value) {
        this.value = value;
    }
}
