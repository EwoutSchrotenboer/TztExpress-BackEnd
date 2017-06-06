package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import tztexpress.models.Package;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Shipment;

import java.util.List;

/**
 * Contains the data to adjust Packages in the database.
 */
public interface PackageRepository extends CrudRepository<Package, Long> {
    @Query("select p from Package p join Shipment s on p.id = s.packageId where s.traincourierId = ?1")
    List<Package> getPackagesForTrainCourier(Long traincourierId);
}
