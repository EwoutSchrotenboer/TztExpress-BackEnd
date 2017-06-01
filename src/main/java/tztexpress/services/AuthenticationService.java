package tztexpress.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationService {

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

        String username = stringArray[0];
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

        if (((DateTime.now().getMillis() - timestamp.getMillis()) / (60 * 1000) % 60) > 30 ) {
            return false;
        }

        // TODO: DB AUTH
        return false;
    }

    public static String CreateToken(String userName, String password) throws UnsupportedEncodingException {
        String authString = userName + ":" + password + ":" + DateTime.now();

        byte[] encodedString = Base64.encodeBase64(authString.getBytes());

        String returnValue;

        try {
            returnValue = new String(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        return returnValue;
    }
}
