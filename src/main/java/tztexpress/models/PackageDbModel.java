package tztexpress.models;

/**
 * Created by Ewout on 30-5-2017.
 */
public class PackageDbModel {
    private long id;
    private long weight;
    private String value;
    private String details;
    private AddressDbModel addressByOriginAddressId;
    private AddressDbModel addressByDestinationAddressId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageDbModel that = (PackageDbModel) o;

        if (id != that.id) return false;
        if (weight != that.weight) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (weight ^ (weight >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    public AddressDbModel getAddressByOriginAddressId() {
        return addressByOriginAddressId;
    }

    public void setAddressByOriginAddressId(AddressDbModel addressByOriginAddressId) {
        this.addressByOriginAddressId = addressByOriginAddressId;
    }

    public AddressDbModel getAddressByDestinationAddressId() {
        return addressByDestinationAddressId;
    }

    public void setAddressByDestinationAddressId(AddressDbModel addressByDestinationAddressId) {
        this.addressByDestinationAddressId = addressByDestinationAddressId;
    }
}
