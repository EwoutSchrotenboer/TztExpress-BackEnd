package tztexpress.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.ExceptionModel;
import tztexpress.models.GenericResult;
import tztexpress.models.RouteRequest;
import tztexpress.repositories.RouteRepository;
import tztexpress.services.AuthenticationService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService = new AuthenticationService();

    @RequestMapping(value = "request", method = RequestMethod.POST)
    public GenericResult<String> testRequest(@RequestHeader HttpHeaders headers, @RequestBody RouteRequest request) {
        try {
            String token = this.authenticationService.CreateToken("a", "b");
            return GenericResultHandler.GenericResult(token);
        } catch (UnsupportedEncodingException ex) {
            ExceptionModel exmodel = new ExceptionModel();
            return GenericResultHandler.GenericExceptionResult(ex);
        }
    }

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public String testRequest2(@RequestHeader HttpHeaders headers, @RequestBody String request) {
        // validate input with database
        boolean isAuthenticated = this.authenticationService.ValidateToken(headers.getValuesAsList("Authentication"));

        if (isAuthenticated) {
            return "Authenticated";
        } else {
            return "Not authenticated";
        }
    }
}
