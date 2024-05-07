package polimi.ingsoft.server.model;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public interface Pattern {
    int getMatch(Board board,Coordinates coordinates);
}