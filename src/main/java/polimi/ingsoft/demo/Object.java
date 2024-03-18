public enum Object implements Item {
    PARCHMENT("PAR"), SPELL("SPE");

    private final String abbreviation;

    private Object(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }
}
