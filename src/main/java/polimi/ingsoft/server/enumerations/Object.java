package polimi.ingsoft.server.enumerations;

import polimi.ingsoft.server.model.Item;

public enum Object implements Item {
    SCROLL("SCR"), POTION("POT") , FEATHER("FEA");

    private final String abbreviation;

    private Object(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }


}
