package tztexpress.repositories;

import tztexpress.model.Package;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package, Long> {
}
