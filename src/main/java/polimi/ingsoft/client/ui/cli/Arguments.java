package polimi.ingsoft.client.ui.cli;

/**
 * Interface that's implemented from all CLI arguments
 */
public interface Arguments {
    /**
     * General arguments avaiable in all CLI's pages
     */
    enum Argument{

    HELP("help");
    private final String value;
    Argument(String value){this.value=value;}
    public String getValue(){return this.value;}
}
}
