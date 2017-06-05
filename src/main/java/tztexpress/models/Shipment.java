package tztexpress.models;

import javax.persistence.*;

/**
 * Generated Shipment model from the database
 */
@Entity
@Table(name="`shipment`")
public class Shipment {
    private Long id;
    private Long originAddressId;
    private Long destinationAddressId;
    private String cost;
    private Long packageId;
    private String couriertype;
    private Long traincourierId;
    private Long externalcourierId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="Shipment_Id_seq")
    @SequenceGenerator(name="Shipment_Id_seq", sequenceName = "Shipment_Id_seq")
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
    @Column(name = "cost", nullable = false, length = 255)
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "packageid", nullable = false)
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @Basic
    @Column(name = "couriertype", nullable = false, length = 255)
    public String getCouriertype() {
        return couriertype;
    }

    public void setCouriertype(String couriertype) {
        this.couriertype = couriertype;
    }

    @Basic
    @Column(name = "traincourierid", nullable = true)
    public Long getTraincourierId() {
        return traincourierId;
    }

    public void setTraincourierId(Long traincourierId) {
        this.traincourierId = traincourierId;
    }

    @Basic
    @Column(name = "externalcourierid", nullable = true)
    public Long getExternalcourierId() {
        return externalcourierId;
    }

    public void setExternalcourierId(Long externalcourierId) {
        this.externalcourierId = externalcourierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        if (id != null ? !id.equals(shipment.id) : shipment.id != null) return false;
        if (originAddressId != null ? !originAddressId.equals(shipment.originAddressId) : shipment.originAddressId != null)
            return false;
        if (destinationAddressId != null ? !destinationAddressId.equals(shipment.destinationAddressId) : shipment.destinationAddressId != null)
            return false;
        if (cost != null ? !cost.equals(shipment.cost) : shipment.cost != null) return false;
        if (packageId != null ? !packageId.equals(shipment.packageId) : shipment.packageId != null) return false;
        if (couriertype != null ? !couriertype.equals(shipment.couriertype) : shipment.couriertype != null)
            return false;
        if (traincourierId != null ? !traincourierId.equals(shipment.traincourierId) : shipment.traincourierId != null)
            return false;
        if (externalcourierId != null ? !externalcourierId.equals(shipment.externalcourierId) : shipment.externalcourierId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (originAddressId != null ? originAddressId.hashCode() : 0);
        result = 31 * result + (destinationAddressId != null ? destinationAddressId.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (packageId != null ? packageId.hashCode() : 0);
        result = 31 * result + (couriertype != null ? couriertype.hashCode() : 0);
        result = 31 * result + (traincourierId != null ? traincourierId.hashCode() : 0);
        result = 31 * result + (externalcourierId != null ? externalcourierId.hashCode() : 0);
        return result;
    }
}
