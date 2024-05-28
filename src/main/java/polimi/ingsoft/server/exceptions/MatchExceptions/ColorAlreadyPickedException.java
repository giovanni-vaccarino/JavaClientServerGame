package polimi.ingsoft.server.exceptions.MatchExceptions;

import polimi.ingsoft.server.enumerations.PlayerColor;

public class ColorAlreadyPickedException extends Exception{
    public ColorAlreadyPickedException(PlayerColor color){super("The color " + color + " has been already selected");}
}
