package tztexpress.services;

import com.sun.media.sound.InvalidDataException;
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

@Service
public class AuthenticationService {

    private static UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Hacky hacky hack, very fragile, treat with care
    // TODO: If everything is validated, return a new token.
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

        // if token is less than 30 minutes old, continue
        if (((DateTime.now().getMillis() - timestamp.getMillis()) / (60 * 1000) % 60) > 30 ) {
            return false;
        }

        // validate password with database
        User user = userRepository.findByEmailAddress(email);

        if (user == null) {
            return false;
        }

        return Password.checkPassword(password, user.getPassword());
    }

    public static String CreateToken(TokenRequest tokenRequest) throws UnsupportedEncodingException, InvalidDataException {
        // validate request before providing a token
        // validate password with database
        User user = userRepository.findByEmailAddress(tokenRequest.Email);

        if (user == null || !Password.checkPassword(tokenRequest.Password, user.getPassword())) {
            throw new InvalidDataException("Invalid email or password");
        }

        String authString = tokenRequest.Email + ":" + tokenRequest.Password + ":" + DateTime.now();

        byte[] encodedString = Base64.encodeBase64(authString.getBytes());

        String returnValue;

        try {
            returnValue = new String(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new InvalidDataException("Combination of username/password could not be encoded, please contact support");
        }

        return returnValue;
    }
}
