package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.enumerators.CourierTypes;
import tztexpress.models.*;
import tztexpress.models.Package;
import tztexpress.repositories.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This service provides models for database objects, to expand data or remove data (for example: passwords)
 * Class was created to prevent circular reference problems within the services.
 */
@Service
public class ModelProviderService {
    private PackageRepository packageRepository;
    private UserService userService;
    private AddressService addressService;
    private ExternalCourierRepository externalCourierRepository;
    private TraincourierRepository traincourierRepository;
    private ShipmentService shipmentService;
    private TraincourierRouteRepository traincourierRouteRepository;

    @Autowired
    public ModelProviderService(UserService userService,
                                PackageRepository packageRepository,
                                AddressService addressService,
                                ExternalCourierRepository externalCourierRepository,
                                ShipmentService shipmentService,
                                TraincourierRepository traincourierRepository,
                                TraincourierRouteRepository traincourierRouteRepository) {
        this.userService = userService;
        this.packageRepository = packageRepository;
        this.addressService = addressService;
        this.externalCourierRepository = externalCourierRepository;
        this.shipmentService = shipmentService;
        this.traincourierRepository = traincourierRepository;
        this.traincourierRouteRepository = traincourierRouteRepository;

    }

    /**
     * Creates a traincouriermodel from a traincourier
     * @param traincourier the object to convert to a model
     * @return the traincourier model
     */
    public TraincourierModel TraincourierToModel(Traincourier traincourier) {
        TraincourierModel returnValue = new TraincourierModel();
        List<Package> packages = this.packageRepository.getPackagesForTrainCourier(traincourier.getId());
        List<TraincourierRoute> routes = this.traincourierRouteRepository.getRoutesForTrainCourier(traincourier.getId());
        returnValue.email = traincourier.getEmail();
        returnValue.identification = traincourier.getIdentification();
        returnValue.vogapproved = traincourier.getVogApproved();
        returnValue.id = traincourier.getId();
        returnValue.user = this.UserToPackageModel(this.userService.getById(traincourier.getUserId()));

        returnValue.packages = new ArrayList<>();
        for(Package pack : packages) {
            returnValue.packages.add(this.PackageToModel(pack));
        }

        returnValue.routes = new ArrayList<>();
        for(TraincourierRoute route : routes) {
            returnValue.routes.add(this.TraincourierRouteToModel(route));
        }

        return returnValue;
    }

    /**
     * Converts a traincourierRoute to a model
     * @param traincourierRoute the traincourierroute
     * @return the model
     */
    public TraincourierRouteModel TraincourierRouteToModel(TraincourierRoute traincourierRoute) {
        TraincourierRouteModel returnValue = new TraincourierRouteModel();
        returnValue.traincourierid = traincourierRoute.getTraincourierId();
        returnValue.weekday = traincourierRoute.getDay();
        returnValue.route = this.traincourierRouteRepository.getRouteById(traincourierRoute.getRouteId());

        return returnValue;
    }

    /**
     * Converts a user to a model
     * @param user the user
     * @return the usermodel
     */
    public UserModel UserToModel(User user) {
        UserModel returnValue = new UserModel();

        returnValue.email = user.getEmail();
        returnValue.firstname = user.getFirstName();
        returnValue.lastname = user.getLastName();
        returnValue.prefix = user.getPrefix();
        returnValue.email = user.getEmail().toLowerCase();
        returnValue.addressid = user.getAddressId();

        return returnValue;
    }

    /**
     * Converts a user to a packagemodel, with expanded information. For example, a full address
     * instead of an address-id
     * @param user the user
     * @return the packagemodel
     */
    public UserPackageModel UserToPackageModel(User user) {
        UserPackageModel returnValue = new UserPackageModel();

        returnValue.email = user.getEmail();
        returnValue.firstname = user.getFirstName();
        returnValue.lastname = user.getLastName();
        returnValue.prefix = user.getPrefix();
        returnValue.email = user.getEmail().toLowerCase();
        returnValue.address = addressService.getAddress(user.getAddressId());

        return returnValue;
    }

    /**
     * Converts a package to a packagemodel
     * @param pack the package - called pack because the name package is reserved.
     * @return the packagemodel
     */
    public PackageModel PackageToModel(Package pack) {
        PackageModel returnValue = new PackageModel();
        returnValue.id = pack.getId();
        returnValue.details = pack.getDetails();
        returnValue.value = pack.getValue();
        returnValue.isDelivered = pack.getIsDelivered();

        returnValue.sender = this.UserToPackageModel(this.userService.getById(pack.getUserId()));
        returnValue.origin = this.addressService.getAddress(pack.getOriginAddressId());
        returnValue.destination = this.addressService.getAddress(pack.getDestinationAddressId());
        returnValue.shipments = this.shipmentService.getShipmentsForPackage(pack.getId());

        // get couriers from shipment(s)
        returnValue.externalcouriers = new ArrayList<>();
        for(Shipment s : returnValue.shipments) {
            if (s.getCouriertype().equals(CourierTypes.TRAINCOURIER.toString())) {
                returnValue.traincourier = this.TraincourierToModel(this.traincourierRepository.findOne(s.getTraincourierId()));
            } else {
                returnValue.externalcouriers.add(this.externalCourierRepository.findOne(s.getExternalcourierId()));
            }
        }

        return returnValue;

    }
}
