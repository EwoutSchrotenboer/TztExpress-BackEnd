package tztexpress.services;

import tztexpress.enumerators.CourierTypes;
import tztexpress.models.*;
import tztexpress.models.Package;
import tztexpress.repositories.ExternalCourierRepository;
import tztexpress.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {

    private PackageRepository packageRepository;
    private ShipmentService shipmentService;
    private AddressService addressService;
    private TrainCourierService trainCourierService;
    private UserService userService;
    private ExternalCourierRepository externalCourierRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository,
                          AddressService addressService,
                          ShipmentService shipmentService,
                          TrainCourierService trainCourierService,
                          ExternalCourierRepository externalCourierRepository,
                          UserService userService) {
        this.packageRepository = packageRepository;
        this.addressService = addressService;
        this.shipmentService = shipmentService;
        this.trainCourierService = trainCourierService;
        this.externalCourierRepository = externalCourierRepository;
        this.userService = userService;
    }

    public List<Package> listAll() {
        List<Package> packages = new ArrayList<>();
        packageRepository.findAll().forEach(packages::add);
        return packages;
    }

    public Package getById(Long id) {

        return packageRepository.findOne(id);
    }

    public Package saveOrUpdate(Package _package) {
        return packageRepository.save(_package);
    }

    public void delete(Long id) {
        packageRepository.delete(id);
    }

    public Package createPackage(PackageRequestModel packageRequestModel, User user) throws IllegalArgumentException {
        if (packageRequestModel.courierchoicemodel == null) {
            throw new IllegalArgumentException("Route information is not provided.");
        }
        CourierChoiceModel courierChoiceModel = packageRequestModel.courierchoicemodel;
        CourierModel courier = courierChoiceModel.courier;

        // Create package
        Package pack = new Package();
        pack.setUserId(user.getId());
        pack.setValue(packageRequestModel.value);
        pack.setWeight(packageRequestModel.weight);
        pack.setDetails(packageRequestModel.details);

        // handle 1 courier + 2 addresses
        AddressModel originAddress = this.convertStringToAddress(courierChoiceModel.originaddress);
        AddressModel destinationAddress = this.convertStringToAddress(courierChoiceModel.destinationaddress);

        // Check if database contains addresses, if not, add them.
        Address originDbAddress = this.addressService.findOrCreateAddress(originAddress);
        Address destinationDbAddress = this.addressService.findOrCreateAddress(destinationAddress);

        pack.setOriginAddressId(originDbAddress.getId());
        pack.setDestinationAddressId(destinationDbAddress.getId());

        // save package to get the Id
        Package dbPackage = this.packageRepository.save(pack);

        String courierType = courierChoiceModel.type;


        // Create shipments
        List<Shipment> shipmentList = new ArrayList<>();

        if (courierType.equals(CourierTypes.TRAINCOURIER.toString())) {
            // handle logic for traincouriers
            AddressModel firstTrainStationAddress = this.convertStringToAddress(courierChoiceModel.courier.origincourier.destinationaddress);
            AddressModel lastTrainStationAddress = this.convertStringToAddress(courierChoiceModel.courier.destinationcourier.originaddress);

            Address firstTrainStationDbAddress = this.addressService.findOrCreateAddress(firstTrainStationAddress);
            Address lastTrainStationDbAddress = this.addressService.findOrCreateAddress(lastTrainStationAddress);

            String originCourierType = courierChoiceModel.courier.origincourier.type.toString();
            String destinationCourierType = courierChoiceModel.courier.destinationcourier.type.toString();
            // Get the three couriers
            long originCourierId = this.externalCourierRepository.findExternalCourierByType(originCourierType).get(0).getId();
            long destinationCourierId = this.externalCourierRepository.findExternalCourierByType(destinationCourierType).get(0).getId();
            long trainCourierId = packageRequestModel.courierchoicemodel.courier.traincourier.traincourierdbid;

            // create three shipments
            Shipment originShipment = this.shipmentService.createShipment(originDbAddress.getId(), firstTrainStationDbAddress.getId(), dbPackage.getId(), courier.origincourier.cost.toString(), originCourierType, originCourierId);
            Shipment trainShipment = this.shipmentService.createShipment(firstTrainStationDbAddress.getId(), lastTrainStationDbAddress.getId(), dbPackage.getId(), courier.traincourier.cost.toString(), courierType, trainCourierId);
            Shipment destinationShipment = this.shipmentService.createShipment(lastTrainStationDbAddress.getId(), destinationDbAddress.getId(), dbPackage.getId(), courier.destinationcourier.cost.toString(), destinationCourierType, destinationCourierId);

            shipmentList.add(originShipment);
            shipmentList.add(destinationShipment);
            shipmentList.add(trainShipment);

        } else {
            // Get the courier
            String cost = courierChoiceModel.cost.toString();
            long courierId = this.externalCourierRepository.findExternalCourierByType(courierType).get(0).getId();
            // Create the shipment
            Shipment shipment = this.shipmentService.createShipment(originDbAddress.getId(), destinationDbAddress.getId(), dbPackage.getId(), cost, courierType, courierId);

            shipmentList.add(shipment);
        }


        // TODO: (mock) send(ing) emails
        // return package so the Id can be used for further reference
        return dbPackage;
    }

    // HACK HACK HACK
    // FRAGILE, TREAT WITH CARE
    public AddressModel convertStringToAddress(String addressString) throws IllegalArgumentException {
        AddressModel returnValue = new AddressModel();
        returnValue.address1 = new String();
        returnValue.address2 = new String();
        returnValue.zipcode = new String();
        returnValue.city = new String();

        // Google directions api returns addresses in the following format
        // Address,Zipcode + City,Country
        String[] stringArray = addressString.split(",");

        if(stringArray.length < 3) {
            throw new IllegalArgumentException("Could not convert the address to a model.");
        }

        String address = new String(); // = stringArray[0];
        String zipcodeCity = new String(); // = stringArray[1];
        // check here if address is the first, or the first two items. The zipcodeCity always starts with 4 digits.
        boolean zipcodeOccurred = false;
        for(String item : stringArray) {
            if(!zipcodeOccurred) {
                // check if the string contains a zipcode
                if(item.length() >= 4) {
                    char[] itemArray = item.trim().toCharArray();
                    if(Character.isDigit(itemArray[0]) &&
                       Character.isDigit(itemArray[1]) &&
                       Character.isDigit(itemArray[2]) &&
                       Character.isDigit(itemArray[3])) {
                        zipcodeOccurred = true;

                        zipcodeCity += item;
                        zipcodeCity += " ";
                    } else {
                        address += item;
                        address += " ";
                    }
                } else {
                    address += item;
                    address += " ";
                }
            } else {
                zipcodeCity += item;
                zipcodeCity += " ";
            }
        }

        address = address.trim();
        zipcodeCity = zipcodeCity.trim();

        // We can assume everything after the first numbers in Address should belong in address2
        boolean numbersOccurred = false;

        for(String subString : address.split(" ")) {

            if(!numbersOccurred) {
                if (this.isNumeric(subString)) {
                    numbersOccurred = true;
                }

                returnValue.address1 += subString + " ";
            } else {
                returnValue.address2 += subString + " ";
            }
        }

        returnValue.address1 = returnValue.address1.trim();
        returnValue.address2 = returnValue.address2.trim();

        // Then, we can split the row with the zipcode and the city, by getting the first 6 characters for the zipcode,
        // and the remaining characters for the city.
        boolean firstLoop = true;
        for(String subString : zipcodeCity.split(" ")) {

            if(returnValue.zipcode.length() < 7) {
                returnValue.zipcode += subString;

                if(firstLoop) {
                    returnValue.zipcode += " ";
                    firstLoop = false;
                }
            }
            else {
                returnValue.city += subString;
                returnValue.city += " ";
            }

        }

        returnValue.zipcode = returnValue.zipcode.trim();
        // Remove country if it is present.
        returnValue.city = returnValue.city.replace("Netherlands", "").trim();

        return returnValue;
    }

    private boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public PackageModel getPackageModel(Long id) {
        PackageModel returnValue = new PackageModel();
        Package pack = this.getById(id);
        returnValue.id = pack.getId();
        returnValue.details = pack.getDetails();
        returnValue.value = pack.getValue();
        returnValue.isDelivered = pack.getIsDelivered();

        returnValue.sender = this.userService.getById(pack.getUserId());
        returnValue.origin = this.addressService.getAddress(pack.getOriginAddressId());
        returnValue.destination = this.addressService.getAddress(pack.getDestinationAddressId());
        returnValue.shipments = this.shipmentService.getShipmentsForPackage(pack.getId());

        // get couriers from shipments
        for(Shipment s : returnValue.shipments) {
            if (s.getCouriertype() == CourierTypes.TRAINCOURIER.toString()) {
                returnValue.traincourier = this.trainCourierService.getById(s.getTraincourierId());
            } else {
                returnValue.externalcouriers.add(this.externalCourierRepository.findOne(s.getExternalcourierId()));
            }
        }

        return returnValue;

    }
}
