import com.google.maps.errors.ApiException;
import tztexpress.models.CourierChoiceModel;
import tztexpress.enumerators.CourierTypes;
import org.junit.Assert;
import org.junit.Test;
import tztexpress.repositories.RouteRepository;

import java.io.IOException;

/**
 * Created by Ewout on 11-5-2017.
 */
public class RouteRepositoryTests {
    @Test
    public void CalculateTrainRoute() throws InterruptedException, ApiException, IOException {
        // arrange
        RouteRepository testRouteRepository = new RouteRepository();
        String origin = "Campus 2-6, 8017 CA Zwolle, Netherlands";
        String destination = "Dam 1, 1012 JS Amsterdam, Netherlands";

        // act
        CourierChoiceModel response = testRouteRepository.CalculateRoute(origin, destination);

        // assert
        Assert.assertEquals(CourierTypes.TRAINCOURIER.toString(), response.Type);
    }

    @Test
    public void CalculateTransportRoute() throws InterruptedException, ApiException, IOException {
        // arrange
        RouteRepository testRouteRepository = new RouteRepository();
        String origin = "Campus 2-6, 8017 CA Zwolle, Netherlands";
        String destination = "Philidorstraat 3, 8031 VW Zwolle, Netherlands";

        // act
        CourierChoiceModel response = testRouteRepository.CalculateRoute(origin, destination);

        // assert
        Assert.assertEquals(CourierTypes.TRANSPORTCOURIER.toString(), response.Type);
    }

    // TODO: Calculate messenger route
    // TODO: Calculate bicycle route
}
