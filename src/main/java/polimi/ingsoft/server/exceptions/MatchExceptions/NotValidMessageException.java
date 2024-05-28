package polimi.ingsoft.server.exceptions.MatchExceptions;

public class NotValidMessageException extends IllegalArgumentException{
    public NotValidMessageException() {
        super("Message text cannot be null or empty");
    }
}
