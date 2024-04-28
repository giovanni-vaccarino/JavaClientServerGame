package polimi.ingsoft.server.model;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public interface Pattern<T> {
    int getMatch(Board board, T obj);//<T> array);
}