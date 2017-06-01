package tztexpress.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Traincourier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String vogapproved;
  private String identification;
  private long userid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getVogapproved() {
    return vogapproved;
  }

  public void setVogapproved(String vogapproved) {
    this.vogapproved = vogapproved;
  }


  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }

}
