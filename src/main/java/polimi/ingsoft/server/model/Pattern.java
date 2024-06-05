package polimi.ingsoft.server.model;



import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_OBJECT, property="cost", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= CornerPattern.class, name="Corner"),
        @JsonSubTypes.Type(value=ItemPattern.class, name="Item"),
        @JsonSubTypes.Type(value=SchemePattern.class, name="Order")
})
/**
 * Interface used from all Patterns
 */
public interface Pattern {

    /**
     * Return the amount of times a certain pattern gets fulfilled
     * @param board the player's board that has to be checked
     * @param coordinates the played card's coordinates
     * @return the amount of times a certain pattern gets fulfilled
     */
    int getMatch(Board board,Coordinates coordinates);
}
