package tztexpress.services;

import org.springframework.stereotype.Service;
import tztexpress.enumerators.CourierTypes;
import tztexpress.models.*;
import tztexpress.models.Package;
import tztexpress.repositories.ExternalCourierRepository;
import tztexpress.repositories.PackageRepository;
import tztexpress.repositories.TraincourierRepository;

import java.util.ArrayList;

@Service
public class ModelProviderService {
    private PackageRepository packageRepository;
    private UserService userService;
    private AddressService addressService;
    private ExternalCourierRepository externalCourierRepository;
    private TraincourierRepository traincourierRepository;
    private ShipmentService shipmentService;

    public ModelProviderService(UserService userService,
                                PackageRepository packageRepository,
                                AddressService addressService,
                                ExternalCourierRepository externalCourierRepository,
                                ShipmentService shipmentService,
                                TraincourierRepository traincourierRepository) {
        this.userService = userService;
        this.packageRepository = packageRepository;
        this.addressService = addressService;
        this.externalCourierRepository = externalCourierRepository;
        this.shipmentService = shipmentService;
        this.traincourierRepository = traincourierRepository;
    }
    public TraincourierModel TraincourierToModel(Traincourier traincourier) {
        TraincourierModel returnValue = new TraincourierModel();
        returnValue.email = traincourier.getEmail();
        returnValue.identification = traincourier.getIdentification();
        returnValue.vogapproved = traincourier.getVogApproved();
        returnValue.id = traincourier.getId();
        returnValue.user = this.UserToPackageModel(this.userService.getById(traincourier.getUserId()));
        returnValue.packages = this.packageRepository.getPackagesForTrainCourier(traincourier.getId());
        return returnValue;
    }

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
