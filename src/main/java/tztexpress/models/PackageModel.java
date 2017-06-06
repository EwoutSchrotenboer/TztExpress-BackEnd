package tztexpress.models;

import java.util.List;

/**
 * The model with additional information for the backoffice application
 */
public class PackageModel {
    public Long id;
    public UserPackageModel sender;
    public Address origin;
    public Address destination;
    public Long weight;
    public String value;
    public String details;
    public Boolean isDelivered;
    public List<Shipment> shipments;
    public TraincourierPackageModel traincourier;
    public List<Externalcourier> externalcouriers;

}
