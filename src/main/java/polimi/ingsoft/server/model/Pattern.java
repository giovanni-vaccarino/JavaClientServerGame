package polimi.ingsoft.server.model;


//@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_OBJECT /*WRAPPER_OBJECT*/, property="cost", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value= HashMap.class, name="cost"),
//        @JsonSubTypes.Type(value= Resource.class, name="MUSHROOM"),
//        @JsonSubTypes.Type(value= Resource.class, name="BUTTERFLY"),
//        @JsonSubTypes.Type(value= Resource.class, name="WOLF"),
//        @JsonSubTypes.Type(value= Resource.class, name="LEAF"),
//        @JsonSubTypes.Type(value= Object.class, name="FEATHER"),
//        @JsonSubTypes.Type(value= Object.class, name="POTION"),
//        @JsonSubTypes.Type(value= Object.class, name="SCROLL")
//})


public interface Pattern {
    int getMatch(Board board,Coordinates coordinates);

}