package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Traincourier;
import tztexpress.models.TraincourierRoute;

import java.util.List;

public interface TraincourierRepository extends CrudRepository<Traincourier, Long> {

    @Query("select tcr from TraincourierRoute tcr, Route r " +
            "where tcr.routeId = r.id " +
            "and tcr.day = ?1 and r.origin = ?2 and r.destination = ?3")
    List<TraincourierRoute> availableCourierRoutes(String weekday, String originTrainStation, String destinationTrainStation);
}