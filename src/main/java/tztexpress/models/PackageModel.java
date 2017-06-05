package tztexpress.models;

/**
 * The model with additional information for the backoffice application
 */
public class PackageModel {
    public Long id;
    public User sender;
    public AddressModel origin;
    public AddressModel destination;
    public Long weight;
    public String value;
    public String details;
    public Boolean isDelivered;

}
