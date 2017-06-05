package tztexpress.repositories;

import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
}
