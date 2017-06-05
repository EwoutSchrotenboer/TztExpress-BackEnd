package tztexpress.repositories;

import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Shipment;

/**
 * Contains the data to adjust Shipments in the database.
 */
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
}
