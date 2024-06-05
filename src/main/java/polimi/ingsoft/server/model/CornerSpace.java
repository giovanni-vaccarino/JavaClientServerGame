package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.List;

/**
 * Class that represents a card's corner, containing Resources, Items or nothing
 */
public class CornerSpace extends Space<Item> {

    /**
     * Creates a new CornerSpace representing a card's corner containing Resources, Items or nothing
     * @param items the list of items that a card should contain
     */
    public CornerSpace(@JsonProperty("items") List<Item> items) {
        super(items);
    }
}
