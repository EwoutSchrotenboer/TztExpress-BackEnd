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
    public boolean ValidateToken(List<String> authentication) {
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

        if (((DateTime.now().getMillis() - timestamp.getMillis()) / (60 * 1000) % 60) < 30 ) {
            // TODO: Verify the database account information
            //if(this.databaseRepository.VerifyLogin(username, password)) {
            return true;
            //}
        }

        return false;
    }
}
