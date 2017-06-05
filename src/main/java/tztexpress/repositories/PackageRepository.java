package tztexpress.repositories;

import tztexpress.models.Package;
import org.springframework.data.repository.CrudRepository;

/**
 * Contains the data to adjust Packages in the database.
 */
public interface PackageRepository extends CrudRepository<Package, Long> {
}
