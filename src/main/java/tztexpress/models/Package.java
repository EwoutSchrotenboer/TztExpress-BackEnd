package tztexpress.models;

import javax.persistence.*;

/**
 * Created by Ewout on 1-6-2017.
 */
@Entity
@Table(name="`package`")
public class Package {
    private Long id;
    private Long originAddressId;
    private Long destinationAddressId;
    private Long weight;
    private String value;
    private String details;
    private Boolean isDelivered;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="Package_Id_seq")
    @SequenceGenerator(name="Package_Id_seq", sequenceName = "Package_Id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "originaddressid", nullable = false)
    public Long getOriginAddressId() {
        return originAddressId;
    }

    public void setOriginAddressId(Long originAddressId) {
        this.originAddressId = originAddressId;
    }

    @Basic
    @Column(name = "destinationaddressid", nullable = false)
    public Long getDestinationAddressId() {
        return destinationAddressId;
    }

    public void setDestinationAddressId(Long destinationAddressId) {
        this.destinationAddressId = destinationAddressId;
    }

    @Basic
    @Column(name = "weight", nullable = false)
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 255)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "details", nullable = true, length = 255)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Basic
    @Column(name = "isdelivered", nullable = true)
    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package aPackage = (Package) o;

        if (id != null ? !id.equals(aPackage.id) : aPackage.id != null) return false;
        if (originAddressId != null ? !originAddressId.equals(aPackage.originAddressId) : aPackage.originAddressId != null)
            return false;
        if (destinationAddressId != null ? !destinationAddressId.equals(aPackage.destinationAddressId) : aPackage.destinationAddressId != null)
            return false;
        if (weight != null ? !weight.equals(aPackage.weight) : aPackage.weight != null) return false;
        if (value != null ? !value.equals(aPackage.value) : aPackage.value != null) return false;
        if (details != null ? !details.equals(aPackage.details) : aPackage.details != null) return false;
        if (isDelivered != null ? !isDelivered.equals(aPackage.isDelivered) : aPackage.isDelivered != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (originAddressId != null ? originAddressId.hashCode() : 0);
        result = 31 * result + (destinationAddressId != null ? destinationAddressId.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (isDelivered != null ? isDelivered.hashCode() : 0);
        return result;
    }
}
