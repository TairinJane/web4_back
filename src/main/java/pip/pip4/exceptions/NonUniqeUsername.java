package pip.pip4.exceptions;

public class NonUniqeUsername extends RuntimeException {
    public NonUniqeUsername() {
    }

    public NonUniqeUsername(String message) {
        super(message);
    }

    public NonUniqeUsername(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqeUsername(Throwable cause) {
        super(cause);
    }
}
