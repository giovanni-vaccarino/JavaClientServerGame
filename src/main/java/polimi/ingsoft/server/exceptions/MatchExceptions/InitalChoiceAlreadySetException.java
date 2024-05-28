package polimi.ingsoft.server.exceptions.MatchExceptions;

import polimi.ingsoft.server.enumerations.INITIAL_STEP;

public class InitalChoiceAlreadySetException extends Exception{
    public InitalChoiceAlreadySetException(INITIAL_STEP initialStep){
        super(initialStep + " already set.");
    }
}
