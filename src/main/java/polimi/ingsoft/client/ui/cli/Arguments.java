package polimi.ingsoft.client.ui.cli;

public interface Arguments {
public enum Argument{

    HELP("help");
    private final String value;
    Argument(String value){this.value=value;}
    public String getValue(){return this.value;}
}
}
