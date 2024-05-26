package polimi.ingsoft.server.enumerations;

import com.fasterxml.jackson.annotation.*;
import polimi.ingsoft.server.model.Item;
public enum Object implements Item {
    SCROLL("SCROLL"), POTION("POTION") , FEATHER("FEATHER");

    private final String abbreviation;

    @JsonCreator
    Object(@JsonProperty("items") String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }

}
