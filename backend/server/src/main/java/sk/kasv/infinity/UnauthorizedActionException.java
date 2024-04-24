package sk.kasv.infinity;

public class UnauthorizedActionException extends RuntimeException {
    private static final long serialVersionUID = 7263168775566582187L;

    public UnauthorizedActionException() {
    }

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
