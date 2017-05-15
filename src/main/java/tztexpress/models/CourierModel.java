package tztexpress.models;

import tztexpress.enumerators.CourierTypes;

/**
 * The courier model, with t
 */
public class CourierModel extends BaseModel {

    public CourierModel() {}

    public CourierModel(CourierTypes type) {
        this.Type = type;
    }

    public CourierModel(CourierTypes type, Double cost) {
        this.Cost = cost;
        this.Type = type;

    }
    public CourierTypes Type;
    public Double Cost;

    // Used for First/Last-leg couriers for traincouriers
    public CourierModel OriginCourier;
    public CourierModel TrainCourier;
    public CourierModel DestinationCourier;

    public String OriginAddress;
    public String DestinationAddress;

    public boolean Available;

    // Bool to set if bicycle with <4km in biggest city; always the cheapest option, skip the other functionality.
    public boolean IsCheapestOption;

}
