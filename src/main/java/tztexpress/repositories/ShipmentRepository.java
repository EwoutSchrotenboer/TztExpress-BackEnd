package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Shipment;

import java.util.List;

/**
 * Contains the data to adjust Shipments in the database.
 */
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
    @Query("select s from Shipment s where s.packageId = ?1")
    List<Shipment> getShipmentsForPackage(Long packageId);
}
