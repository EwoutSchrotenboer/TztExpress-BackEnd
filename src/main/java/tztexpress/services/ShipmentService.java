package tztexpress.services;

import tztexpress.enumerators.CourierTypes;
import tztexpress.models.Shipment;
import tztexpress.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment createShipment(long originId, long destinationId, long packageId, String cost, String courierType, long courierId) {
        Shipment shipment = new Shipment();

        shipment.setOriginAddressId(originId);
        shipment.setDestinationAddressId(destinationId);
        shipment.setPackageId(packageId);
        shipment.setCost(cost);
        shipment.setCouriertype(courierType);

        if (courierType == CourierTypes.TRAINCOURIER.toString()) {
            shipment.setTraincourierId(courierId);
        } else {
            shipment.setExternalcourierId(courierId);
        }

        return this.shipmentRepository.save(shipment);
    }
}