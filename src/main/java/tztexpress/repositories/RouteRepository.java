package tztexpress.repositories;

import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Route;

/**
 * Routerepository
 */
public interface RouteRepository extends CrudRepository<Route, Long> {
}
