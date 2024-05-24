package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.List;

public class CornerSpace extends Space<Item> {
    public CornerSpace(@JsonProperty("items") List<Item> items) {
        super(items);
    }
}
