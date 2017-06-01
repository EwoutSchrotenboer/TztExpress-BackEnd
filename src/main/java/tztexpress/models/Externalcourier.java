package tztexpress.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Externalcourier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String companyname;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCompanyname() {
    return companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

}
