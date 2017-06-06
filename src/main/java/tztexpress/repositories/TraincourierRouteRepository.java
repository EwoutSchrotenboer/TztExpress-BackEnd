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
}
