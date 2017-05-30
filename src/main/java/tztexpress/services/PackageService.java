package tztexpress.services;

import tztexpress.model.Package;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
public interface PackageService {

    List<Package> listAll();

    Package getById(Long id);

    Package saveOrUpdate(Package _package);

    void delete(Long id);
}
