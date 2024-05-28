package polimi.ingsoft.server.exceptions.MatchExceptions;

public class WrongPlayerForCurrentTurnException extends Exception {
    public WrongPlayerForCurrentTurnException() {
        super("Wrong player for the current turn.");
    }
}
