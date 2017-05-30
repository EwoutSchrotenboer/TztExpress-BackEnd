package tztexpress.controllers;

import org.apache.tomcat.jni.Time;
import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.repositories.*;
import tztexpress.services.*;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {
    private RouteRepository routeRepository = new RouteRepository();
    private AuthenticationService authenticationService = new AuthenticationService();

    @RequestMapping(method = RequestMethod.POST)
    public GenericResult<CourierChoiceModel> calculate(@RequestHeader HttpHeaders headers, @RequestBody RouteRequest request) {

        boolean isValid = this.authenticationService.ValidateToken(headers.getValuesAsList("Authentication"));

        if (isValid) {
            if (request.origin != null && request.destination != null) {
                try {
                    return GenericResultHandler.GenericResult(this.routeRepository.CalculateRoute(request.origin, request.destination));
                } catch (Exception e) {
                    return GenericResultHandler.GenericExceptionResult(e);
                }
            } else {
                return GenericResultHandler.GenericExceptionResult("Invalid request, origin or destination is missing.");
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Authentication not valid");
        }
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public String testRequest(@RequestHeader HttpHeaders headers, @RequestBody RouteRequest request) {
        String convertString = request.origin + request.destination;

        String authString = request.origin + ":" + request.destination + ":" + DateTime.now();

        byte[] encodedString = Base64.encodeBase64(authString.getBytes());

        String returnValue;

        try {
             returnValue = new String(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "Error, no valid authenticationtoken";
        }

        return returnValue;


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
