package polimi.ingsoft.enumerations;

import polimi.ingsoft.model.Item;

public enum Object implements Item {
    SCROLL("SCR"), SPELL("SPE");

    private final String abbreviation;

    private Object(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }


}
