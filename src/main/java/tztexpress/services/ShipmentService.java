package tztexpress.services;

import tztexpress.enumerators.CourierTypes;
import tztexpress.models.Shipment;
import tztexpress.models.User;
import tztexpress.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service creates a shipment, for a package.
 */
@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    /**
     * Gets the shipments for this package
     * @param id the packageId
     * @return a list of shipments
     */
    public List<Shipment> getShipmentsForPackage(long id) {
        return this.shipmentRepository.getShipmentsForPackage(id);
    }

    /**
     * A shipment is created with the provided information. This way, all couriers get registered as having handled
     * a specific package
     * @param originId the origin address id
     * @param destinationId the destination addrss id
     * @param packageId the id of the package
     * @param cost the full cost of the shipping route
     * @param courierType the type of the courier (Defined in CourierTypes)
     * @param courierId the Id of the courier
     * @return the new shipment.
     */
    public Shipment createShipment(long originId, long destinationId, long packageId, String cost, String courierType, long courierId) {
        Shipment shipment = new Shipment();

        shipment.setOriginAddressId(originId);
        shipment.setDestinationAddressId(destinationId);
        shipment.setPackageId(packageId);
        shipment.setCost(cost);
        shipment.setCouriertype(courierType);

        if (courierType.equals(CourierTypes.TRAINCOURIER.toString())) {
            shipment.setTraincourierId(courierId);
        } else {
            shipment.setExternalcourierId(courierId);
        }

        return this.shipmentRepository.save(shipment);
    }
}