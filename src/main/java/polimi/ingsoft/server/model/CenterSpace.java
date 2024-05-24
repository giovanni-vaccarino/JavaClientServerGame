package polimi.ingsoft.server.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

public class CenterSpace extends Space<Resource> {
    public CenterSpace(@JsonProperty("items") List<Resource> items) {
        super(items);
    }

}
