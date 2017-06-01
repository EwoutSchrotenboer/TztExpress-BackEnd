package tztexpress.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Package {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long originaddressid;
  private long destinationaddressid;
  private long weight;
  private String value;
  private String details;


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

}
