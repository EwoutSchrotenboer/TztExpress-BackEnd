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

/**
 * The packagecontroller contains code for both the frontend and the backoffice application. the request for packages
 * and the single package query are for the backoffice. Creation of packages is done on the website/frontend.
 * Authentication is required for all methods.
 */
@Controller
@RequestMapping("/api/package")
public class PackageController {
    private PackageService packageService;

    @Autowired
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    /**
     * Request a list of all packages, authentication is required.
     * @param headers the authentication header
     * @return the list of packages
     */
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

    /**
     * Returns a package with a specific Id
     * @param headers the authentication header
     * @param id the databaseId of the package
     * @return the requested package
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody GenericResult<Package> getPackage(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            return GenericResultHandler.GenericResult(packageService.getById(Long.valueOf(id)));
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }

    /**
     * Creates the package, with the CourierChoiceModel and package information. It returns the package model
     * @param headers the authentication and json headers
     * @param packageRequestModel this contains package information and the earlier created route informatin
     * @return the created package.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody GenericResult<Package> createPackage(@RequestHeader HttpHeaders headers, @RequestBody PackageRequestModel packageRequestModel) {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            try {
                return GenericResultHandler.GenericResult(packageService.createPackage(packageRequestModel));
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}