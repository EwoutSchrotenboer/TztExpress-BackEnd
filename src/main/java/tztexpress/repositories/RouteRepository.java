package tztexpress.repositories;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.springframework.stereotype.Repository;
import tztexpress.enumerators.BiggestCities;
import tztexpress.enumerators.CourierTypes;
import tztexpress.enumerators.Status;
import tztexpress.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The routerepository contains the code for the handling of the routecalculations
 */
@Repository
public class RouteRepository {
    private GeoApiContext context = new GeoApiContext();
    private static final Double TRAINCOURIERPRICE = 6.0;
    private static final int MAXIMUMBICYCLEDISTANCECHEAPEST = 4000;

    /**
     * Initialize the repositories.RouteRepository
     */
    public RouteRepository() {
        this.context = context
                .setQueryRateLimit(3)
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS)
                .setApiKey("AIzaSyBdz5GYyufanHNIWY8QKnRqLKZuVlnxRYc");
    }

    /**
     * Calculates the cheapest courier and returns it, for a given origin and destination.
     *
     * @param origin      the origin address in string format
     * @param destination the destination address in string format
     * @return a CourierChoiceModel with status for the cheapest courier for the given route
     */
    public CourierChoiceModel CalculateRoute(String origin, String destination) throws InterruptedException, ApiException, IOException {
        CourierChoiceModel returnValue = new CourierChoiceModel();

        CourierModel cheapestCourier = this.GetCheapestCourier(origin, destination, true);

        if(cheapestCourier != null && cheapestCourier.Type != null) {
            returnValue.Courier = cheapestCourier;
            returnValue.Type = cheapestCourier.Type.toString();
            returnValue.Status = Status.OK.toString();
            returnValue.Courier.Cost *= 1.2;
            returnValue.Cost = cheapestCourier.Cost.toString();

            returnValue.OriginAddress = cheapestCourier.OriginAddress;
            returnValue.DestinationAddress = cheapestCourier.DestinationAddress;
        } else {
            returnValue.Status = Status.ERROR.toString();
            returnValue.AdditionalInformation = "Courier could not be calculated, please contact support.";
        }

        return returnValue;
    }

    /**
     * Gets the cheapest courier for a given origin and destination
     *
     * @param origin      the origin address in string format
     * @param destination the destination address in string format
     * @param fullRoute   whether the route is the full route, this is necessary for the bicycle route and to prevent
     *                    an infinite loop for the train courier
     * @return a couriermodel for the cheapest courier for the given route
     */
    private CourierModel GetCheapestCourier(String origin, String destination, boolean fullRoute) throws InterruptedException, ApiException, IOException {

        DirectionsResult distanceBicycle = this.GetDistance(origin, destination, TravelMode.BICYCLING);

        // TODO: Handle exceptions, for now, return null
        if( distanceBicycle.routes == null) {
            return null;
        }

        CourierModel bicycleCourier = this.BicycleCourierRoute(distanceBicycle, fullRoute);
        DirectionsResult distanceDriving = this.GetDistance(origin, destination, TravelMode.DRIVING);

        if( distanceDriving.routes == null) {
            return null;
        }

        CourierModel messengerCourier = this.DrivingCourierRoute(distanceDriving, CourierTypes.MESSENGERCOURIER);
        CourierModel transportCourier = this.DrivingCourierRoute(distanceDriving, CourierTypes.TRANSPORTCOURIER);

        ArrayList<CourierModel> courierModelArrayList = new ArrayList<>();
        courierModelArrayList.add(messengerCourier);
        courierModelArrayList.add(transportCourier);
        courierModelArrayList.add(bicycleCourier);

        // if it's not the full route, the route is for the traincourier; skip
        if (fullRoute) {
            DirectionsResult distanceTransit = this.GetDistance(origin, destination, TravelMode.TRANSIT);

            if (distanceTransit == null) {
                return null;
            }

            CourierModel trainCourier = this.TrainCourierRoute(distanceTransit);
            courierModelArrayList.add(trainCourier);
        }

        return this.CalculateCheapestCourier(courierModelArrayList);
    }

    /**
     * Calculates the train route, with the origin to first station and last station to destination subroutes.
     *
     * @param distanceTransit the DirectionsResult for the transit route
     * @return A couriermodel with the calculated prices for the parts of the routes as well as the total price
     */
    private CourierModel TrainCourierRoute(DirectionsResult distanceTransit) throws InterruptedException, ApiException, IOException {
        CourierModel returnValue = new CourierModel(CourierTypes.TRAINCOURIER);

        String origin = distanceTransit.routes[0].legs[0].startAddress;
        String destination = distanceTransit.routes[0].legs[0].endAddress;

        ArrayList<DirectionsStep> directionTransitSteps = new ArrayList<>();
        DirectionsStep[] steps = distanceTransit.routes[0].legs[0].steps;

        for (DirectionsStep step : steps) {
            if (step.travelMode == TravelMode.TRANSIT) {
                if (step.transitDetails.line.vehicle.type.name().equals("HEAVY_RAIL"))
                    directionTransitSteps.add(step);
            }
        }

        // If train is available:
        if (directionTransitSteps.size() > 0) {
            String firstTrainStation = directionTransitSteps.get(0).transitDetails.departureStop.name + " Station";
            String lastTrainStation = directionTransitSteps.get(directionTransitSteps.size() - 1).transitDetails.arrivalStop.name + " Station";

            CourierModel cheapestOriginCourier = this.GetCheapestCourier(origin, firstTrainStation, false);
            CourierModel cheapestDestinationCourier = this.GetCheapestCourier(lastTrainStation, destination, false);

            // combine prices for first/train/last leg, then compare them with the other prices
            if(cheapestOriginCourier != null && cheapestOriginCourier.Cost != null &&
               cheapestDestinationCourier != null && cheapestDestinationCourier.Cost != null) {
                returnValue.Cost = TRAINCOURIERPRICE + cheapestOriginCourier.Cost + cheapestDestinationCourier.Cost;
                returnValue.OriginCourier = cheapestOriginCourier;
                returnValue.DestinationCourier = cheapestDestinationCourier;

                returnValue.TrainCourier = new CourierModel(CourierTypes.TRAINCOURIER, TRAINCOURIERPRICE);
                returnValue.TrainCourier.OriginAddress = firstTrainStation;
                returnValue.TrainCourier.DestinationAddress = lastTrainStation;
                returnValue.Available = true;
            } else {
                returnValue.Available = false;
            }
        } else {
            returnValue.Available = false;
        }

        returnValue.OriginAddress = origin;
        returnValue.DestinationAddress = destination;
        return returnValue;
    }

    /**
     * Calculates the driving courier routes. This depends on the couriertype.
     *
     * @param distanceDriving the DirectionsResult for the driving route
     * @param courierType whether the courier is a messenger or a transport courier
     * @return A couriermodel with the calculated cost for the given route
     */
    private CourierModel DrivingCourierRoute(DirectionsResult distanceDriving, CourierTypes courierType) {
        CourierModel returnValue = new CourierModel(courierType);

        if (returnValue.Type.equals(CourierTypes.MESSENGERCOURIER)) {
            returnValue.Cost = this.CalculateMessengerCourierCosts(distanceDriving);
        } else if (returnValue.Type.equals(CourierTypes.TRANSPORTCOURIER)) {
            returnValue.Cost = this.CalculateTransportCourierCosts(distanceDriving);
        }

        if (returnValue.Cost != null) {
            returnValue.Available = true;
        }

        returnValue.OriginAddress = distanceDriving.routes[0].legs[0].startAddress;
        returnValue.DestinationAddress = distanceDriving.routes[0].legs[0].endAddress;

        return returnValue;
    }



    /**
     * Calculates the bicycle courier route. This depends on whether the origin address is in one of the 25 biggest
     * cities.
     * @param distanceBicycling the DirectionsResult for the bicycling route
     * @param fullRoute   whether the route is complete or one of the legs of the trainroute
     * @return A couriermodel with the calculated cost for the given route
     */
    private CourierModel BicycleCourierRoute(DirectionsResult distanceBicycling, boolean fullRoute) {
        CourierModel returnValue = new CourierModel(CourierTypes.BICYCLECOURIER);

        returnValue.OriginAddress = distanceBicycling.routes[0].legs[0].startAddress;
        returnValue.DestinationAddress = distanceBicycling.routes[0].legs[0].endAddress;

        boolean originBigCity = this.CheckBiggestCities(returnValue.OriginAddress);

        if (originBigCity) {
            returnValue.Available = true;

            if (fullRoute && distanceBicycling.routes[0].legs[0].distance.inMeters < MAXIMUMBICYCLEDISTANCECHEAPEST) {
                // If the route is within one of the 25 biggest cities and shorter than 4km, this option is always
                // the cheapest. Skip the other API-calls and return the cycling route.
                returnValue.Cost = this.CalculateBicycleCourierCosts(distanceBicycling);
                returnValue.IsCheapestOption = true;
                return returnValue;

            } else {
                returnValue.Cost = this.CalculateBicycleCourierCosts(distanceBicycling);
            }
        } else {
            returnValue.Available = false;
        }

        return returnValue;
    }

    /**
     * Calculates the messenger courier cost given the business rules
     *
     * @param distanceBicycling the Google Directions API response
     * @return the cost of the transport in euro's
     */
    private Double CalculateBicycleCourierCosts(DirectionsResult distanceBicycling) {
        double returnValue = 0.00;
        long distanceInMeters = distanceBicycling.routes[0].legs[0].distance.inMeters;

        if (distanceInMeters < 4000) {
            returnValue = 9.00;
        } else if (distanceInMeters < 8000) {
            returnValue = 14.00;
        } else if (distanceInMeters < 12000) {
            returnValue = 19.00;
        } else if (distanceInMeters >= 12000) {
            long extraDistance = distanceInMeters - 12000;
            double extraDistanceInKm = extraDistance / 1000.0;
            returnValue = 15.00 + (extraDistanceInKm * 0.56);
        }

        return returnValue;
    }

    /**
     * Calculates the messenger courier cost given the business rules
     *
     * @param distanceDriving the Google Directions API response
     * @return the cost of the transport in euro's
     */
    private Double CalculateMessengerCourierCosts(DirectionsResult distanceDriving) {
        long distanceInMeters = distanceDriving.routes[0].legs[0].distance.inMeters;

        double returnValue = 12.50;

        if (distanceInMeters > 40000) {
            long extraDistance = distanceInMeters - 40000;
            double extraDistanceInKm = extraDistance / 1000.0;

            returnValue += extraDistanceInKm * 0.40;
        }

        return returnValue;

    }

    /**
     * Calculates the transport courier cost given the business rules
     *
     * @param distanceDriving the Google Directions API response
     * @return the cost of the transport in euro's
     */
    private Double CalculateTransportCourierCosts(DirectionsResult distanceDriving) {

        long distanceInMeters = distanceDriving.routes[0].legs[0].distance.inMeters;

        double returnValue = 10.00;

        if (distanceInMeters > 25000) {
            long extraDistance = distanceInMeters - 25000;
            double extraDistanceInKm = extraDistance / 1000.0;

            returnValue += extraDistanceInKm * 0.39;
        }

        return returnValue;
    }

    /**
     * The call to the Google Maps Directions API,
     *
     * @param origin      the origin address in string format
     * @param destination the destination address in string format
     * @param travelMode  the mode of travel (BICYCLING, DRIVING, TRANSIT, WALKING, UNKNOWN)
     * @return A Directionsresult containing the parsed JSON from the Google Maps API
     */
    private DirectionsResult GetDistance(String origin, String destination, TravelMode travelMode) throws InterruptedException, ApiException, IOException {
        DirectionsResult returnValue = new DirectionsResult();
        try {
            returnValue = DirectionsApi.newRequest(context)
                    .origin(origin)
                    .destination(destination)
                    .mode(travelMode)
                    .await();
        } catch (ApiException | IOException | InterruptedException ex) {
            // TODO: Next implementation - add exceptionlogging to database
            throw ex;
        }

        return returnValue;
    }

    /**
     * Checks if the given string contains one of the 25 biggest cities in the Netherlands
     * The spaces are removed because the enumerator cities do not contain spaces. (E.G. DENHAAG instead of Den Haag)
     *
     * @param s The inputstring
     * @return boolean that indicates whether the input contained one of the 25 biggest cities
     */
    private boolean CheckBiggestCities(String s) {
        for (BiggestCities city : BiggestCities.values()) {
            if (s.replace(" ", "").toLowerCase().contains(city.toString().toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Calculates the cheapest courier from a given list of couriers
     *
     * @param courierList the list of couriers
     * @return the cheapest courier
     */
    private CourierModel CalculateCheapestCourier(ArrayList<CourierModel> courierList) {
        CourierModel cheapestCourier = new CourierModel(null);

        for (CourierModel courier : courierList) {
            if (courier.Available && cheapestCourier.Type == null) {
                cheapestCourier = courier;
            }
            if (courier.Available && courier.Cost != null && (cheapestCourier.Cost == null || courier.Cost <= cheapestCourier.Cost)) {
                cheapestCourier = courier;
            }
        }

        return cheapestCourier;
    }
}
