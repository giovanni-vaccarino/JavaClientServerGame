package polimi.ingsoft.server.exceptions;

public class NotValidNumPlayersException extends IllegalArgumentException{
    public NotValidNumPlayersException(){
        super("Numeber of players requested not valid. Put a number between 2 and 4.");
    }
}
