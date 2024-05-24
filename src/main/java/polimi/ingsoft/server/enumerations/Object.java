package polimi.ingsoft.server.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import polimi.ingsoft.server.model.Item;
@JsonTypeName("Object")
//@JsonProperty("Items")
public enum Object implements Item {
    SCROLL("SCR"), POTION("POT") , FEATHER("FEA");

    private final String abbreviation;

    private Object(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }

}
