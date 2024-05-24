package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_ARRAY /*WRAPPER_OBJECT*/, property="Items", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= Resource.class, name="MUSHROOM"),
        @JsonSubTypes.Type(value= Resource.class, name="BUTTERFLY"),
        @JsonSubTypes.Type(value= Resource.class, name="WOLF"),
        @JsonSubTypes.Type(value= Resource.class, name="LEAF"),
        @JsonSubTypes.Type(value= Object.class, name="FEATHER"),
        @JsonSubTypes.Type(value= Object.class, name="POTION"),
        @JsonSubTypes.Type(value= Object.class, name="SCROLL")
})

public interface Item {

}
