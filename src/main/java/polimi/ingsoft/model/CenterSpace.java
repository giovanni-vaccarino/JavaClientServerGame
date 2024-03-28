package polimi.ingsoft.model;

import java.util.List;

import polimi.ingsoft.enumerations.Resource;

public class CenterSpace extends Space<Resource> {
    protected CenterSpace(List<Resource> items) {
        super(items);
    }
}
