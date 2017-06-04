package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.services.AddressService;
import tztexpress.services.AuthenticationService;
import tztexpress.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/address")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

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
