package tztexpress.services;

import com.sun.media.sound.InvalidDataException;
import tztexpress.enumerators.CourierTypes;
import tztexpress.models.*;
import tztexpress.models.Package;
import tztexpress.repositories.ExternalCourierRepository;
import tztexpress.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.repositories.ShipmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {

    private PackageRepository packageRepository;
    private ShipmentService shipmentService;
    private AddressService addressService;
    private TrainCourierService trainCourierService;
    private ExternalCourierRepository externalCourierRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository,
                          AddressService addressService,
                          ShipmentService shipmentService,
                          TrainCourierService trainCourierService,
                          ExternalCourierRepository externalCourierRepository) {
        this.packageRepository = packageRepository;
        this.addressService = addressService;
        this.shipmentService = shipmentService;
        this.trainCourierService = trainCourierService;
        this.externalCourierRepository = externalCourierRepository;
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

    public Package createPackage(PackageRequestModel packageRequestModel) throws InvalidDataException {
        if (packageRequestModel.CourierChoiceModel == null) {
            throw new InvalidDataException("Route information is not provided.");
        }
        CourierChoiceModel courierChoiceModel = packageRequestModel.CourierChoiceModel;
        CourierModel courier = courierChoiceModel.Courier;

        // Create package
        Package pack = new Package();
        pack.setValue(packageRequestModel.value);
        pack.setWeight(packageRequestModel.weight);
        pack.setDetails(packageRequestModel.details);

        // handle 1 courier + 2 addresses
        AddressModel originAddress = this.convertStringToAddress(courierChoiceModel.OriginAddress);
        AddressModel destinationAddress = this.convertStringToAddress(courierChoiceModel.DestinationAddress);

        // Check if database contains addresses, if not, add them.
        Address originDbAddress = this.addressService.findOrCreateAddress(originAddress);
        Address destinationDbAddress = this.addressService.findOrCreateAddress(destinationAddress);

        pack.setOriginAddressId(originDbAddress.getId());
        pack.setDestinationAddressId(destinationDbAddress.getId());

        // save package to get the Id
        Package dbPackage = this.packageRepository.save(pack);

        String courierType = courierChoiceModel.Type;


        // Create shipments
        List<Shipment> shipmentList = new ArrayList<>();

        if (courierType.equals(CourierTypes.TRAINCOURIER.toString())) {
            // handle logic for traincouriers
            AddressModel firstTrainStationAddress = this.convertStringToAddress(courierChoiceModel.Courier.OriginCourier.DestinationAddress);
            AddressModel lastTrainStationAddress = this.convertStringToAddress(courierChoiceModel.Courier.DestinationCourier.OriginAddress);

            Address firstTrainStationDbAddress = this.addressService.findOrCreateAddress(firstTrainStationAddress);
            Address lastTrainStationDbAddress = this.addressService.findOrCreateAddress(lastTrainStationAddress);

            String originCourierType = courierChoiceModel.Courier.OriginCourier.Type.toString();
            String destinationCourierType = courierChoiceModel.Courier.DestinationCourier.Type.toString();
            // Get the three couriers
            long originCourierId = this.externalCourierRepository.findExternalCourierByType(originCourierType).get(0).getId();
            long destinationCourierId = this.externalCourierRepository.findExternalCourierByType(destinationCourierType).get(0).getId();
            long trainCourierId = packageRequestModel.CourierChoiceModel.Courier.TrainCourier.TrainCourierDbId;

            // create three shipments
            Shipment originShipment = this.shipmentService.createShipment(originDbAddress.getId(), firstTrainStationDbAddress.getId(), dbPackage.getId(), courier.OriginCourier.Cost.toString(), originCourierType, originCourierId);
            Shipment trainShipment = this.shipmentService.createShipment(firstTrainStationDbAddress.getId(), lastTrainStationDbAddress.getId(), dbPackage.getId(), courier.TrainCourier.Cost.toString(), courierType, trainCourierId);
            Shipment destinationShipment = this.shipmentService.createShipment(lastTrainStationDbAddress.getId(), destinationDbAddress.getId(), dbPackage.getId(), courier.DestinationCourier.Cost.toString(), destinationCourierType, destinationCourierId);

            shipmentList.add(originShipment);
            shipmentList.add(destinationShipment);
            shipmentList.add(trainShipment);

        } else {
            // Get the courier
            String cost = courierChoiceModel.Cost.toString();
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
    public AddressModel convertStringToAddress(String addressString) throws InvalidDataException {
        AddressModel returnValue = new AddressModel();

        // Google directions api returns addresses in the following format
        // Address,Zipcode + City,Country
        String[] stringArray = addressString.split(",");

        if(stringArray.length < 3) {
            throw new InvalidDataException("Could not convert the address to a model.");
        }

        String address = stringArray[0];
        String zipcodeCity = stringArray[1];
        // check here if address is the first, or the first two items. The zipcodeCity always starts with 4 digits.
        boolean zipcodeOccurred = false;
        for(String item : stringArray) {
            if(!zipcodeOccurred) {
                // check if the string contains a zipcode
                if(item.length() >= 4) {
                    char[] itemArray = item.toCharArray();
                    if(Character.isDigit(itemArray[0]) &&
                       Character.isDigit(itemArray[1]) &&
                       Character.isDigit(itemArray[2]) &&
                       Character.isDigit(itemArray[3])) {
                        zipcodeOccurred = true;

                        zipcodeCity += item;
                        zipcodeCity += " ";
                    }
                }

                address += item;
                address += " ";
            } else {
                zipcodeCity += item;
                zipcodeCity += " ";
            }
        }

        address.trim();
        zipcodeCity.trim();

        // We can assume everything after the first numbers in Address should belong in address2
        boolean numbersOccurred = false;

        for(String subString : address.split(" ")) {

            if (this.isNumeric(subString)) {
                numbersOccurred = true;
            }

            if(numbersOccurred) {
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
                }
            }
            else {
                returnValue.city += subString;
                returnValue.city += " ";
            }
            firstLoop = false;
        }

        returnValue.zipcode = returnValue.zipcode.trim();
        returnValue.city = returnValue.city.trim();

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
}
