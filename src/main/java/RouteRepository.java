import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import models.BiggestCities;
import models.CourierModel;
import models.CourierChoiceModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RouteRepository {
    private GeoApiContext context = new GeoApiContext();

    private static final Double TRAINCOURIERPRICE = 6.0;
    private static final int MAXIMUMBICYCLEDISTANCECHEAPEST = 4000;

    public CourierChoiceModel CalculateRoute(String origin, String destination) throws InterruptedException, ApiException, IOException {
        // TODO: Handle exceptions
        //       Clean up class/refactor

        // init context
        this.InitializeContext();

        // first get the bicycling distance
        // but first check if the origin is in one of the 25 biggest cities
        boolean originBigCity = this.CheckBiggestCities(origin);
        boolean destinationBigCity = this.CheckBiggestCities(destination);
        CourierChoiceModel returnValue = new CourierChoiceModel();

        boolean bicycleAvailable = true;
        boolean trainAvailable = true;

        // define all possible couriers
        CourierModel trainCourier = new CourierModel("Treinkoerier");
        CourierModel cheapestFirstLegCourier = null;
        CourierModel cheapestLastLegCourier = null;

        CourierModel bicycleCourierComplete = new CourierModel("Fietskoerier");
        CourierModel messengerCourierComplete = new CourierModel("Bodekoerier");
        CourierModel transportCourierComplete = new CourierModel("Vrachtwagenkoerier");

        // First, calculate all regular routes
        // REFACTOR
        DirectionsResult distanceBicycling = this.GetDistance(origin, destination, TravelMode.BICYCLING);

        if (distanceBicycling.routes == null) {
            returnValue.Status = "Invalid";
            returnValue.AdditionalInformation = "Route could not be calculated";
            return returnValue;
        }

        if (originBigCity) {
            // todo: handle returnvalue without distance
            if (distanceBicycling.routes[0].legs[0].distance.inMeters <= MAXIMUMBICYCLEDISTANCECHEAPEST) {
                returnValue.Courier = bicycleCourierComplete;
                returnValue.Courier.Cost = this.CalculateBicycleCourierCosts(distanceBicycling);

                // todo: decide on returnstatuses and register them in enums
                returnValue.Status = "OK";

                return returnValue;

            } else {
                bicycleCourierComplete.Cost = this.CalculateBicycleCourierCosts(distanceBicycling);
            }
        } else {
            // if not in one of the 25 biggest cities, bikes are not available.
            bicycleAvailable = false;
        }

        // Calculate messenger courier && transport courier
        // REFACTOR
        DirectionsResult distanceDriving = this.GetDistance(origin, destination, TravelMode.DRIVING);

        // check status before calculating everything
        if (distanceDriving.routes == null) {
            returnValue.Status = "Invalid";
            returnValue.AdditionalInformation = "Route could not be calculated";
            return returnValue;
        }

        messengerCourierComplete.Cost = this.CalculateMessengerCourierCosts(distanceDriving);
        transportCourierComplete.Cost = this.CalculateTransportCourierCosts(distanceDriving);

        // Then, handle train courier logic

        CourierModel bicycleCourierFirstLeg = new CourierModel("Fietskoerier");
        CourierModel messengerCourierFirstLeg = new CourierModel("Bodekoerier");
        CourierModel transportCourierFirstLeg = new CourierModel("Vrachtwagenkoerier");

        CourierModel bicycleCourierLastLeg = new CourierModel("Fietskoerier");
        CourierModel messengerCourierLastLeg = new CourierModel("Bodekoerier");
        CourierModel transportCourierLastLeg = new CourierModel("Vrachtwagenkoerier");

        DirectionsResult distanceTransit = this.GetDistance(origin, destination, TravelMode.TRANSIT);

        // calculate first leg by checking distance between origin and first trainstation
        // create list of all train-steps in the directionresult
        ArrayList<DirectionsStep> directionTransitSteps = new ArrayList<>();
        DirectionsStep[] steps = distanceTransit.routes[0].legs[0].steps;

        for (int i = 0; i < steps.length; i++) {
            if (steps[i].travelMode == TravelMode.TRANSIT) {
                // TODO: Fix magic, explain thought process
                if (steps[i].transitDetails.line.vehicle.type.name().equals("HEAVY_RAIL"))
                directionTransitSteps.add(steps[i]);
            }
        }

        // TODO: Validate this, could be that the trainstations aren't getting picked up properly.
        if(directionTransitSteps.size() == 0) {
            trainAvailable = false;
        }

        if(trainAvailable) {
            String firstTrainStation = directionTransitSteps.get(0).transitDetails.departureStop.name + " Station";
            String lastTrainStation = directionTransitSteps.get(directionTransitSteps.size() - 1).transitDetails.arrivalStop.name + " Station";

            // calculate last leg by checking distance between destination and last trainstation
            DirectionsResult firstLegBicycle = this.GetDistance(origin, firstTrainStation, TravelMode.BICYCLING);
            DirectionsResult firstLegDriving = this.GetDistance(origin, firstTrainStation, TravelMode.DRIVING);

            if (originBigCity) {
                bicycleCourierFirstLeg.Cost = this.CalculateBicycleCourierCosts(firstLegBicycle);
            }
            messengerCourierFirstLeg.Cost = this.CalculateMessengerCourierCosts(firstLegDriving);
            transportCourierFirstLeg.Cost = this.CalculateTransportCourierCosts(firstLegDriving);

            DirectionsResult lastLegBicycle = this.GetDistance(destination, lastTrainStation, TravelMode.BICYCLING);
            DirectionsResult lastLegDriving = this.GetDistance(destination, lastTrainStation, TravelMode.DRIVING);

            if (destinationBigCity) {
                bicycleCourierLastLeg.Cost = this.CalculateBicycleCourierCosts(lastLegBicycle);
            }

            messengerCourierLastLeg.Cost = this.CalculateMessengerCourierCosts(lastLegDriving);
            transportCourierLastLeg.Cost = this.CalculateTransportCourierCosts(lastLegDriving);

            // Compare prices for first and last leg and choose:
            ArrayList<CourierModel> courierFirstLegList = new ArrayList<>();
            courierFirstLegList.add(bicycleCourierFirstLeg);
            courierFirstLegList.add(messengerCourierFirstLeg);
            courierFirstLegList.add(transportCourierFirstLeg);
            cheapestFirstLegCourier = this.CheapestCourier(courierFirstLegList);

            ArrayList<CourierModel> courierLastLegList = new ArrayList<>();
            courierLastLegList.add(bicycleCourierLastLeg);
            courierLastLegList.add(messengerCourierLastLeg);
            courierLastLegList.add(transportCourierLastLeg);
            cheapestLastLegCourier = this.CheapestCourier(courierLastLegList);

            // combine prices for first/train/last leg, then compare them with the other prices
            trainCourier.Cost = this.TRAINCOURIERPRICE + cheapestFirstLegCourier.Cost + cheapestLastLegCourier.Cost;
        }
        // Create final list:
        ArrayList<CourierModel> overallCheapestCourierList = new ArrayList<>();

        overallCheapestCourierList.add(messengerCourierComplete);
        overallCheapestCourierList.add(transportCourierComplete);

        if(bicycleAvailable) {
            overallCheapestCourierList.add(bicycleCourierComplete);
        }
        if(trainAvailable) {
            overallCheapestCourierList.add(trainCourier);
        }

        CourierModel cheapestCourier = this.CheapestCourier(overallCheapestCourierList);

        returnValue.Courier = cheapestCourier;
        returnValue.Type = cheapestCourier.Type;

        if (cheapestCourier.Type.equals("Treinkoerier")) {
            returnValue.CourierArrayList = new ArrayList<CourierModel>();
            returnValue.CourierArrayList.add(cheapestFirstLegCourier);

            // traincourier should show the fixed price here:
            trainCourier.Cost = this.TRAINCOURIERPRICE;
            returnValue.CourierArrayList.add(trainCourier);
            returnValue.CourierArrayList.add(cheapestLastLegCourier);
        }
        returnValue.Status = "OK";

        // TODO: Kortingen verrekenen, 20% toeslag voor TZT optellen
        // boven 1000 pakketten 10% korting op fietskoeriers/bodekoeriers/vrachtwagenkoeriers
        // Treinkoeriers zijn vast
        returnValue.Courier.Cost *= 1.2;
        return returnValue;
    }
    // use distance to calculate cost
    // return cost
    private Double CalculateBicycleCourierCosts(DirectionsResult distanceBicycling) {
        double returnValue = 0.00;
        long distanceInMeters = distanceBicycling.routes[0].legs[0].distance.inMeters;

        if (distanceInMeters < 4000) {
            returnValue = 9.00;
        } else if (distanceInMeters < 8000) {
            returnValue =  14.00;
        } else if (distanceInMeters < 12000) {
            returnValue =  19.00;
        } else if (distanceInMeters > 12000) {
            long extraDistance = distanceInMeters - 12000;
            double extraDistanceInKm = extraDistance / 1000.0;

            returnValue =  15.00 + (extraDistanceInKm * 0.56);
        }

        return returnValue;
    }

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

    private DirectionsResult GetDistance(String origin, String destination, TravelMode travelMode) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(origin)
                .destination(destination)
                .mode(travelMode)
                .await();
    }

    private boolean CheckBiggestCities(String s) {
        for (BiggestCities city : BiggestCities.values()) {
            if (s.replace(" ", "").toLowerCase().contains(city.toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private CourierModel CheapestCourier(ArrayList<CourierModel> courierList) {
        CourierModel cheapestCourier = new CourierModel("Comparison");

        for(CourierModel courier : courierList) {
            if (cheapestCourier.Type.equals("Comparison")) {
                cheapestCourier = courier;
            }
            if (courier.Cost != null && (cheapestCourier.Cost == null || courier.Cost <= cheapestCourier.Cost)) {
                cheapestCourier = courier;
            }
        }

        return cheapestCourier;
    }

    private void InitializeContext() {
        this.context = context
                .setQueryRateLimit(3)
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS)
                .setApiKey("AIzaSyBdz5GYyufanHNIWY8QKnRqLKZuVlnxRYc");
    }
}
