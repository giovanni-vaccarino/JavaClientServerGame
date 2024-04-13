package polimi.ingsoft.server.model;
import polimi.ingsoft.server.enumerations.Resource;

public class Link {
    private final Resource color;
    private final Coordinates posFromBegin;
    public Link(Resource color, Coordinates posFromBegin){
      this.color=color;
      this.posFromBegin=posFromBegin;
    }

    public Coordinates getPosFromBegin() {
        return posFromBegin;
    }

    public Resource getColor() {
        return color;
    }
}
