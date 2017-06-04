package tztexpress.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.GenericResult;
import tztexpress.models.TokenRequest;
import tztexpress.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    @RequestMapping(value = "request", method = RequestMethod.POST)
    public GenericResult<String> requestToken(@RequestBody TokenRequest request) {
        try {
            String returnValue = AuthenticationService.CreateToken(request);
            return GenericResultHandler.GenericResult(returnValue);
        } catch (Exception ex) {
            return GenericResultHandler.GenericExceptionResult(ex);
        }
    }

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
