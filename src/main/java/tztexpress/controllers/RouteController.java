package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.repositories.*;
import tztexpress.services.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {
    private RouteRepository routeRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public GenericResult<CourierChoiceModel> calculate(@RequestHeader HttpHeaders headers, @RequestBody RouteRequest request) {

        boolean isValid = AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"));

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
}

