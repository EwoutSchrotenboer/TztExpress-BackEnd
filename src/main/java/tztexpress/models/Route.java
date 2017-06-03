package tztexpress.models;

import javax.persistence.*;

/**
 * Created by Ewout on 1-6-2017.
 */
@Entity
@Table(name="`route`")
public class Route {
    private Long id;
    private String origin;
    private String destination;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="Route_Id_seq")
    @SequenceGenerator(name="Route_Id_seq", sequenceName = "Route_Id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "origin", nullable = false, length = 255)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "destination", nullable = false, length = 255)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != null ? !id.equals(route.id) : route.id != null) return false;
        if (origin != null ? !origin.equals(route.origin) : route.origin != null) return false;
        if (destination != null ? !destination.equals(route.destination) : route.destination != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
