package polimi.ingsoft.server.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

import java.io.Serializable;

/**
 * Object used by SchemePattern class to represent a certain disposition of a set of cards
 */
public class Link implements Serializable {
    private final Resource color;
    private final Coordinates posFromBegin;

    /**
     * Creates a new Link Object
     * @param items the Item that's present in the card's CenterSpace
     * @param posFromBegin the Link's position relative to the starting point of the SchemePattern
     */
    public Link(@JsonProperty("color") Resource items, @JsonProperty("posFromBegin") Coordinates posFromBegin){
      this.color=items;
      this.posFromBegin=posFromBegin;
    }

    /**
     * Returns a link's relative position from the beginning of a SchemePattern
     * @return a link's relative position from the beginning of a SchemePattern
     */
    public Coordinates getPosFromBegin() {
        return posFromBegin;
    }

    /**
     * Returns a link's representing color
     * @return a link's representing color
     */
    public Resource getColor() {
        return color;
    }
}
