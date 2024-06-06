package polimi.ingsoft.server.enumerations;
import com.fasterxml.jackson.annotation.*;
import polimi.ingsoft.server.model.Item;

/**
 * Enumerations that lists all the possible object values
 */
public enum Object implements Item {
    SCROLL("SCROLL"), POTION("POTION") , FEATHER("FEATHER");

    private final String abbreviation;

    @JsonCreator
    Object(@JsonProperty("items") String abbreviation){
        this.abbreviation = abbreviation;
    }

    /**
     * Returns an Object's representing String value
     * @return an Object's representing String value
     */
    public String getAbbreviation(){
        return abbreviation;
    }

}
