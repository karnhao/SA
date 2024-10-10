package ku.cs.exception;

public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        this("Something went wrong");
    }
}
