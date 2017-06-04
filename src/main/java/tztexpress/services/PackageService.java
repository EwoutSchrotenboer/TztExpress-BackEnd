package tztexpress.services;

import com.sun.media.sound.InvalidDataException;
import tztexpress.enumerators.CourierTypes;
import tztexpress.models.Address;
import tztexpress.models.AddressModel;
import tztexpress.models.Package;
import tztexpress.models.PackageRequestModel;
import tztexpress.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {

    private PackageRepository packageRepository;
    private AddressService addressService;

    @Autowired
    public PackageService(PackageRepository packageRepository, AddressService addressService) {
        this.packageRepository = packageRepository;
        this.addressService = addressService;
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
        // todo:
        // check databases if addresses are available for origin and destination. (one courier, two addresses, three couriers, six addresses)
        if (packageRequestModel.CourierChoiceModel == null) {
            throw new InvalidDataException("Route information is not provided.");
        }

        // Create package
        Package pack = new Package();
        pack.setValue(packageRequestModel.value);
        pack.setWeight(packageRequestModel.weight);
        pack.setDetails(packageRequestModel.details);

        // handle 1 courier + 2 addresses
        AddressModel originAddress = this.convertStringToAddress(packageRequestModel.CourierChoiceModel.OriginAddress);
        AddressModel destinationAddress = this.convertStringToAddress(packageRequestModel.CourierChoiceModel.DestinationAddress);

        // Check if database contains addresses, if not, add them.
        Address originDbAddress = this.addressService.findOrCreateAddress(originAddress);
        Address destinationDbAddress = this.addressService.findOrCreateAddress(destinationAddress);

        pack.setOriginAddressId(originDbAddress.getId());
        pack.setDestinationAddressId(destinationDbAddress.getId());

        String courierType = packageRequestModel.CourierChoiceModel.Type;

        if (courierType.equals(CourierTypes.TRAINCOURIER.toString())) {
            // handle logic for traincouriers
            AddressModel firstTrainStationAddress = this.convertStringToAddress(packageRequestModel.CourierChoiceModel.Courier.OriginCourier.DestinationAddress);
            AddressModel lastTrainStationAddress = this.convertStringToAddress(packageRequestModel.CourierChoiceModel.Courier.DestinationCourier.OriginAddress);

            Address firstTrainStationDbAddress = this.addressService.findOrCreateAddress(firstTrainStationAddress);
            Address lastTrainStationDbAddress = this.addressService.findOrCreateAddress(lastTrainStationAddress);

        } else {

        }
        // if not: create addresses with the available information.
        // create package
        // create shipments
        // link external couriers
        // set up list for emails to send
        // (mock) send(ing) emails
        // return package so the Id can be used for further reference
        return null;
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
