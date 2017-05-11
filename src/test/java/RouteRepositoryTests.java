import com.google.maps.errors.ApiException;
import models.CourierChoiceModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ewout on 11-5-2017.
 */
public class RouteRepositoryTests {
    @Test
    public void CalculateRouteTest() throws InterruptedException, ApiException, IOException {

        RouteRepository testRouteRepository = new RouteRepository();

        // arrange
        String origin = "Campus 2-6, 8017 CA Zwolle, Netherlands";
        String destination = "Dam 1, 1012 JS Amsterdam, Netherlands";

        // act
        CourierChoiceModel response = testRouteRepository.CalculateRoute(origin, destination);
        // assert
        Assert.assertEquals("Treinkoerier", response.Type);




        // arrange
        String origin2 = "Campus 2-6, 8017 CA Zwolle, Netherlands";
        String destination2 = "Philidorstraat 3, 8031 VW Zwolle, Netherlands";

        // act
        CourierChoiceModel response2 = testRouteRepository.CalculateRoute(origin2, destination2);

        // assert
        Assert.assertEquals("Vrachtwagenkoerier", response2.Type);
    }
}
