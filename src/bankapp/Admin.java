package bankapp;

import java.security.NoSuchAlgorithmException;

/**
 * Represents an administrator in the banking system.
 * Admins have access to all accounts and elevated privileges.
 */
public class Admin extends User {

    /**
     * Private constructor to prevent direct creation of Admin objects.
     * Use {@link #createAdmin} to create an admin via authorization.
     *
     * @param username The admin's username.
     * @param password The admin's password.
     * @throws NoSuchAlgorithmException If SHA-512 is not available.
     */
    public Admin(String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
    }

    /**
     * Indicates whether this user is an admin.
     *
     * @return true, as this is an Admin instance.
     */
    @Override
    public boolean isAdmin() {
        return true;
    }

    /**
     * Method to create an admin, restricted to existing admins only and thus is only available to the Admin API.
     *
     * @param requestingUser The user requesting to create an admin.
     * @param username       The new admin's username.
     * @param password       The new admin's password.
     * @return A new Admin instance if authorized, otherwise null.
     * @throws NoSuchAlgorithmException If hashing fails.
     */
    public static Admin createAdmin(User requestingUser, String username, String password)
            throws NoSuchAlgorithmException {
    	boolean isUsernameNull = username == null;
    	boolean isPasswordNull = password == null;
    	boolean isRequestingUserNull = requestingUser == null;
    	
    	if(isUsernameNull || isPasswordNull || isRequestingUserNull) {
    		return null;
    	}
    	
    	boolean isUsernameEmpty = username.isEmpty();
    	boolean isPasswordEmpty = password.isEmpty();
    	if(isUsernameEmpty || isPasswordEmpty) {
    		return null;
    	}

    	boolean isRequestingUserAnAdmin = requestingUser.isAdmin();

        if (isRequestingUserAnAdmin) {
            return new Admin(username, password);
        }
        return null;
    }
}
