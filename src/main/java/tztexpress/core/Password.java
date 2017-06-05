package tztexpress.core;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This class handles the password creation and checking.
 */
public class Password {
    /**
     * A workload of 6 is chosen, as this is a demo application on light hardware. If this application goes to production
     * we can change the workload to 12 or even 16, for a more secure hash.
     */
    private static int workload = 6;

    /**
     * Hashes the plain password
     * @param plain_pass the password to hash
     * @return the hash
     */
    public static String hashPassword(String plain_pass) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(plain_pass, salt);

        return hashed_password;
    }

    /**
     * Checks the plain password against the database hash
     * @param plain_pass the plain password
     * @param hash the database hash
     * @return whether the hashes match.
     */
    public static boolean checkPassword(String plain_pass, String hash) {

        if(null == hash || !hash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash.");
        }

        return BCrypt.checkpw(plain_pass, hash);
    }
}
