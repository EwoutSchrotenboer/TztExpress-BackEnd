package tztexpress.models;

import javax.persistence.*;

/**
 * Generated traincourierroute model from the database table.
 */
@Entity
@Table(name="`traincourierroute`")
public class TraincourierRoute {
    private Long id;
    private String day;
    private Long traincourierId;
    private Long routeId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="TraincourierRoute_Id_seq")
    @SequenceGenerator(name="TraincourierRoute_Id_seq", sequenceName = "TraincourierRoute_Id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "day", nullable = false, length = 50)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Basic
    @Column(name = "traincourierid", nullable = false)
    public Long getTraincourierId() {
        return traincourierId;
    }

    public void setTraincourierId(Long traincourierId) {
        this.traincourierId = traincourierId;
    }

    @Basic
    @Column(name = "routeid", nullable = false)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraincourierRoute that = (TraincourierRoute) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (traincourierId != null ? !traincourierId.equals(that.traincourierId) : that.traincourierId != null)
            return false;
        if (routeId != null ? !routeId.equals(that.routeId) : that.routeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (traincourierId != null ? traincourierId.hashCode() : 0);
        result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
        return result;
    }
}
