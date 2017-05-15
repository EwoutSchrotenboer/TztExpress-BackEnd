package tztexpress.models;

import java.util.ArrayList;

/**
 * The courier choice model, including the courier that was chosen as the cheapest option, the status and the type of
 * courier.
 */
public class CourierChoiceModel extends BaseModel {
    public CourierModel Courier;
    public String Type;
    public String AdditionalInformation;
    public String Status;

    public String OriginAddress;
    public String DestinationAddress;

    // Note: Cost is set to String here, because the JSON somehow truncates the values.
    // TODO: find out how to fix this decently.
    public String Cost;
}
