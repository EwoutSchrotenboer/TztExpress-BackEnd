package tztexpress.repositories;

import tztexpress.models.Package;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package, Long> {
}
