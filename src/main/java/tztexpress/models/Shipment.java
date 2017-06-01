package tztexpress.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long originaddressid;
  private long destinationaddressid;
  private String cost;
  private long packageid;
  private String couriertype;
  private long traincourierid;
  private long externalcourierid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOriginaddressid() {
    return originaddressid;
  }

  public void setOriginaddressid(long originaddressid) {
    this.originaddressid = originaddressid;
  }


  public long getDestinationaddressid() {
    return destinationaddressid;
  }

  public void setDestinationaddressid(long destinationaddressid) {
    this.destinationaddressid = destinationaddressid;
  }


  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }


  public long getPackageid() {
    return packageid;
  }

  public void setPackageid(long packageid) {
    this.packageid = packageid;
  }


  public String getCouriertype() {
    return couriertype;
  }

  public void setCouriertype(String couriertype) {
    this.couriertype = couriertype;
  }


  public long getTraincourierid() {
    return traincourierid;
  }

  public void setTraincourierid(long traincourierid) {
    this.traincourierid = traincourierid;
  }


  public long getExternalcourierid() {
    return externalcourierid;
  }

  public void setExternalcourierid(long externalcourierid) {
    this.externalcourierid = externalcourierid;
  }

}
