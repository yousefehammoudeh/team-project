package use_case;

/**
 * Marker exception type for use-case layer to describe data access failures
 * without depending on outer data_access packages.
 */
public class UseCaseDataAccessException extends Exception {
    public UseCaseDataAccessException(String message) {
        super(message);
    }

    public UseCaseDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
