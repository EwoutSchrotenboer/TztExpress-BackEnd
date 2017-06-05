package tztexpress.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.core.Password;
import tztexpress.models.TokenRequest;
import tztexpress.models.User;
import tztexpress.repositories.UserRepository;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service authenticates all incoming requests when called. It checks the Authentication-header.
 */
@Service
public class AuthenticationService {

    private static UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * HACK HACK HACK - VERY FRAGILE - TREAT WITH CARE
     * This mehtod validates the token. It will return true if the token is valid (within the timelimit, and with
     * correct information about the user, username and password).
     * @param authentication the encrypted validation token
     * @return whether the token is valid or not
     */
    public static boolean ValidateToken(List<String> authentication) {
        if (authentication.size() != 1) {
            return false;
        }
        String authenticationStringEncoded = authentication.get(0);
        byte[] authByteArray = Base64.decodeBase64(authenticationStringEncoded.getBytes());

        String authString;
        try {
            authString = new String(authByteArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        Map<String, String> authenticationElements = new HashMap<String, String>();

        // We can assume here that the string is always in the right order.
        String[] stringArray = authString.split(":");

        String email = stringArray[0];
        String password = stringArray[1];

        // build timestamp from remaining items
        String timestampString = new String();

        for(int i = 2; i < stringArray.length - 1; i++) {
            if (i > 2) {
                timestampString += ":";
            }
            timestampString += stringArray[i];
        }

        DateTime timestamp = DateTime.parse(timestampString);

        // if token is less than 60 minutes old, continue
        if (((DateTime.now().getMillis() - timestamp.getMillis()) / (60 * 1000) % 60) > 60 ) {
            return false;
        }

        // validate password with database
        User user = userRepository.findByEmailAddress(email);

        if (user == null) {
            return false;
        }

        return Password.checkPassword(password, user.getPassword());
    }

    /**
     * Creates a token based on the current time, email and password of the user.
     * @param tokenRequest the requestobject with the email and password of the user
     * @return a authentication token
     * @throws UnsupportedEncodingException is thrown if the email and password cannot be encoded with UTF-8 encoding
     * @throws IllegalArgumentException is thrown if the userdata is not correct (bad password or email)
     */
    public static String CreateToken(TokenRequest tokenRequest) throws UnsupportedEncodingException, IllegalArgumentException {
        // validate request before providing a token
        // validate password with database
        User user = userRepository.findByEmailAddress(tokenRequest.email);

        if (user == null || !Password.checkPassword(tokenRequest.password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String authString = tokenRequest.email + ":" + tokenRequest.password + ":" + DateTime.now();

        byte[] encodedString = Base64.encodeBase64(authString.getBytes());

        String returnValue;

        try {
            returnValue = new String(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Combination of username/password could not be encoded, please contact support");
        }

        return returnValue;
    }

    /**
     * Hacky implementation to get the user from an authentication token
     * @param authentication the auth token
     * @return the user
     */
    public static User getUserFromToken(List<String> authentication) {

        String authenticationStringEncoded = authentication.get(0);
        byte[] authByteArray = Base64.decodeBase64(authenticationStringEncoded.getBytes());

        String authString;
        try {
            authString = new String(authByteArray, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // We can assume the token string is correct, as it is validated before this point.
            return null;
        }

        Map<String, String> authenticationElements = new HashMap<String, String>();

        // We can assume here that the string is always in the right order.
        String[] stringArray = authString.split(":");

        String email = stringArray[0];

        return userRepository.findByEmailAddress(email);
    }
}
