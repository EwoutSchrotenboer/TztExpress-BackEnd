package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Route;
import tztexpress.models.TraincourierRoute;

import java.util.List;

/**
 * TraincourierRouteRepository, with added functionality to get all traincourierroutes for a specific courierId
 */
public interface TraincourierRouteRepository extends CrudRepository<TraincourierRoute, Long> {
    @Query("select t from TraincourierRoute t where t.traincourierId = ?1")
    List<TraincourierRoute> getRoutesForTrainCourier(Long courierId);

    /**
     * Technically this query does not belong here, but as it is only used in reference to traincourierroutes
     * we have decided to place it here anyway.
     * @param routeId the route id
     * @return the route
     */
    @Query("select r from Route r where r.id = ?1")
    Route getRouteById(Long routeId);
}
