package models;

/**
 * Created by Ewout on 11-5-2017.
 */
public class CourierModel {

    // todo: create enum for type - this should set the "kosten"-value as well, as it is a fixed propety.
    public CourierModel() {

    }

    public CourierModel(String type) {
        this.Type = type;
    }

    public CourierModel(String type, Double cost) {
        this.Cost = cost;
        this.Type = type;

    }
    public String Type;
    public Double Cost;

}
