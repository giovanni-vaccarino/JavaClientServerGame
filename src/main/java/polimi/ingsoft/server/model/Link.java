package polimi.ingsoft.server.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.enumerations.Resource;

import java.io.Serializable;

public class Link implements Serializable {
    private final Resource color;
    private final Coordinates posFromBegin;
    public Link(@JsonProperty("color") Resource items, @JsonProperty("posFromBegin") Coordinates posFromBegin){
      this.color=items;
      this.posFromBegin=posFromBegin;
    }

    public Coordinates getPosFromBegin() {
        return posFromBegin;
    }

    public Resource getColor() {
        return color;
    }
}
