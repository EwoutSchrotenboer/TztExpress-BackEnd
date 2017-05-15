package tztexpress.controllers;

import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.repositories.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private RouteRepository routeRepository = new RouteRepository();

    @RequestMapping(method = RequestMethod.POST)
    public GenericResult<CourierChoiceModel> calculate(@RequestBody RouteRequest request) {
        if (request.origin != null && request.destination != null) {
            try {
                return GenericResultHandler.GenericResult(this.routeRepository.CalculateRoute(request.origin, request.destination));
            } catch (Exception e) {
                return GenericResultHandler.GenericExceptionResult(e);
            }

        }
        else {
            return GenericResultHandler.GenericExceptionResult("Invalid request, origin or destination is missing.");
        }
    }
}
