package polimi.ingsoft.server.exceptions.MatchExceptions;

public class WrongStepException extends Exception {
    public WrongStepException() {
        super("Wrong step for the current phase.");
    }
}
