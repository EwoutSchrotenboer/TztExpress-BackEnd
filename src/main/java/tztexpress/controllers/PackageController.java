package tztexpress.controllers;

import tztexpress.models.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public @ResponseBody List<Package> listPackages(){
        return packageService.listAll();
    }

    @RequestMapping(value = "/packages/{id}", method = RequestMethod.GET)
    public @ResponseBody Package getPackage(@PathVariable String id){
        return packageService.getById(Long.valueOf(id));
    }

    @RequestMapping(value = "/packages", method = RequestMethod.POST)
    public @ResponseBody Package saveOrUpdatePackage(@RequestBody Package _package){
        return packageService.saveOrUpdate(_package);
    }

    @RequestMapping(value = "/packages/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id){
        packageService.delete(Long.valueOf(id));
        return "Ok";
    }
}
