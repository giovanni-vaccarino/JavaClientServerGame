package polimi.ingsoft.server.enumerations;

import com.fasterxml.jackson.annotation.JsonTypeName;
import polimi.ingsoft.server.model.Item;
@JsonTypeName("Resource")
public enum Resource implements Item {
    LEAF("LEAF"), WOLF("WOLF"), MUSHROOM("MUSHROOM"), BUTTERFLY("BUTTERFLY");
    private String value;

     Resource(String value){
        this.value=value;
    }
}