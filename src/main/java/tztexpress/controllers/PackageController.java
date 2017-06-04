package tztexpress.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.GenericResult;
import tztexpress.models.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tztexpress.models.PackageRequestModel;
import tztexpress.services.AuthenticationService;
import tztexpress.services.PackageService;

import java.util.List;

@Controller
@RequestMapping("/api/package")
public class PackageController {
    private PackageService packageService;

    @Autowired
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    @RequestMapping(value = "/packages", method = RequestMethod.GET)
    public @ResponseBody GenericResult<List<Package>> listPackages(@RequestHeader HttpHeaders headers)
    {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            return GenericResultHandler.GenericResult(packageService.listAll());
        }
        else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody GenericResult<Package> getPackage(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            return GenericResultHandler.GenericResult(packageService.getById(Long.valueOf(id)));
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }

//    // TODO
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody GenericResult<Package> createPackage(@RequestHeader HttpHeaders headers, @RequestBody PackageRequestModel packageRequestModel) {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            return GenericResultHandler.GenericResult(packageService.createPackage(packageRequestModel));
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}