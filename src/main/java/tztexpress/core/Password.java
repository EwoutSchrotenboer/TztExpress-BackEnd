package tztexpress.core;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Password {
    private static int workload = 6;

    public static String hashPassword(String plain_pass) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(plain_pass, salt);

        return hashed_password;
    }

    public static boolean checkPassword(String plain_pass, String hash) {

        if(null == hash || !hash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash.");
        }

        return BCrypt.checkpw(plain_pass, hash);
    }
}
