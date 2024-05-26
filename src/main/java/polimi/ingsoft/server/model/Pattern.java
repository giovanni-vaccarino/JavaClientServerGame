package polimi.ingsoft.server.model;



import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_OBJECT, property="cost", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= CornerPattern.class, name="Corner"),
        @JsonSubTypes.Type(value=ItemPattern.class, name="Item"),
        @JsonSubTypes.Type(value=SchemePattern.class, name="Scheme")
})
public interface Pattern {
    int getMatch(Board board,Coordinates coordinates);
}
