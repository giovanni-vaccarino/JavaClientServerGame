package polimi.ingsoft.server.model;

import java.util.ArrayList;
import java.util.List;

public abstract class CardCollection <T extends Drawable>{
    List<T> cards = new ArrayList<>();
}
