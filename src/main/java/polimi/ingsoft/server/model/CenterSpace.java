package polimi.ingsoft.server.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

/**
 * Represents the central space of all GameCards
 */
public class CenterSpace extends Space<Resource> {

    /**
     * CenterSace creator
     * @param items the list of resources that should be granted by playing a card that
     *              displays the CenterSpace (face up)
     */
    public CenterSpace(@JsonProperty("items") List<Resource> items) {
        super(items);
    }

}
