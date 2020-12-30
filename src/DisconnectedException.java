/**
 * Throw this when checkInput() does not detect an input.
 */
public class DisconnectedException extends Exception {

    public DisconnectedException(String message) {
        super(message);
    }

}