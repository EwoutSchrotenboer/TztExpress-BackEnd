package tztexpress.models;

/**
 * Created by Ewout on 30-5-2017.
 */
public class TraincourierRouteDbModel {
    private long id;
    private String day;
    private RouteDbModel routeByRouteId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraincourierRouteDbModel that = (TraincourierRouteDbModel) o;

        if (id != that.id) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    public RouteDbModel getRouteByRouteId() {
        return routeByRouteId;
    }

    public void setRouteByRouteId(RouteDbModel routeByRouteId) {
        this.routeByRouteId = routeByRouteId;
    }
}
