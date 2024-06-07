package polimi.ingsoft.server.model.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Interface used from all Objects that can be present in Corner or Center spaces
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As./*WRAPPER_ARRAY */WRAPPER_OBJECT, property="items", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= Resource.class, name="RESOURCE"),
        @JsonSubTypes.Type(value= Object.class, name="OBJECT"),
})

public interface Item extends Serializable {

}
