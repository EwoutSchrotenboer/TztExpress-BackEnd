package tztexpress.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.GenericResult;
import tztexpress.models.TokenRequest;
import tztexpress.services.AuthenticationService;

/**
 * This controller handles all authenticationtokens, creation/request and validation.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    /**
     * Requests a token for a specific username and password, the token will stay valid for 60 minutes
     * @param request the request containing the username and password
     * @return the token as a string in a generic result
     */
    @RequestMapping(value = "request", method = RequestMethod.POST)
    public GenericResult<String> requestToken(@RequestBody TokenRequest request) {
        try {
            String returnValue = AuthenticationService.CreateToken(request);
            return GenericResultHandler.GenericResult(returnValue);
        } catch (Exception ex) {
            return GenericResultHandler.GenericExceptionResult(ex);
        }
    }

    /**
     * Will validate the token, to check if it is still valid. DEPRECATED, WILL BE REMOVED
     * @param headers the authentication header
     * @return a string with information whether the token is valid or not
     */
    @RequestMapping(value = "validatetoken", method = RequestMethod.POST)
    public String validateTokenRequest(@RequestHeader HttpHeaders headers) {
        // validate input with database
        boolean isAuthenticated = AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"));

        if (isAuthenticated) {
            return "Authenticated";
        } else {
            return "Not authenticated";
        }
    }
}
