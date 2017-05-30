package tztexpress.models;

/**
 * Created by Ewout on 30-5-2017.
 */
public class ShipmentDbModel {
    private long id;
    private String cost;
    private String couriertype;
    private AddressDbModel addressByOriginAddressId;
    private AddressDbModel addressByDestinationAddressId;
    private PackageDbModel packageByPackageId;
    private ExternalcourierDbModel externalcourierByExternalcourierId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCouriertype() {
        return couriertype;
    }

    public void setCouriertype(String couriertype) {
        this.couriertype = couriertype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentDbModel that = (ShipmentDbModel) o;

        if (id != that.id) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (couriertype != null ? !couriertype.equals(that.couriertype) : that.couriertype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (couriertype != null ? couriertype.hashCode() : 0);
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

    public PackageDbModel getPackageByPackageId() {
        return packageByPackageId;
    }

    public void setPackageByPackageId(PackageDbModel packageByPackageId) {
        this.packageByPackageId = packageByPackageId;
    }

    public ExternalcourierDbModel getExternalcourierByExternalcourierId() {
        return externalcourierByExternalcourierId;
    }

    public void setExternalcourierByExternalcourierId(ExternalcourierDbModel externalcourierByExternalcourierId) {
        this.externalcourierByExternalcourierId = externalcourierByExternalcourierId;
    }
}
