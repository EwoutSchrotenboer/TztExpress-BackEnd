package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.Address;
import tztexpress.models.AddressModel;
import tztexpress.models.ChangeAddressModel;
import tztexpress.repositories.AddressRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The service to create or update addresses.
 */
@Service
public class AddressService {

    private static AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Gets an address from the database
     * @param addressModel the model for the query
     * @return the requested address
     * @throws IllegalArgumentException the addressmodel gets validated, if it contains wrong data, the exception is
     * thrown.
     */
    public Address getAddress(AddressModel addressModel) throws IllegalArgumentException {
        // check if address is complete:
        if (validAddress(addressModel)) {
            return addressRepository.findAddress(addressModel.address1, addressModel.address2, addressModel.zipcode, addressModel.city);
        }

        // It should not hit this return, as an exception is thrown.
        return null;
    }

    /**
     * Creates an address in the database with the provided information
     * @param addressModel the address model
     * @return the new address
     * @throws IllegalArgumentException the addressmodel gets validated, if it contains wrong data, the exception is
     * thrown.
     */
    public Address createAddress(AddressModel addressModel) throws IllegalArgumentException {
        if(validAddress(addressModel)) {
            Address newAddress = new Address();

            newAddress.setAddress1(addressModel.address1);

            if (addressModel.address2 != null) {
                newAddress.setAddress2(addressModel.address2);
            }

            newAddress.setZipcode(addressModel.zipcode);

            newAddress.setCity(addressModel.city);

            return addressRepository.save(newAddress);
        }

        // exception should be thrown, code should not be reached.
        return null;
    }

    /**
     * Updates an address in the database
     * @param addressModel the address model
     * @return the updated address model
     * @throws IllegalArgumentException the addressmodel gets validated, if it contains wrong data, the exception is
     * thrown.
     */
    public Address updateAddress(ChangeAddressModel addressModel) throws IllegalArgumentException {
        if(validAddress(addressModel)) {
            Address address = addressRepository.findOne(addressModel.id);

            if(address == null) {
                throw new IllegalArgumentException("Invalid address Id: " + addressModel.id);
            }

            if (addressModel.address1 != null) {
                address.setAddress1(addressModel.address1);
            }

            if (addressModel.address2 != null) {
                address.setAddress2(addressModel.address2);
            }

            if (addressModel.zipcode != null) {
                address.setZipcode(addressModel.zipcode);
            }

            if (addressModel.city != null) {
                address.setCity(addressModel.city);
            }

            return addressRepository.save(address);
        }

        // exception should be thrown, code should not be reached.
        return null;
    }

    /**
     * Validates the address model
     * @param addressModel the model to validate
     * @return whether the address is valid.
     * @throws IllegalArgumentException the addressmodel gets validated, if it contains wrong data, the exception is
     * thrown.
     */
    public static boolean validAddress(AddressModel addressModel) throws IllegalArgumentException {
        if (addressModel.address1 == null || addressModel.zipcode == null || addressModel.city == null) {
            String exceptionMessage = new String();

            exceptionMessage += (addressModel.address1 == null ? "Address1 not set" : addressModel.address1) + ", ";
            exceptionMessage += (addressModel.zipcode == null ? "Zipcode not set" : addressModel.zipcode) + ", ";
            exceptionMessage += (addressModel.city == null ? "City not set" : addressModel.city) + ".";

            throw new IllegalArgumentException(String.format("Invalid address: %s", exceptionMessage));
        }

        // validate zipcode
        Pattern regex = Pattern.compile("([0-9]{4})\\s*([A-Za-z]{2})");
        Matcher match = regex.matcher(addressModel.zipcode);

        if (match.matches()) {
            return true;
        } else {
            throw new IllegalArgumentException(String.format("Invalid format for zipcode: %s", addressModel.zipcode));
        }
    }

    /**
     * Umbrella method to check the database for an address, if it does not exist, create it.
     * @param addressModel the model to find or create
     * @return the found or created address
     * @throws IllegalArgumentException the addressmodel gets validated, if it contains wrong data, the exception is
     * thrown.
     */
    public Address findOrCreateAddress(AddressModel addressModel) throws IllegalArgumentException {
        Address returnValue;

        returnValue = this.getAddress(addressModel);

        if (returnValue == null) {
            returnValue = this.createAddress(addressModel);
        }

        return returnValue;
    }
}
