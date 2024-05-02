package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CardCollection <T extends Drawable> implements Serializable {
    List<T> cards = new ArrayList<>();
}
