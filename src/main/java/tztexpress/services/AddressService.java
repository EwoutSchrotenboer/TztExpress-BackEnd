package tztexpress.services;

import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.Address;
import tztexpress.models.AddressModel;
import tztexpress.repositories.AddressRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ewout on 3-6-2017.
 */
@Service
public class AddressService {

    private static AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddress(AddressModel addressModel) throws InvalidDataException {
        // check if address is complete:
        if (validAddress(addressModel)) {
//            if(addressModel.address2 != null && add) {
//               return addressRepository.findAddress2lines(addressModel.address1, addressModel.address2, addressModel.zipcode, addressModel.city);
//            } else {
                return addressRepository.findAddress(addressModel.address1, addressModel.address2, addressModel.zipcode, addressModel.city);
            //}
        }

        return null;

    }

    public Address createAddress(AddressModel addressModel) throws InvalidDataException {
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

        return null;
    }

    private static boolean validAddress(AddressModel addressModel) throws InvalidDataException {
        if (addressModel.address1 == null || addressModel.zipcode == null || addressModel.city == null) {
            String exceptionMessage = new String();

            exceptionMessage += (addressModel.address1 == null ? "Address1 not set" : addressModel.address1) + ", ";
            exceptionMessage += (addressModel.zipcode == null ? "Zipcode not set" : addressModel.zipcode) + ", ";
            exceptionMessage += (addressModel.city == null ? "City not set" : addressModel.city) + ".";

            throw new InvalidDataException(String.format("Invalid address: %s", exceptionMessage));
        }

        // validate zipcode
        Pattern regex = Pattern.compile("([0-9]{4})\\s*([A-Za-z]{2})");
        Matcher match = regex.matcher(addressModel.zipcode);

        if (match.matches()) {
            return true;
        } else {
            throw new InvalidDataException(String.format("Invalid format for zipcode: %s", addressModel.zipcode));
        }
    }


}