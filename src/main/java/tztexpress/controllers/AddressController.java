package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.services.AddressService;
import tztexpress.services.AuthenticationService;

import java.util.List;

/**
 * The address controller contains the methods for creating and adjusting addresses.
 * Authenticationtokens are required for all described methods.
 */
@Controller
@RequestMapping("/api/address")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * The create method creates a new address
     * @param headers the json and authentication-header
     * @param addressModel the model with the address information
     * @return a generic result with the new address
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<Address> createAddress(@RequestHeader HttpHeaders headers, @RequestBody AddressModel addressModel){
        if (AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            try {
                Address address = addressService.createAddress(addressModel);
                return GenericResultHandler.GenericResult(address);
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }

    /**
     * Update the address with new information.
     * @param headers the json and authentication-header
     * @param addressModel The address model, it contains an Id value for the current address in the database.
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<Address> updateAddress(@RequestHeader HttpHeaders headers, @RequestBody ChangeAddressModel addressModel) {
        if (AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            try {
                Address address = addressService.updateAddress(addressModel);
                return GenericResultHandler.GenericResult(address);
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}
