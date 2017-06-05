package tztexpress.models;

import tztexpress.enumerators.CourierTypes;

/**
 * The courier model, with t
 */
public class CourierModel extends BaseModel {

    public CourierModel() {}

    public CourierModel(CourierTypes type) {
        this.type = type;
    }

    public CourierModel(CourierTypes type, Double cost) {
        this.cost = cost;
        this.type = type;

    }
    public CourierTypes type;
    public Double cost;
    public long traincourierdbid;

    // Used for First/Last-leg couriers for traincouriers
    public CourierModel origincourier;
    public CourierModel traincourier;
    public CourierModel destinationcourier;

    public String originaddress;
    public String destinationaddress;

    public boolean available;

    // Bool to set if bicycle with <4km in biggest city; always the cheapest option, skip the other functionality.
    public boolean ischeapestoption;

}
