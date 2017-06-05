package tztexpress.models;

import java.util.ArrayList;

/**
 * The courier choice model, including the courier that was chosen as the cheapest option, the status and the type of
 * courier.
 */
public class CourierChoiceModel extends BaseModel {
    /**
     * The main courier
     */
    public CourierModel courier;

    /**
     * The type of the courier
     */
    public String type;

    /**
     * Additional information, currently not in use.
     */
    public String additionalinformation;

    /**
     * Status of the route calculation
     */
    public String status;

    public String originaddress;
    public String destinationaddress;

    // Note: Cost is set to String here, because the JSON somehow truncates the values.
    public String cost;
}
