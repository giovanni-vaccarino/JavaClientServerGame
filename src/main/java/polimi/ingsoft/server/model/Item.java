package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;

import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As./*WRAPPER_ARRAY */WRAPPER_OBJECT, property="items", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= Resource.class, name="RESOURCE"),
        @JsonSubTypes.Type(value= Object.class, name="OBJECT"),
})

public interface Item extends Serializable {

}
