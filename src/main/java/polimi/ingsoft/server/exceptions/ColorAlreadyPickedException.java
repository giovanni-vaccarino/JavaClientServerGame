package polimi.ingsoft.server.exceptions;

import polimi.ingsoft.server.enumerations.PlayerColors;

public class ColorAlreadyPickedException extends Exception{
    public ColorAlreadyPickedException(PlayerColors color){super("The color " + color + " has been already selected");}
}
