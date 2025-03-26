package bankapp;

/**
 * Enum representing various error codes for authentication-related failures.
 * Each error code corresponds to a specific error condition.
 */
public enum ErrorCodes {

    /**
     * Error code indicating that the username was not found.
     * This code is used when a user attempts to log in with a non-existent username.
     */
    USERNAME_NOT_FOUND(1),

    /**
     * Error code indicating that the provided password is incorrect.
     * This code is used when a user attempts to log in with an incorrect password.
     */
    PASSWORD_INCORRECT(2),

    /**
     * Error code indicating that either the username or password is incorrect.
     * This code is used for a more general failure when the credentials do not match.
     */
    INVALID_CREDENTIALS(3);

    private final int code;

    // Constructor to assign the numeric code to each error.
    ErrorCodes(int code) {
        this.code = code;
    }

    /**
     * Gets the numeric error code associated with this error.
     *
     * @return The error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the string representation of the error code.
     *
     * @return A string representing the error code.
     */
    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
