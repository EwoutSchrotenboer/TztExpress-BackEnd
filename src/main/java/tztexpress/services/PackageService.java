package tztexpress.services;

import tztexpress.models.Package;
import tztexpress.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {

    private PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
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
}
