package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.ExceptionModel;
import tztexpress.models.GenericResult;
import tztexpress.models.RouteRequest;
import tztexpress.models.TokenRequest;
import tztexpress.services.AuthenticationService;

import java.io.UnsupportedEncodingException;

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
