package polimi.ingsoft.server.exceptions;

public class NotValidMessageException extends IllegalArgumentException{
    public NotValidMessageException() {
        super("Message text cannot be null or empty");
    }
}
