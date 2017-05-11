import com.google.maps.errors.ApiException;
import models.CourierChoiceModel;
import models.RouteRequest;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/route")
public class RouteController {

    private RouteRepository routeRepository = new RouteRepository();

    @RequestMapping(method = RequestMethod.POST)
    public CourierChoiceModel calculate(@RequestBody RouteRequest request) throws InterruptedException, ApiException, IOException {
        if (request.origin != null && request.destination != null) {
            return this.routeRepository.CalculateRoute(request.origin, request.destination);
        }
        else {
            CourierChoiceModel returnValue = new CourierChoiceModel();
            returnValue.Status = "Error, incomplete request";
            return returnValue;
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RouteController.class, args);
    }


}