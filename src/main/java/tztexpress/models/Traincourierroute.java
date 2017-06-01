package tztexpress.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Traincourierroute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String day;
  private long traincourierid;
  private long routeid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }


  public long getTraincourierid() {
    return traincourierid;
  }

  public void setTraincourierid(long traincourierid) {
    this.traincourierid = traincourierid;
  }


  public long getRouteid() {
    return routeid;
  }

  public void setRouteid(long routeid) {
    this.routeid = routeid;
  }

}
